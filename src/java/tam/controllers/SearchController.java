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
import javax.servlet.http.HttpSession;
import tam.daos.BlogDAO;
import tam.dtos.BlogDTO;
import tam.supportMethods.PagingHandler;

/**
 *
 * @author hoang
 */
public class SearchController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ADMIN = "admin.jsp";
    private static final String INDEX = "index.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        String searchedContent;
        String searchedArticle = "";
        String searchedStatus = null;

        String pg = request.getParameter("pg");
        int numOfBlogsPerPage = 3;
        String url = ERROR;

        try {
            searchedContent = request.getParameter("searchedContent").trim();

            if (session.getAttribute("ROLE") != null) {
                String role = request.getSession(false).getAttribute("ROLE").toString();
                if (role.equals("Admin")) {
                    searchedArticle = request.getParameter("searchedArticle").trim();
                    searchedStatus = request.getParameter("searchedStatus");
                    System.out.println(searchedStatus);
                }
            }

            BlogDAO blogDAO = new BlogDAO();
            PagingHandler pagingHandler = new PagingHandler();
            int blogsTotal = blogDAO.getSearchedBlogsTotal(searchedContent, searchedArticle, searchedStatus);

            if (blogsTotal > 0) {
                int totalPage = pagingHandler.getTotalPage(pg, blogsTotal, numOfBlogsPerPage);
                int page = pagingHandler.getPage(pg);
                if (page > 0 && page <= totalPage) {
                    List<BlogDTO> blogsData = blogDAO.getSearchedBlogsData(searchedContent, searchedArticle, searchedStatus, page, numOfBlogsPerPage);

                    request.setAttribute("BlogsData", blogsData);
                    request.setAttribute("TotalPage", totalPage);
                }
            } else {
                request.setAttribute("SearchError", "No Record Could Be Found!");
            }

            url = INDEX;
            if (session.getAttribute("ROLE") != null) {
                String role = request.getSession(false).getAttribute("ROLE").toString();
                if (role.equals("Admin")) {
                    url = ADMIN;
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
