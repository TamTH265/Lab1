/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hoang
 */
public class MainController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String REGISTER = "RegisterController";
    private static final String LOGIN = "LoginController";
    private static final String LOGOUT = "LogoutController";
    private static final String ARTICLEPOSTING = "ArticlePostingController";
    private static final String DATALOADING = "DataLoadingController";
    private static final String ARTICLEDETAILLOADING = "ArticleDetailLoadingController";
    private static final String SEARCH = "SearchController";
    private static final String COMMENTPOSTING = "CommentPostingController";
    private static final String ARTICLEDETAILMANAGEMENT = "ArticleDetailManagementController";
    private static final String ACCOUNTACTIVATING = "AccountActivatingController";
    private static final String ARTICLESDELETING = "ArticlesDeletingController";

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
            String action = request.getParameter("action");

            switch (action) {
                case "register":
                    url = REGISTER;
                    break;
                case "login":
                    url = LOGIN;
                    break;
                case "logout":
                    url = LOGOUT;
                    break;
                case "postArticle":
                    url = ARTICLEPOSTING;
                    break;
                case "loadData":
                    url = DATALOADING;
                    break;
                case "getBlogDetail":
                    url = ARTICLEDETAILLOADING;
                    break;
                case "search":
                    url = SEARCH;
                    break;
                case "postComment":
                    url = COMMENTPOSTING;
                    break;
                case "approveArticle":
                    url = ARTICLEDETAILMANAGEMENT;
                    break;
                case "deleteArticle":
                    url = ARTICLEDETAILMANAGEMENT;
                    break;
                case "restoreArticle":
                    url = ARTICLEDETAILMANAGEMENT;
                    break;
                case "verify":
                    url = ACCOUNTACTIVATING;
                    break;
                case "deleteArticles":
                    url = ARTICLESDELETING;
                    break;
                default:
                    request.setAttribute("ERROR", "Your action is invalid");
                    break;
            }
        } catch (Exception e) {
            log("Error at MainController: " + e.getMessage());
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
