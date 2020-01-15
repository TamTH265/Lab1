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
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,700|Roboto:400,500,700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="./styles/header.css" rel="stylesheet" />
        <link href="./styles/footer.css" rel="stylesheet" />
        <link rel="stylesheet" href="./styles/article-detail.css" />
    </head>
    <body>
        <header>
            <nav class="nav-app">
                <div class="logo">
                    <a href="index.jsp"><img src="./images/logo.png" alt=""></a>
                </div>
                <ul class="nav-menu">
                    <li><a href="
                           <c:if test="${sessionScope.ROLE ne 'Admin'}">index.jsp</c:if>
                           <c:if test="${sessionScope.ROLE eq 'Admin'}">admin.jsp</c:if>
                               ">Home</a></li>
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
                <div class="blog-container">
                    <h1 class="blog-title">${BlogDetail.title}</h1>
                    <div class="blog-annotation">
                        Posted by <span class="blog-author">${BlogDetail.author}</span> at <span class="blog-posted-time">${BlogDetail.postedTime}</span>
                    </div>
                    <div class="blog-content">${BlogDetail.content}</div>
                    <c:if test="${sessionScope.ROLE eq 'Member'}">
                        <c:url value="MainController" var="postComment">
                            <c:param value="${BlogDetail.title}" name="title" />
                            <c:param value="${BlogDetail.content}" name="content" />
                            <c:param value="${BlogDetail.author}" name="author" />
                            <c:param value="${BlogDetail.postedTime}" name="postedTime" />
                            <c:param value="${BlogDetail.blogID}" name="blogID" />
                        </c:url>
                    </c:if>
                    <c:if test="${sessionScope.ROLE ne 'Member'}">
                        <c:url value="login.jsp" var="postComment"></c:url>
                    </c:if>
                    <form action="${postComment}" method="POST">
                        <input id="comment-posting" type="text" class="comment-posting" name="comment" placeholder="Add a comment..."/>
                        <button disabled="disabled" id="posting-btn" class="comment-btn" type="submit" name="action" value="postComment">Add Comment</button>
                    </form>
                </div>

                <c:if test="${requestScope.CommentsData != null}">
                    <div class="comment-list-container">
                        <c:forEach items="${requestScope.CommentsData}" var="comment">
                            <div class="comment-item">
                                <div class="comment-annotation">
                                    <span class="comment-name">${comment.userName}</span>
                                    <span class="comment-time">${comment.commentTime}</span>
                                </div>
                                <div class="comment-content">${comment.content}</div>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </c:if>
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

        <script src="https://kit.fontawesome.com/c4b1e58fe3.js" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
        <script src="./scripts/comment-posting.js"></script>
    </body>
</html>
