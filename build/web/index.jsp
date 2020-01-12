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
        <link href="https://fonts.googleapis.com/css?family=Poppins:400,500,700|Roboto:400,500&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link href="./styles/header.css" rel="stylesheet" />
        <link href="./styles/footer.css" rel="stylesheet" />
    </head>
    <body>
        <header>
            <nav class="nav-app">
                <div class="logo">
                    <a href="index.jsp"><img src="./images/logo.png" alt=""></a>
                </div>
                <ul class="nav-menu">
                    <li><a href="index.jsp">Home</a></li>
                    <li><a href="#">Blog</a></li>
                    <li><a href="#">Contact</a></li>
                    <li><a href="#">About</a></li>
                    <li><button><a href="login.jsp">Sign In</a></button></li>
                    <li><button><a href="sign-up.jsp">Sign Up</a></button></li>
                </ul>
            </nav>
        </header>

        <c:if test="${param.searchedContent == null}">
            <c:if test="${param.action == null}">
                <c:url value="MainController" var="dataLoading"> 
                    <c:param value="loadData" name="action" /> 
                </c:url>
                <c:redirect url="${dataLoading}" />
            </c:if>
        </c:if>
        <h1>Index Page</h1>

        <c:url value="MainController" var="viewAllBlogs">
            <c:param value="loadData" name="action" />
        </c:url>
        <div><a href="${viewAllBlogs}">View All</a></div>

        <form action="MainController" method="POST">
            Input here <input type="text" name="searchedContent" value="${param.searchedContent}"/>
            <input type="submit" name="action" value="search">
        </form>
        <c:if test="${requestScope.SearchError != null}">
            <span style="color: #f00;">${requestScope.SearchError}</span>
        </c:if>

        <c:if test="${requestScope.BlogsData != null}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Tile</th>
                        <th>Short Description</th>
                        <th>Author</th>
                        <th>Posted Time</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.BlogsData}" var="blog" varStatus="counter" > 
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                <c:url var="handleBlogDetail" value="MainController">
                                    <c:param name="action" value="getBlogDetail" />
                                    <c:param name="blogID" value="${blog.blogID}" />
                                </c:url>
                                <a href="${handleBlogDetail}">${blog.title}</a>
                            </td>
                            <td>${blog.shortDescription}</td>
                            <td>${blog.author}</td>
                            <td>${blog.postedTime}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <c:forEach var = "index" begin = "1" end = "${requestScope.TotalPage}">
                <c:url value="MainController" var="handlePage">
                    <c:if test="${param.searchedContent == null}">
                        <c:param value="loadData" name="action" />
                    </c:if>
                    <c:if test="${param.searchedContent != null}">
                        <c:param value="search" name="action" />
                        <c:param name="searchedContent" value="${param.searchedContent}" />
                    </c:if>
                    <c:param value="${index}" name="pg" />
                </c:url>
                <a href="${handlePage}">${index}</a>
            </c:forEach>
        </c:if>
        <c:if test="${requestScope.SearchError == null}">
            <c:if test="${requestScope.BlogsData == null}">
                <div style="color: #f00;">There isn't any data!</div>
            </c:if>
        </c:if>

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
<!--                <div class="main-footer-services col-lg-3 col-md-6">
                    <h3>Our Services</h3>
                    <ul class="services-list">
                        <li><a href="#">Special Food, Drink Booking</a></li>
                        <li><a href="#">Special Food, Drink Booking</a></li>
                        <li><a href="#">Special Food, Drink Booking</a></li>
                    </ul>
                </div>-->
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
    </body>
</html>