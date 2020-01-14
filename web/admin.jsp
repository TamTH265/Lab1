<%-- 
    Document   : admin
    Created on : Jan 10, 2020, 3:39:50 PM
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
        <link rel="stylesheet" href="./styles/admin.css" />
    </head>
    <body>
        <c:if test="${param.searchedContent == null}">
            <c:if test="${!param.action.equals('loadData')}">
                <c:url value="MainController" var="dataLoading"> 
                    <c:param value="loadData" name="action" /> 
                </c:url>
                <c:redirect url="${dataLoading}" />
            </c:if>
        </c:if>

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
                    <li><button><a href="register.jsp">Sign Up</a></button></li>
                </ul>
            </nav>
        </header>

        <div class="container">
            <form action="MainController" method="POST">
                <div class="form-group">
                    <label for="content">Content(REQUIRED)</label>
                    <input id="search-content" name="searchedContent" value="${param.searchedContent}" type="text" class="form-control" placeholder="Input Content..." />
                    <span class="error" id="search-error"></span>
                </div>
                <div class="form-group">
                    <label for="article">Article</label>
                    <input name="searchedArticle" value="${param.searchedArticle}" type="text" class="form-control" id="article" placeholder="Input Article..." />
                </div>
                <div class="form-group">
                    <div>Status</div>
                    <div class="form-check form-check-inline">
                        <input name="searchedStatus" class="form-check-input" type="radio" id="new" value="new" <c:if test="${param.searchedStatus.equals('new')}">checked="checked"</c:if> />
                            <label class="form-check-label" for="new">New</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input name="searchedStatus" class="form-check-input" type="radio" id="activated" value="activated" <c:if test="${param.searchedStatus.equals('activated')}">checked="checked"</c:if> />
                            <label class="form-check-label" for="activated">Activated</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input name="searchedStatus" class="form-check-input" type="radio" id="deleted" value="deleted" <c:if test="${param.searchedStatus.equals('deleted')}">checked="checked"</c:if> />
                            <label class="form-check-label" for="deleted">Deleted</label>
                        </div>
                    </div>
                    <button type="submit" name="action" value="search" id="search-btn">Search</button>
                <c:url value="MainController" var="viewAllBlogs">
                    <c:param value="loadData" name="action" />
                </c:url>
                <a href="${viewAllBlogs}">View All</a>
            </form>
            <c:if test="${requestScope.SearchError != null}">
                <div style="padding: 35px 0 40px 0; color: #f00; font-size: 28px; text-align: center;">${requestScope.SearchError}</div>
            </c:if>

            <c:if test="${requestScope.BlogsData != null}">
                <table class="table table-striped" style=" box-shadow: 0 5px 15px 2px rgba(0, 0, 0, 0.2);">
                    <thead style="background: #131627; color: #fff;">
                        <tr>
                            <th scope="col">No.</th>
                            <th scope="col">Title</th>
                            <th scope="col">Author</th>
                            <th scope="col">Posted Time</th>
                            <th scope="col">Status</th>
                            <th scope="col">Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.BlogsData}" var="blog" varStatus="counter" >
                            <tr>
                                <th scope="row">${counter.count}</th>
                                <td>${blog.title}</td>
                                <td>${blog.author}</td>
                                <td>${blog.postedTime}</td>
                                <td>${blog.status}</td>
                                <td>
                                    <c:url var="handleBlogDetail" value="MainController">
                                        <c:param name="action" value="getBlogDetail" />
                                        <c:param name="blogID" value="${blog.blogID}" />
                                    </c:url>
                                    <a href="${handleBlogDetail}">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="page-container">
                    <c:forEach var = "index" begin = "1" end = "${requestScope.TotalPage}">
                        <c:url value="MainController" var="handlePage">
                            <c:if test="${param.searchedContent == null && param.searchedArticle == null && paramValues.searchedStatus == null}">
                                <c:param value="loadData" name="action" />
                            </c:if>
                            <c:if test="${param.searchedContent != null}">
                                <c:param value="search" name="action" />
                                <c:param name="searchedContent" value="${param.searchedContent}" />
                                <c:param name="searchedArticle" value="${param.searchedArticle}" />
                                <c:param name="searchedStatus" value="${param.searchedStatus}" />
                            </c:if>
                            <c:param value="${index}" name="pg" />
                        </c:url>
                        <a class="page-item" href="${handlePage}">${index}</a>
                    </c:forEach>
                </div>

            </c:if>
            <c:if test="${requestScope.SearchError == null}">
                <c:if test="${requestScope.BlogsData == null}">
                    <div style="color: #f00;">There isn't any data!</div>
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

        <!--        <script src="https://kit.fontawesome.com/c4b1e58fe3.js" crossorigin="anonymous"></script>
                <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
                <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>-->
        <script src="./scripts/search-handling.js"></script>
    </body>
</html>
