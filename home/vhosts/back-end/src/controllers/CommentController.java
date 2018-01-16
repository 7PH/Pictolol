package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.*;
import facades.*;
import utils.APIHelper;

import java.util.Date;
import java.util.List;
/**
 * Servlet implementation class CommentController
 */
@WebServlet("/Comments")
public class CommentController extends Controller {
	private static final long serialVersionUID = 1L;

	@EJB
	private
	ImageCommentFacade commentFacade;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* init session, etc.. */
        super.doGet(request, response);

        /* Get session instance */
        HttpSession session = request.getSession();

        /* Ensure csrf token is there :) */
        if (! APIHelper.checkCsrf(request, response, session)) {
            APIHelper.exit(response, true, "Le token CSRF est invalide");
            return;
        }

        /* Loading route parameter */
        String op = request.getParameter("do");


		switch (op) {

			case "addComment":
				int idImage = Integer.parseInt(request.getParameter("idImage"));
				String textComment = request.getParameter("text");
				boolean error = true;
				if (session.getAttribute("idUser") != null && idImage > 0 && textComment != null) {
					int idUser = (int) session.getAttribute("idUser");
					Date date = new Date();
					date.getTime();
					int idc = commentFacade.addImageComment(textComment, date);
					commentFacade.addCommentToImage(idc, idImage);
					commentFacade.addImageCommentToUser(idc, idUser);
					error = false;
					String message = "addComment";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "editComment":
				int idComment = Integer.parseInt(request.getParameter("idComment"));
				error = true;
				String text = request.getParameter("text");
				if (idComment > 0 && text != null) {
					Date date = new Date();
					date.getTime();
					commentFacade.editImageComment(idComment, text, date);
					error = false;
					String message = "editComment";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "deleteComment":
				int idDelete = Integer.parseInt(request.getParameter("idComment"));
				error = true;
				if (idDelete > 0) {
					commentFacade.deleteImageComment(idDelete);
					error = false;
					String message = "deleteComment";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "imageComments":
				idImage = Integer.parseInt(request.getParameter("idImage"));
				if (idImage > 0) {
					List < ImageComment > lic = commentFacade.imageCommentsByImage(idImage);
					String json = new Gson().toJson(lic);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

			case "userComments":
				int idUser = Integer.parseInt(request.getParameter("idUser"));
				if (idUser > 0) {
					commentFacade.imageCommentsByUser(idUser);
					List < ImageComment > luc = commentFacade.imageCommentsByUser(idUser);
					String json = new Gson().toJson(luc);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

			case "comments":
				List < ImageComment > lc = commentFacade.ImageComments();
				String json = new Gson().toJson(lc);
				response.setContentType("application/json");
				response.getWriter().print(json);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}