/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tam.daos.BlogDAO;
import tam.dtos.BlogDTO;
import tam.supportMethods.PagingHandler;

/**
 *
 * @author hoang
 */
public class SearchController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ARTICLEPAGE = "articlesPage.jsp";
    private static final String INVALID = "articlesPage.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        String searchedContent = request.getParameter("searchedContent");
        String pg = request.getParameter("pg");
        int numOfBlogsPerPage = 3;
        try {
            if (searchedContent.equals("")) {
                request.setAttribute("SearchError", "Please Input Value To Search");
                url = INVALID;
            } else {
                BlogDAO blogDAO = new BlogDAO();
                PagingHandler pagingHandler = new PagingHandler();
                int blogsTotal = blogDAO.getSearchedBlogsByContentTotal(searchedContent);

                if (blogsTotal > 0) {
                    int totalPage = pagingHandler.getTotalPage(pg, blogsTotal, numOfBlogsPerPage);
                    int page = pagingHandler.getPage(pg);
                    if (page > 0 && page <= totalPage) {
                        List<BlogDTO> blogsData = blogDAO.searchByContent(searchedContent, page, numOfBlogsPerPage);

                        request.setAttribute("BlogsData", blogsData);
                        request.setAttribute("TotalPage", totalPage);
                    }
                    url = ARTICLEPAGE;
                } else {
                    request.setAttribute("SearchError", "There isn't any results!");
                    url = ARTICLEPAGE;
                }
            }
        } catch (Exception e) {
            log("ERROR at SearchController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
