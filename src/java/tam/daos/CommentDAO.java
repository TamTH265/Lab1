/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import tam.db.MyConnection;
import tam.dtos.CommentDTO;

/**
 *
 * @author hoang
 */
public class CommentDAO {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public CommentDAO() {
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

    public boolean postComment(String memberEmail, int blogID, String content, String commentTime) throws Exception {
        boolean isSuccess = false;

        try {
            String sql = "insert into Comment(MemberEmail, BlogID, Content, CommentTime) values(?, ?, ?, ?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, memberEmail);
            preStm.setInt(2, blogID);
            preStm.setString(3, content);
            preStm.setString(4, commentTime);
            isSuccess = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }

        return isSuccess;
    }

    public List<CommentDTO> getAllCommentsByBlogID(int blogID) throws Exception {
        List<CommentDTO> commentsList = null;

        try {
            String sql = "select Account.Name, Comment.Content, Comment.CommentTime from Comment, Account where Comment.BlogID = ? and Comment.MemberEmail = Account.Email";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, blogID);
            rs = preStm.executeQuery();
            commentsList = new ArrayList<>();
            while (rs.next()) {
               String userName = rs.getString(1); 
               String content = rs.getString(2); 
               String commentTime = rs.getString(3); 

               CommentDTO comment = new CommentDTO(content, commentTime, userName);
               commentsList.add(comment);
            }
        } finally {
            closeConnection();
        }
        
        return commentsList;
    }
}
