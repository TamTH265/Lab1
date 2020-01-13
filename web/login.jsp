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
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,700|Roboto:400,500&display=swap"
              rel="stylesheet" />
        <link rel="stylesheet" href="./styles/login.css" />
    </head>
    <body>
        <!--        <h1>Login Page</h1>-->
        <!--        <form action="MainController" method="POST">
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
    </form>-->



        <div class="login-page">
            <form class="login-form" id="demo" method="POST" action="LoginController">
                <h3 class="form-title">LOGIN</h3>
                <div class="form-input">
                    <div>Email</div>
                    <span class="icon"><i class="fas fa-user"></i></span>
                    <input type="text" name="email" class="input-field" value="${param.email}" />
                    <c:if test="${requestScope.AccountError.emailError != NULL}"><span class="error">${requestScope.AccountError.emailError}</span></c:if>
                    <c:if test="${requestScope.InvalidAccount != NULL}"><span class="error">${requestScope.InvalidAccount}</span></c:if>
                    </div>
                    <div class="form-input">
                        <div>PASSWORD</div>
                        <span class="icon"><i class="fas fa-key"></i></span>
                        <input type="password" name="password" class="input-field" />
                    <c:if test="${requestScope.AccountError.passwordError != NULL}"><span class="error">${requestScope.AccountError.passwordError}</span></c:if>
                </div>
                <div class="new-account-creation">
                    <span>If you're a new one, <a href="register.jsp">Create new account!</a></span>
                </div>
                <button type="submit" name="action" value="Login">Login</button>
            </form>
        </div>

        <script src="https://kit.fontawesome.com/c4b1e58fe3.js" crossorigin="anonymous"></script>
    </body>
</html>
