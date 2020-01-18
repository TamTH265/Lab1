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
        <link rel="stylesheet" href="./styles/all.css"/>
        <link rel="stylesheet" href="./styles/bootstrap.min.css" />
        <link rel="stylesheet" href="./styles/header.css" />
        <link rel="stylesheet" href="./styles/footer.css" />
        <link rel="stylesheet" href="./styles/admin.css" />
    </head>
    <body>
        <c:if test="${sessionScope.ROLE ne 'Admin'}">
            <c:redirect url="login.jsp" />
        </c:if>
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
            <form action="MainController" method="POST" class="search-form">
                <div class="form-row">
                    <div class="form-group col-md-8">
                        <label for="content">Content(REQUIRED)</label>
                        <input id="search-content" name="searchedContent" value="${param.searchedContent}" type="text" class="form-control" placeholder="Input Content..." />
                        <span class="error" id="search-error"></span>
                    </div>
                    <div class="form-group col-md-3 ml-auto">
                        <label>Filter Supporter</label>
                        <div>
                            <div class="form-check form-check-inline">
                                <input name="articleFilter" <c:if test="${param.articleFilter != null}">checked="checked"</c:if> class="form-check-input" type="checkbox" id="article-filter" />
                                    <label class="form-check-label" for="article-filter">By Article</label>
                                </div>
                                <div class="form-check form-check-inline">
                                    <input name="statusFilter" <c:if test="${param.statusFilter != null}">checked="checked"</c:if> class="form-check-input" type="checkbox" id="status-filter" />
                                    <label class="form-check-label" for="status-filter">By Status</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" id="article-filter-container" style="display: none;">
                        <label for="article">Article</label>
                        <input name="searchedArticle" value="${param.searchedArticle}" type="text" class="form-control" id="article" placeholder="Input Article..." />
                </div>
                <div class="form-group" id="status-filter-container" style="display: none;">
                    <div>Status</div>
                    <div class="form-check form-check-inline">
                        <input name="searchedStatus" class="form-check-input st" type="radio" id="new" value="new" <c:if test="${param.searchedStatus.equals('new')}">checked="checked"</c:if> />
                            <label class="form-check-label" for="new">New</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input name="searchedStatus" class="form-check-input st" type="radio" id="activated" value="activated" <c:if test="${param.searchedStatus.equals('activated')}">checked="checked"</c:if> />
                            <label class="form-check-label" for="activated">Activated</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input name="searchedStatus" class="form-check-input st" type="radio" id="deleted" value="deleted" <c:if test="${param.searchedStatus.equals('deleted')}">checked="checked"</c:if> />
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
                <c:url var="deleteArticles" value="MainController">
                    <c:param name="action" value="deleteArticles" />
                </c:url>
                <form action="${deleteArticles}" method="POST" style="text-align: center; display: inline;">
                    <table class="table table-striped" style=" box-shadow: 0 5px 15px 2px rgba(0, 0, 0, 0.2);">
                        <thead style="background: #131627; color: #fff;">
                            <tr>
                                <th scope="col">No.</th>
                                <th scope="col">Title</th>
                                <th scope="col">Author</th>
                                <th scope="col">Posted Time</th>
                                <th scope="col">Status</th>
                                <th scope="col">Detail</th>
                                <th scope="col">Tick</th>
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
                                    <td style="text-align: center;">
                                        <input type="checkbox" name="selectedBlogs" value="${blog.blogID}" />
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <button type="submit" name="action" value="deleteArticles">Delete Selected Items</button>
                </form>

                <div class="page-container">
                    <c:forEach var = "index" begin = "1" end = "${requestScope.TotalPage}">
                        <c:url value="MainController" var="handlePage">
                            <c:if test="${param.searchedContent == null && param.searchedArticle == null && paramValues.searchedStatus == null}">
                                <c:param value="loadData" name="action" />
                            </c:if>
                            <c:if test="${param.searchedContent != null}">
                                <c:param value="search" name="action" />
                                <c:param name="searchedContent" value="${param.searchedContent}" />
                                <c:if test="${param.searchedArticle eq ''}">
                                    <c:param name="searchedArticle" value="${param.searchedArticle}" />
                                </c:if>
                                <c:if test="${param.searchedArticle ne ''}">
                                    <c:param name="searchedArticle" value="${param.searchedArticle}" />
                                    <c:param name="articleFilter" value="${param.articleFilter}" />
                                </c:if>
                                <c:if test="${param.searchedStatus != null}">
                                    <c:param name="searchedStatus" value="${param.searchedStatus}" />
                                    <c:param name="statusFilter" value="${param.statusFilter}" />
                                </c:if>
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

        <script src="./scripts/all.js"></script>
        <script src="./scripts/search-handling.js"></script>
        <script>
            const articleFilter = document.getElementById('article-filter')
            const statusFilter = document.getElementById('status-filter')
            const articleFilterContainer = document.getElementById('article-filter-container')
            const statusFilterContainer = document.getElementById('status-filter-container')
            const article = document.getElementById('article')
            const st = document.getElementsByClassName('st')

            if (${param.articleFilter != null}) {
                articleFilterContainer.style.display = 'block'
            } else {
                articleFilterContainer.style.display = 'none'
            }
            if (${param.statusFilter != null}) {
                statusFilterContainer.style.display = 'block'
            } else {
                statusFilterContainer.style.display = 'none'
            }

            articleFilter.addEventListener('click', () => {
                if (articleFilter.checked) {
                    articleFilterContainer.style.display = 'block'
                } else {
                    articleFilterContainer.style.display = 'none'
                    article.value = ''
                }
            })
            statusFilter.addEventListener('click', () => {
                if (statusFilter.checked) {
                    statusFilterContainer.style.display = 'block'
                } else {
                    statusFilterContainer.style.display = 'none'
                    for (let i = 0; i < st.length; i++) {
                        st[i].checked = false
                    }
                }
            })
        </script>
    </body>
</html>
