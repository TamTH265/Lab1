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
        </c:if>
    </body>
</html>
