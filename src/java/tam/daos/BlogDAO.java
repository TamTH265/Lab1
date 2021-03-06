/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.daos;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import tam.db.MyConnection;
import tam.dtos.BlogDTO;

/**
 *
 * @author hoang
 */
public class BlogDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public BlogDAO() {
    }

    private void closeConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public boolean postArticle(String email, String title, String shortDescription, String content, Timestamp postedTime) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "insert into Blog(Title, ShortDescription, Content, Author, PostedTime, Status, PreStatus) values(?, ?, ?, ?, ?, 'New', 'New')";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, title);
            preStm.setString(2, shortDescription);
            preStm.setString(3, content);
            preStm.setString(4, email);
            preStm.setTimestamp(5, postedTime);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public boolean checkDuplicate(String title) throws Exception {
        boolean isDuplicate = false;

        try {
            String sql = "select Title from Blog where Title like ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, title);
            rs = preStm.executeQuery();
            if (rs.next()) {
                isDuplicate = true;
            }
        } finally {
            closeConnection();
        }

        return isDuplicate;
    }

    public List<BlogDTO> getAllBlogs(int page, int numOfbBlogsPerPage, String signal) throws Exception {
        List<BlogDTO> blogsList = null;

        try {
            String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime desc) as rowNum from Blog signal) as blog where blog.rowNum between ? and ?";
            if (!signal.equals("Admin")) {
                sql = sql.replace("signal", "where Status = 'Activated'");
            } else {
                sql = sql.replace("signal", "");
            }
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, (page - 1) * numOfbBlogsPerPage + 1);
            preStm.setInt(2, (page - 1) * numOfbBlogsPerPage + numOfbBlogsPerPage);
            rs = preStm.executeQuery();
            blogsList = new ArrayList<>();
            while (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String author = rs.getString("Author");
                String title = rs.getString("Title");
                String shortDescription = rs.getString("ShortDescription");
                Timestamp postedTime = rs.getTimestamp("PostedTime");
                String status = rs.getString("Status");
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

                BlogDTO dto = new BlogDTO(title, shortDescription, null, author, sdf.format(postedTime), status, blogID);
                blogsList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return blogsList;
    }

    public int getBlogsTotal(String signal) throws Exception {
        int total = 0;

        try {
            String sql = "select count(*) from Blog signal";
            if (!signal.equals("Admin")) {
                sql = sql.replace("signal", "where Status = 'Activated'");
            } else {
                sql = sql.replace("signal", "");
            }
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            rs = preStm.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } finally {
            closeConnection();
        }

        return total;
    }

    public BlogDTO getBlogDetailByBlogID(int id) throws Exception {
        BlogDTO blog = null;

        try {
            String sql = "select BlogID, Title, ShortDescription, Content, Author, Status, PostedTime from Blog where BlogID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String title = rs.getString("Title");
                String shortDescription = rs.getString("ShortDescription");
                String content = rs.getString("Content");
                String author = rs.getString("Author");
                String status = rs.getString("Status");
                Timestamp postedTime = rs.getTimestamp("PostedTime");
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

                blog = new BlogDTO(title, shortDescription, content, author, sdf.format(postedTime), status, blogID);
            }
        } finally {
            closeConnection();
        }

        return blog;
    }

    public boolean approveArticle(int blogID) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Blog set Status = 'Activated', PreStatus = 'Activated' where BlogID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, blogID);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public boolean deleteArticle(int blogID) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Blog set Status = 'Deleted' where BlogID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, blogID);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public boolean restoreArticle(int blogID) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Blog set Status = PreStatus where BlogID = ?";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, blogID);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public List<BlogDTO> getSearchedBlogsData(String searchedContent, String searchedArticle, String searchedStatus, int page, int numOfBlogsPerPage, String signal) throws Exception {
        List<BlogDTO> blogsList = null;

        try {
            conn = MyConnection.getMyConnection();

            if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus == null) {
                searchDataByContent(searchedContent, page, numOfBlogsPerPage, signal);
            } else if (!searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus == null) {
                searchDataByContentAndArticle(searchedContent, searchedArticle, page, numOfBlogsPerPage);
            } else if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus != null) {
                searchDataByContentAndStatus(searchedContent, searchedStatus, page, numOfBlogsPerPage);
            } else if (!searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus != null) {
                searchDataByAll(searchedContent, searchedArticle, searchedStatus, page, numOfBlogsPerPage);
            }

            blogsList = new ArrayList<>();
            while (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String author = rs.getString("Author");
                String title = rs.getString("Title");
                Timestamp postedTime = rs.getTimestamp("PostedTime");
                String shortDescription = rs.getString("ShortDescription");
                String status = rs.getString("Status");
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

                BlogDTO blog = new BlogDTO(title, shortDescription, null, author, sdf.format(postedTime), status, blogID);
                blogsList.add(blog);
            }
        } finally {
            closeConnection();
        }

        return blogsList;
    }

    private void searchDataByContent(String searchedContent, int page, int numOfBlogsPerPage, String signal) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime desc) as rowNum from Blog where Content like ? signal) as blog where blog.rowNum between ? and ?";
        if (!signal.equals("Admin")) {
            sql = sql.replace("signal", "and Status = 'Activated'");
        } else {
            sql = sql.replace("signal", "");
        }
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setInt(2, (page - 1) * numOfBlogsPerPage + 1);
        preStm.setInt(3, (page - 1) * numOfBlogsPerPage + numOfBlogsPerPage);
        rs = preStm.executeQuery();
    }

    private void searchDataByContentAndArticle(String searchedContent, String searchedArticle, int page, int numOfBlogsPerPage) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime desc) as rowNum from Blog where Content like ? and Title like ?) as blog where blog.rowNum between ? and ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setString(2, "%" + searchedArticle + "%");
        preStm.setInt(3, (page - 1) * 3 + 1);
        preStm.setInt(4, (page - 1) * 3 + numOfBlogsPerPage);
        rs = preStm.executeQuery();
    }

    private void searchDataByContentAndStatus(String searchedContent, String searchedStatus, int page, int numOfBlogsPerPage) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime desc) as rowNum from Blog where Status = ? and Content like ? ) as blog where blog.rowNum between ? and ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, searchedStatus);
        preStm.setString(2, "%" + searchedContent + "%");
        preStm.setInt(3, (page - 1) * 3 + 1);
        preStm.setInt(4, (page - 1) * 3 + numOfBlogsPerPage);
        rs = preStm.executeQuery();
    }

    private void searchDataByAll(String searchedContent, String searchedArticle, String searchedStatus, int page, int numOfBlogsPerPage) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime desc) as rowNum from Blog where Content like ? and Title like ? and Status = ?) as blog where blog.rowNum between ? and ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setString(2, "%" + searchedArticle + "%");
        preStm.setString(3, searchedStatus);
        preStm.setInt(4, (page - 1) * 3 + 1);
        preStm.setInt(5, (page - 1) * 3 + numOfBlogsPerPage);
        rs = preStm.executeQuery();
    }

    public int getSearchedBlogsTotal(String searchedContent, String searchedArticle, String searchedStatus, String signal) throws Exception {
        int total = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus == null) {
                total = getSearchedBlogsByContentTotal(searchedContent, signal);
            } else if (!searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus == null) {
                total = getSearchedBlogsByContentAndArticleTotal(searchedContent, searchedArticle);
            } else if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus != null) {
                total = getSearchedBlogsByContentAndStatusTotal(searchedContent, searchedStatus);
            } else if (!searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus != null) {
                total = getSearchedBlogsByAllTotal(searchedContent, searchedArticle, searchedStatus);
            }

        } finally {
            closeConnection();
        }

        return total;
    }

    private int getSearchedBlogsByContentTotal(String searchedContent, String signal) throws Exception {
        int total = 0;

        String sql = "select count(*) from Blog where Content like ? signal";
        if (!signal.equals("Admin")) {
            sql = sql.replace("signal", "and Status = 'Activated'");
        } else {
            sql = sql.replace("signal", "");
        }
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        return total;
    }

    private int getSearchedBlogsByContentAndArticleTotal(String searchedContent, String searchedArticle) throws Exception {
        int total = 0;

        String sql = "select count(*) from Blog where Content like ? and Title like ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setString(2, "%" + searchedArticle + "%");
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        return total;
    }

    private int getSearchedBlogsByContentAndStatusTotal(String searchedContent, String searchedStatus) throws Exception {
        int total = 0;
        String sql = "select count(*) from Blog where Content like ? and Status = ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setString(2, searchedStatus);
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        return total;
    }

    private int getSearchedBlogsByAllTotal(String searchedContent, String searchedArticle, String searchedStatus) throws Exception {
        int total = 0;
        String sql = "select count(*) from Blog where Content like ? and Title like ? and Status = ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setString(2, "%" + searchedArticle + "%");
        preStm.setString(3, searchedStatus);
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        return total;
    }

    public boolean deleteSelectedBlogs(List<Integer> selectedBlogs) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Blog set Status = 'Deleted' where BlogID in (?)";
            String sqlIn = selectedBlogs.stream().map(x -> String.valueOf(x)).collect(Collectors.joining(",", "(", ")"));
            sql = sql.replace("(?)", sqlIn);
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }
}
