package controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.*;
import facades.*;
import java.util.Date;
import java.util.List;
/**
 * Servlet implementation class CommentController
 */
@WebServlet("/Tags")
public class TagController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	TagFacade tagFacade;

	public TagController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = request.getParameter("do");
		switch (op) {

			case "addTag":
				int idImage = Integer.parseInt(request.getParameter("idImage"));
				String textTag = request.getParameter("textTag");
				boolean error = true;
				if (idImage > 0 && textTag != null) {
					int idt = tagFacade.addTag(textTag);
					tagFacade.addTagToImage(idt, idImage);
					error = false;
					String message = "addTag";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "editComment":
				int idTag = Integer.parseInt(request.getParameter("idTag"));
				textTag = request.getParameter("textTag");
				error = true;
				String tag = request.getParameter("tag");
				if (idTag > 0 && tag != null) {
					Date date = new Date();
					date.getTime();
					tagFacade.editTag(idTag, textTag);
					error = false;
					String message = "editTag";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "deleteTag":
				idTag = Integer.parseInt(request.getParameter("idTag"));
				error = true;
				if (idTag > 0) {
					tagFacade.deleteTag(idTag);
					error = false;
					String message = "deleteTag";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}

			case "imageTags":
				idImage = Integer.parseInt(request.getParameter("idImage"));
				if (idImage > 0) {
					List < Tag > lic = tagFacade.tagsByImage(idImage);
					String json = new Gson().toJson(lic);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

			case "comments":
				List < Tag > lc = tagFacade.tags();
				String json = new Gson().toJson(lc);
				response.setContentType("application/json");
				response.getWriter().print(json);

			case "tagImages":
				idTag = Integer.parseInt(request.getParameter("idTag"));
				if (idTag > 0) {
					List < Image > lic = tagFacade.imagesByTag(idTag);
					json = new Gson().toJson(lic);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}

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