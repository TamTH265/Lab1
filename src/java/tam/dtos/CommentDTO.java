/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.dtos;

import java.io.Serializable;

/**
 *
 * @author hoang
 */
public class CommentDTO implements Serializable {

    private String memberEmail, content, userName, commentTime;
    private int blogID;

    public CommentDTO() {
    }

    public CommentDTO(String content, String commentTime, String userName) {
        this.content = content;
        this.commentTime = commentTime;
        this.userName = userName;
    }
    
    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
