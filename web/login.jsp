<%-- 
    Document   : login
    Created on : Jan 8, 2020, 7:45:18 PM
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
        <h1>Login Page</h1>
        <form action="MainController" method="POST">
            Email<input type="email" name="email" value="${param.email}" />
            <c:if test="${requestScope.AccountError.emailError != NULL}">
                <span style="color: #f00;">${requestScope.AccountError.emailError}</span>
            </c:if>
            Password<input type="password" name="password" />
            <c:if test="${requestScope.AccountError.emailError != NULL}">
                <span style="color: #f00;">${requestScope.AccountError.emailError}</span>
            </c:if>
            <input type="submit" name="action" value="login" />
            <c:if test="${requestScope.InvalidAccount != NULL}">
                <span style="color: #f00;">${requestScope.InvalidAccount}</span>
            </c:if>
        </form>
    </body>
</html>
