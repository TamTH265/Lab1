<%-- 
    Document   : articlePosting
    Created on : Jan 8, 2020, 8:51:04 PM
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
        <c:if test="${sessionScope.ROLE ne 'Member'}">
            <jsp:forward page="login.jsp" />
        </c:if>
        <h1>Posting Page</h1>
        <form action="MainController" method="POST">
            Title<input type="text" name="title" />
            <c:if test="${requestScope.BlogError.titleError != null}">
                <span style="color: #f00;">${requestScope.BlogError.titleError}</span>
            </c:if>
            Short Description<textarea name="shortDescription"></textarea>
            <c:if test="${requestScope.BlogError.shortDescriptionError != null}">
                <span style="color: #f00;">${requestScope.BlogError.shortDescriptionError}</span>
            </c:if>
            Content<textarea name="content"></textarea>
            <c:if test="${requestScope.BlogError.contentError != null}">
                <span style="color: #f00;">${requestScope.BlogError.contentError}</span>
            </c:if>
            <input type="submit" name="action" value="postArticle" />
        </form>
    </body>
</html>
