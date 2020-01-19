<%-- 
    Document   : register
    Created on : Jan 8, 2020, 6:23:30 PM
    Author     : hoang
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,700|Roboto:400,500&display=swap"
              rel="stylesheet">
        <link rel="stylesheet" href="./styles/register.css">
    </head>
    <body>
        <c:if test="${sessionScope.ROLE eq 'Admin'}">
            <c:redirect url="admin.jsp" />
        </c:if>
        <c:if test="${sessionScope.ROLE eq 'Member'}">
            <c:redirect url="index.jsp" />
        </c:if>
        <div class="signup-form-container">
            <form id="register-form" action="MainController" method="POST">
                <h3>Sign Up</h3>
                <div class="form-item">
                    <label for="email">Email</label>
                    <input id="email" type="text" name="email" value="${param.email}" />
                    <span class="error" id="email-error"><c:if test="${requestScope.DuplicateError != NULL}">${requestScope.DuplicateError} </c:if></span>
                    </div>
                    <div class="form-item">
                        <label for="name">User Name</label>
                        <input id="username" type="text" name="name" value="${param.name}" />
                    <span class="error" id="username-error"></span>
                </div>
                <div class="form-item">
                    <label for="password">Password</label>
                    <input id="password" type="password" name="password" />
                    <span class="error" id="password-error"></span>
                </div>
                <div class="form-item">
                    <label for="password-confirm">Confirm Password</label>
                    <input id="password-confirm" type="password" name="passwordConfirm" />
                    <span class="error" id="password-confirm-error"></span>
                </div>
                <button id="register-btn" type="submit" name="action" value="register">Sign Up</button>
            </form>
        </div>

        <script src="./scripts/register-handling.js"></script>
    </body>
</html>
