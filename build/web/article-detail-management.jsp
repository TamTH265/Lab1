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
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,700|Roboto:400,500&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="./styles/all.css"/>
        <link rel="stylesheet" href="./styles/bootstrap.min.css" />
        <link rel="stylesheet" href="./styles/header.css" />
        <link rel="stylesheet" href="./styles/footer.css" />
        <link rel="stylesheet" href="./styles/article-detail-management.css" />
    </head>
    <body>
        <c:if test="${sessionScope.ROLE ne 'Admin'}">
            <c:redirect url="login.jsp" />
        </c:if>
        <header>
            <nav class="nav-app">
                <div class="logo">
                    <a href="<c:if test="${sessionScope.ROLE ne 'Admin'}">index.jsp</c:if>
                       <c:if test="${sessionScope.ROLE eq 'Admin'}">admin.jsp</c:if>"><img src="./images/logo.png" alt=""></a>
                    </div>
                    <ul class="nav-menu">
                        <li><a href="<c:if test="${sessionScope.ROLE ne 'Admin'}">index.jsp</c:if>
                           <c:if test="${sessionScope.ROLE eq 'Admin'}">admin.jsp</c:if>">Home</a></li>
                    <c:if test="${sessionScope.ROLE eq 'Member'}"><li><a href="article-posting.jsp">Blog Posting</a></li></c:if>
                        <c:if test="${sessionScope.ROLE ne 'Member' && sessionScope.ROLE ne 'Admin'}">
                        <li><button><a class="header-btn" href="login.jsp">Sign In</a></li>
                        <li><button><a class="header-btn" href="register.jsp">Sign Up</a></li>
                                </c:if>
                                <c:if test="${sessionScope.ROLE eq 'Member' || sessionScope.ROLE eq 'Admin'}">
                        <li><a href="#">Hello, ${sessionScope.NAME}!</a></li>
                        <li><button><a class="header-btn" href="LogoutController">Sign Out</a></button></li>
                                </c:if>
                </ul>
            </nav>
        </header>

        <div class="container">
            <c:if test="${requestScope.BlogDetail != null}">   
                <c:set value="${requestScope.BlogDetail}" var="BlogDetail" />
            </c:if>
            <div class="blog-container">
                <h1 class="blog-title">${BlogDetail.title}</h1>
                <div class="blog-annotation">
                    Posted by <span class="blog-author">${BlogDetail.author}</span> at <span class="blog-posted-time">${BlogDetail.postedTime}</span>
                    <div class="blog-status"><span>Status: </span>${BlogDetail.status}</div>
                </div>
                <div class="short-description">${BlogDetail.shortDescription}</div>
                <div class="blog-content">${BlogDetail.content}</div>

                <c:url var="articleDetailManage" value="MainController">
                    <c:param name="blogID" value="${BlogDetail.blogID}" />
                </c:url>
                <div class="form-container">
                    <c:if test="${BlogDetail.status eq 'New'}">
                        <form action="${articleDetailManage}" method="POST">
                            <button type="submit" name="action" value="approveArticle">Approve</button>
                        </form>
                    </c:if>
                    <c:if test="${BlogDetail.status eq 'New' || BlogDetail.status eq 'Activated'}">
                        <form action="${articleDetailManage}" method="POST">
                            <button type="submit" name="action" value="deleteArticle">Delete</button>
                        </form>
                    </c:if>
                    <c:if test="${BlogDetail.status eq 'Deleted'}">
                        <form action="${articleDetailManage}" method="POST">
                            <button type="submit" name="action" value="restoreArticle">Restore</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </div>        

        <footer class="container-fluid main-footer">
            <div class="row">
                <div class="main-footer-contacts col-lg-4 col-md-12">
                    <h3>Contact Us</h3>
                    <ul class="contacts-list">
                        <li>
                            <p>1 (800) 686-6688</p>
                            <p>blog@gmail.com</p>
                        </li>
                        <li>
                            <p>40 Baria Sreet 133/2</p>
                            <p>NewYork City, US</p>
                        </li>
                        <li>Open hours: 8.00-22.00 Mon-Sat</li>
                    </ul>
                </div>
                <div class="main-footer-legal col-lg-4 col-md-12">
                    <h3>Legal</h3>
                    <ul class="legal-list">
                        <li>
                            <a href="#">Privacy Policy</a>
                        </li>
                        <li>
                            <a href="#">Terms And Conditions</a>
                        </li>
                    </ul>
                </div>
                <div class="main-footer-newsletter col-lg-4 col-md-12">
                    <h3>Our Newsletter</h3>
                    <p>Subscribe to our mailing list to get the updates to your email inbox.</p>
                    <form>
                        <input type="text" placeholder="Email">
                        <button type="button">SUBSCRIBE</button>
                    </form>
                    <ul class="links-list">
                        <li><i class="fab fa-facebook-f"></i></li>
                        <li><i class="fab fa-twitter"></i></li>
                        <li><i class="fab fa-google-plus-g"></i></li>
                        <li><i class="fab fa-instagram"></i></li>
                    </ul>
                </div>
            </div>
            <div class="footer-reserved">
                Copyright ©2019 All rights reserved
            </div>
        </footer>

        <script src="./scripts/all.js"></script>
    </body>
</html>
