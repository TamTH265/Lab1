<%-- 
    Document   : articleDetail
    Created on : Jan 9, 2020, 3:57:21 PM
    Author     : hoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Article Detail</h1>
        <c:if test="${requestScope.BlogDetail != null}">   
            <c:set value="${requestScope.BlogDetail}" var="BlogDetail" />
            <ul>
                <li>${BlogDetail.title}</li>
                <li>${BlogDetail.shortDescription}</li>
                <li>${BlogDetail.content}</li>
                <li>${BlogDetail.author}</li>
                <li>${BlogDetail.postedTime}</li>
            </ul>
            <c:url value="MainController" var="postComment">
                <c:param value="${BlogDetail.title}" name="title" />
                <c:param value="${BlogDetail.shortDescription}" name="shortDescription" />
                <c:param value="${BlogDetail.content}" name="content" />
                <c:param value="${BlogDetail.author}" name="author" />
                <c:param value="${BlogDetail.postedTime}" name="postedTime" />
                <c:param value="${BlogDetail.blogID}" name="blogID" />
            </c:url>
            <form action="${postComment}" method="POST">
                <textarea name="comment"></textarea>
                <c:if test="${requestScope.CommentError.contentError != null}">
                    <span style="color: #f00;">${requestScope.CommentError.contentError}</span>
                </c:if>
                <input type="submit" name="action" value="postComment" />
            </form>
            <h3>Comments List</h3>
            <c:if test="${requestScope.CommentsData != null}">
                <ul>
                    <c:forEach items="${requestScope.CommentsData}" var="comment">
                        <li>
                            <span style="color: #00f">${comment.userName}</span>
                            <span>${comment.content}</span>
                            <span style="color: #333">${comment.commentTime}</span>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${requestScope.CommentsData == null}">
                <span style="color: #f00;">There isn't any comments in this article!</span>
            </c:if>
        </c:if>
    </body>
</html>
