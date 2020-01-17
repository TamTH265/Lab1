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
public class BlogDTO implements Serializable {

    private String title, shortDescription, content, author, status, postedTime;
    private int blogID;

    public BlogDTO() {
    }

    public BlogDTO(String title, String shortDescription, String content) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
    }

    public BlogDTO(String title, String shortDescription, String content, String author, String postedTime) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.author = author;
        this.postedTime = postedTime;
    }

    public BlogDTO(String title, String shortDescription, String content, String author, String postedTime, String status, int blogID) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.content = content;
        this.author = author;
        this.postedTime = postedTime;
        this.status = status;
        this.blogID = blogID;
    }

    public int getBlogID() {
        return blogID;
    }

    public void setBlogID(int blogID) {
        this.blogID = blogID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }
}
