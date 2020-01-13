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
        <div class="signup-form-container">
            <form action="MainController" method="POST">
                <h3>Sign Up</h3>
                <div class="form-item">
                    <label for="email">Email</label>
                    <input type="text" name="email" value="${param.email}" id="email" />
                    <c:if test="${requestScope.AccountError.emailError != NULL}">
                        <span class="error">${requestScope.AccountError.emailError}</span>
                    </c:if>
                </div>
                <div class="form-item">
                    <label for="name">User Name</label>
                    <input type="text" name="name" value="${param.name}" id="name" />
                    <c:if test="${requestScope.AccountError.nameError != NULL}">
                        <span class="error">${requestScope.AccountError.nameError}</span>
                    </c:if>
                </div>
                <div class="form-item">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" />
                    <c:if test="${requestScope.AccountError.passwordError != NULL}">
                        <span class="error">${requestScope.AccountError.passwordError}</span>
                    </c:if>
                </div>
                <div class="form-item">
                    <label for="password-confirm">Confirm Password</label>
                    <input type="password" name="passwordConfirm" id="password-confirm" />
                </div>
                <div class="terms-confirm">
                    <label for="terms-confirm" class="checkbox-container">
                        <input type="checkbox" id="terms-confirm" name="cbxTermsConfirm">
                        <span class="checkmark"></span>
                        I agree to the <a href="#">Terms of User</a>
                    </label>
                </div>
                <button type="submit" name="action" value="register">Sign Up</button>
            </form>
        </div>
    </body>
</html>
