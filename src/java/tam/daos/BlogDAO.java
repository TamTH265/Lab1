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
import java.util.ArrayList;
import java.util.List;
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

    public boolean postArticle(String email, String title, String shortDescription, String content, String postedTime) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "insert into Blog(Title, ShortDescription, Content, Author, PostedTime, Status) values(?, ?, ?, ?, ?, 'New')";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, title);
            preStm.setString(2, shortDescription);
            preStm.setString(3, content);
            preStm.setString(4, email);
            preStm.setString(5, postedTime);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public List<BlogDTO> getAllBlogs(int page, int numOfbBlogsPerPage) throws Exception {
        List<BlogDTO> blogsList = null;

        try {
            String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime) as rowNum from Blog) as blog where blog.rowNum between ? and ? order by PostedTime desc";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, (page - 1) * 3 + 1);
            preStm.setInt(2, (page - 1) * 3 + numOfbBlogsPerPage);
            rs = preStm.executeQuery();
            blogsList = new ArrayList<>();
            while (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String author = rs.getString("Author");
                String title = rs.getString("Title");
                String shortDescription = rs.getString("ShortDescription");
                String postedTime = rs.getString("PostedTime");
                String status = rs.getString("Status");
                BlogDTO dto = new BlogDTO(blogID, author, title, shortDescription, postedTime, status);
                blogsList.add(dto);
            }
        } finally {
            closeConnection();
        }

        return blogsList;
    }

    public int getBlogsTotal() throws Exception {
        int total = 0;

        try {
            String sql = "select count(*) from Blog";
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
            String sql = "select BlogID, Title, ShortDescription, Content, Author, PostedTime from Blog where BlogID = ?";
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
                String postedTime = rs.getString("PostedTime");
                blog = new BlogDTO(title, shortDescription, content, author, postedTime, blogID);
            }
        } finally {
            closeConnection();
        }

        return blog;
    }

    public boolean approveArticle(int blogID) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "update Blog set Status = 'Activated' where BlogID = ?";
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

    public List<BlogDTO> getSearchedBlogsData(String searchedContent, String searchedArticle, String[] searchedStatus, int page, int numOfbBlogsPerPage) throws Exception {
        List<BlogDTO> blogsList = null;

        try {
            conn = MyConnection.getMyConnection();

            if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus == null) {
                searchDataByContent(searchedContent, page, numOfbBlogsPerPage);
            } else if (searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus == null) {
                searchDataByArticle(searchedArticle, page, numOfbBlogsPerPage);
            }

            blogsList = new ArrayList<>();
            while (rs.next()) {
                int blogID = rs.getInt("BlogID");
                String author = rs.getString("Author");
                String title = rs.getString("title");
                String postedTime = rs.getString("PostedTime");
                String shortDescription = rs.getString("ShortDescription");
                String status = rs.getString("Status");
                BlogDTO blog = new BlogDTO(blogID, author, title, shortDescription, postedTime, status);
                blogsList.add(blog);
            }
        } finally {
            closeConnection();
        }

        return blogsList;
    }

    private void searchDataByContent(String searchedContent, int page, int numOfbBlogsPerPage) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime) as rowNum from Blog where Content like ?) as blog where blog.rowNum between ? and ? order by PostedTime desc";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        preStm.setInt(2, (page - 1) * 3 + 1);
        preStm.setInt(3, (page - 1) * 3 + numOfbBlogsPerPage);
        rs = preStm.executeQuery();
    }

    private void searchDataByArticle(String searchedArticle, int page, int numOfbBlogsPerPage) throws Exception {
        String sql = "select BlogID, Author, Title, PostedTime, ShortDescription, Status from (select BlogID, Author, Title, PostedTime, ShortDescription, Status, ROW_NUMBER() over (order by PostedTime) as rowNum from Blog where Title like ?) as blog where blog.rowNum between ? and ? order by PostedTime desc";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedArticle + "%");
        preStm.setInt(2, (page - 1) * 3 + 1);
        preStm.setInt(3, (page - 1) * 3 + numOfbBlogsPerPage);
        rs = preStm.executeQuery();
    }
    
    
    private void searchedDataByStatus(String[] seachedStatus, int page, int numOfBlogsPerPage) throws Exception {
        
    }
    
    
    
    

    public int getSearchedBlogsTotal(String searchedContent, String searchedArticle, String[] searchedStatus) throws Exception {
        int total = 0;
        try {
            conn = MyConnection.getMyConnection();
            if (!searchedContent.equals("") && searchedArticle.equals("") && searchedStatus == null) {
                total = getSearchedBlogsByContentTotal(searchedContent);
            } else if (searchedContent.equals("") && !searchedArticle.equals("") && searchedStatus == null) {
                total = getSearchedBlogsByArticleTotal(searchedArticle);
            }
            
        } finally {
            closeConnection();
        }

        return total;
    }

    private int getSearchedBlogsByContentTotal(String searchedContent) throws Exception {
        int total = 0;

        String sql = "select count(*) from Blog where Content like ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedContent + "%");
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }

        return total;
    }
    
    private int getSearchedBlogsByArticleTotal(String searchedArticle) throws Exception {
        int total = 0;
        
         String sql = "select count(*) from Blog where Title like ?";
        preStm = conn.prepareStatement(sql);
        preStm.setString(1, "%" + searchedArticle + "%");
        rs = preStm.executeQuery();
        if (rs.next()) {
            total = rs.getInt(1);
        }
        
        return total;
    }
}
