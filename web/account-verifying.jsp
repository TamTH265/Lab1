<%-- 
    Document   : account-verifying
    Created on : Jan 12, 2020, 10:12:46 AM
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
        <h1>Thank You For Registration!</h1>
        <p class="lead">Please Check your Email for Account Verification Link.</p>
        <form action="MainController" method="POST">
            <input type="text" name="inputedVerifyingCode" />
            <input type="submit" name="action" value="verify">
        </form>
        <c:if test="${requestScope.VERIFYINGERROR != null}">
            <span style="color: #f00;">${requestScope.VERIFYINGERROR}</span>
        </c:if>
    </body>
</html>
