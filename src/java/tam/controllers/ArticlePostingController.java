/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tam.daos.BlogDAO;
import tam.dtos.BlogErrorObject;

/**
 *
 * @author hoang
 */
public class ArticlePostingController extends HttpServlet {

	private static final String SUCCESS = "index.jsp";
	private static final String ERROR = "error.jsp";
	private static final String INVALID = "articlePosting.jsp";

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
			String title = request.getParameter("title");
			String shortDescription = request.getParameter("shortDescription");
			String content = request.getParameter("content");

			BlogErrorObject blogErrorObj = new BlogErrorObject();
			boolean isValid = true;
			if (title.length() == 0) {
				blogErrorObj.setTitleError("Title cannot be blank!");
				isValid = false;
			}
			if (shortDescription.length() == 0) {
				blogErrorObj.setShortDescriptionError("Short Description cannot be blank!");
				isValid = false;
			}
			if (content.length() == 0) {
				blogErrorObj.setContentError("Content cannot be blank!");
				isValid = false;
			}

			if (isValid) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				BlogDAO blogDAO = new BlogDAO();
				if (blogDAO.postArticle(email, title, shortDescription, content, dtf.format(now))) {
				url = SUCCESS;
				} else {
					request.setAttribute("ERROR", "Posting Article Failed!");
				}
			} else {
				request.setAttribute("BlogError", blogErrorObj);
				url = INVALID;
			}
		} catch (Exception e) {
			log("Error at ArticlePostingController: " + e.getMessage());
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
