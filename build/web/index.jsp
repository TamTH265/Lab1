<%-- 
    Document   : index
    Created on : Jan 7, 2020, 2:07:05 PM
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
        <h1>Index Page</h1>
        <c:if test="${sessionScope.NAME != null}">
            <div>Hello, ${sessionScope.NAME}</div>
        </c:if>
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="logout" />
        </form>
    </body>
</html>
