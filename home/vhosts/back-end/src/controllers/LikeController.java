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
@WebServlet("/Likes")
public class LikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	ImageLikeFacade likeFacade;

	public LikeController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			case "addLike":
				int idImage = Integer.parseInt(request.getParameter("idImage"));
				boolean error = true;
				if (session.getAttribute("idUser") != null && idImage > 0) {
					int idUser = (int) session.getAttribute("idUser");
					Date date = new Date();
					date.getTime();
					int idc = likeFacade.addImageLike(date);
					likeFacade.addLikeToImage(idc, idImage);
					likeFacade.addImageLikeToUser(idc, idUser);
					error = false;
					String message = "addLike";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "deleteLike":
				int idDelete = Integer.parseInt(request.getParameter("idLike"));
				error = true;
				if (idDelete > 0) {
					likeFacade.deleteImageLike(idDelete);
					error = false;
					String message = "deleteLike";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "imageLikes":
				idImage = Integer.parseInt(request.getParameter("idLike"));
				if (idImage > 0) {
					List < ImageLike > lic = likeFacade.imageLikesByImage(idImage);
					String json = new Gson().toJson(lic);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

			case "userLikes":
				int idUser = Integer.parseInt(request.getParameter("idUser"));
				if (idUser > 0) {
					likeFacade.imageLikesByUser(idUser);
					List < ImageLike > luc = likeFacade.imageLikesByUser(idUser);
					String json = new Gson().toJson(luc);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

			case "likes":
				List < ImageLike > lc = likeFacade.ImageLike();
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