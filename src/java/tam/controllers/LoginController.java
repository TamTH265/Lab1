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
import javax.servlet.http.HttpSession;
import tam.daos.AccountDAO;
import tam.dtos.AccountErrorObject;
import tam.supportMethods.SHA_256;

/**
 *
 * @author hoang
 */
public class LoginController extends HttpServlet {

    private static final String ERROR = "error.jsp";
    private static final String ADMIN = "index.jsp";
    private static final String MEMBER = "index.jsp";
    private static final String INVALID = "login.jsp";

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
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            AccountErrorObject accountErrorObj = new AccountErrorObject();
            boolean isValid = true;
            if (email.length() == 0) {
                accountErrorObj.setEmailError("Email cannot be blank!");
                isValid = false;
            }
            if (password.length() == 0) {
                accountErrorObj.setPasswordError("Password cannot be blank!");
                isValid = false;
            }

            if (isValid) {
                AccountDAO accountDAO = new AccountDAO();
                SHA_256 sha = new SHA_256();
                String encodedPassword = sha.getEncodedString(password);
                String role = accountDAO.handleLogin(email, encodedPassword);
                if (role.equals("failed")) {
                    request.setAttribute("InvalidAccount", "Username or Password is invalid!");
                    url = INVALID;
                } else {
                    HttpSession session = request.getSession();
                    String name = accountDAO.getLoginName(email, encodedPassword);
                    session.setAttribute("EMAIL", email);
                    session.setAttribute("NAME", name);
                    session.setAttribute("ROLE", role);

                    switch (role) {
                        case "Admin":
                            url = ADMIN;
                            break;
                        case "Member":
                            url = MEMBER;
                            break;
                        default:
                            request.setAttribute("ERROR", "Your role is invalid");
                            break;
                    }
                }
            } else {
                request.setAttribute("AccountError", accountErrorObj);
                url = INVALID;
            }
        } catch (Exception e) {
            log("Error at LoginController:" + e.getMessage());
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
