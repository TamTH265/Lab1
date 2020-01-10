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
    </body>
</html>