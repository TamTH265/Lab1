<%-- 
    Document   : article-detail-management
    Created on : Jan 11, 2020, 8:28:09 AM
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
        <h1>Article Detail Management</h1>
        <c:if test="${requestScope.BlogDetail != null}">   
            <c:set value="${requestScope.BlogDetail}" var="BlogDetail" />
            <ul>
                <li>${BlogDetail.title}</li>
                <li>${BlogDetail.shortDescription}</li>
                <li>${BlogDetail.content}</li>
                <li>${BlogDetail.author}</li>
                <li>${BlogDetail.postedTime}</li>
            </ul>
            <c:url var="articleDetailManage" value="MainController">
                <c:param name="blogID" value="${BlogDetail.blogID}" />
            </c:url>
            <form action="${articleDetailManage}" method="POST">
                <input type="submit" name="action" value="approveArticle" />
            </form>
            <form action="${articleDetailManage}" method="POST">
                <input type="submit" name="action" value="deleteArticle" />
            </form>
            <c:if test="${requestScope.CommentsData == null}">
                <span style="color: #f00;">There isn't any comments in this article!</span>
            </c:if>
        </c:if>
    </body>
</html>
