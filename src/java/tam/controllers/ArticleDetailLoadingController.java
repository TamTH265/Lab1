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
import tam.daos.CommentDAO;
import tam.dtos.BlogDTO;
import tam.dtos.CommentDTO;

/**
 *
 * @author hoang
 */
public class ArticleDetailLoadingController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ARTICLEDETAIL = "article-detail.jsp";
    private static final String ARTICLEDETAILMANAGEMENT = "article-detail-management.jsp";

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
        String url = ERROR;
        try {
            int blogID = Integer.parseInt(request.getParameter("blogID"));
            BlogDAO blogDAO = new BlogDAO();
            CommentDAO commentDAO = new CommentDAO();
            BlogDTO blogDetail = blogDAO.getBlogDetailByBlogID(blogID);
            List<CommentDTO> commentsData = commentDAO.getAllCommentsByBlogID(blogID);
            if (blogDetail != null) {
                HttpSession session = request.getSession(false);
                if (session.getAttribute("ROLE") != null) {
                    String role = request.getSession(false).getAttribute("ROLE").toString();
                    if (role.equals("Admin")) {
                        url = ARTICLEDETAILMANAGEMENT;
                    }
                } else {
                    url = ARTICLEDETAIL;
                }
                blogDetail.setBlogID(blogID);
                request.setAttribute("BlogDetail", blogDetail);
                request.setAttribute("CommentsData", commentsData);
            } else {
                url = ERROR;
                request.setAttribute("ERROR", "Loading Blog Detail Failed!");
            }
        } catch (Exception e) {
            log("ERROR at ArticleDetailLoadingController: " + e.getMessage());
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
