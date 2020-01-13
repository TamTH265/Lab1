/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tam.daos.CommentDAO;
import tam.dtos.BlogDTO;
import tam.dtos.CommentDTO;
import tam.dtos.CommentErrorObject;

/**
 *
 * @author hoang
 */
public class CommentPostingController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String SUCCESS = "article-detail.jsp";
    private static final String INVALID = "article-detail.jsp";

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
        try {
            String email = request.getSession(false).getAttribute("EMAIL").toString();
            int blogID = Integer.parseInt(request.getParameter("blogID"));
            String comment = request.getParameter("comment").trim();
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String shortDescription = request.getParameter("shortDescription");
            String author = request.getParameter("author");
            String postedTime = request.getParameter("postedTime");
            CommentDAO commentDAO = new CommentDAO();

            CommentErrorObject commentErrorObj = new CommentErrorObject();
            boolean isValid = true;
            if (comment.length() == 0) {
                commentErrorObj.setContentError("Comment cannot be blank!");
                isValid = false;
            }

            if (isValid) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                if (commentDAO.postComment(email, blogID, comment, dtf.format(now))) {
                    BlogDTO blogDetail = new BlogDTO(title, shortDescription, content, author, postedTime, blogID);
                    request.setAttribute("BlogDetail", blogDetail);

                    List<CommentDTO> commentsData = commentDAO.getAllCommentsByBlogID(blogID);
                    request.setAttribute("CommentsData", commentsData);
                    url = SUCCESS;
                } else {
                    request.setAttribute("ERROR", "Posting Comment Failed!");
                }
            } else {
                request.setAttribute("CommentError", commentErrorObj);
                BlogDTO blogDetail = new BlogDTO(title, shortDescription, content, author, postedTime, blogID);
                request.setAttribute("BlogDetail", blogDetail);
                
                List<CommentDTO> commentsData = commentDAO.getAllCommentsByBlogID(blogID);
                request.setAttribute("CommentsData", commentsData);
                url = INVALID;
            }
        } catch (Exception e) {
            log("ERROR at CommentPostingController: " + e.getMessage());
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
