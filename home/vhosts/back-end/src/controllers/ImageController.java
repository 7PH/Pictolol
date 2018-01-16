package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

@WebServlet("/Images")
public class ImageController extends Controller {
	private static final long serialVersionUID = 1L;

	@EJB
	private
	ImageFacade imageFacade;

	@EJB
	private
	ImageViewFacade imageViewFacade;

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

			case "addcat":
				String cat = request.getParameter("cat");
				boolean error = true;
				String message = "error";
				if (cat != null) {
					imageFacade.addCategory(cat);
					error = false;
					message = "add";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "editcat":
				int idc = Integer.parseInt(request.getParameter("idc"));
				cat = request.getParameter("cat");
				error = true;
				message = "error";
				if (cat != null && imageFacade.getCategoryById(idc) != null) {
					imageFacade.editCategory(idc, cat);
					error = false;
					message = "edit";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "deletecat":
				idc = Integer.parseInt(request.getParameter("idc"));
				error = true;
				message = "error";
				if (imageFacade.getCategoryById(idc) != null) {
					imageFacade.deleteCategory(idc);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "listc":
				List < Category > lc = imageFacade.categories();
				String json = new Gson().toJson(lc);
				response.setContentType("application/json");
				response.getWriter().print(json);
				break;

			case "addim":
				String url = request.getParameter("url");
				String title = request.getParameter("title");
				idc = Integer.parseInt(request.getParameter("idc"));
				int idu = Integer.parseInt(request.getParameter("idu"));
				error = true;
				message = "error";
				if (url != null && title != null && imageFacade.getUserById(idu) != null && imageFacade.getCategoryById(idc) != null) {
					imageFacade.ajoutImage(url, title, idc, idu);
					error = false;
					message = "add";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "editim":
				url = request.getParameter("url");
				title = request.getParameter("title");
				int idi = Integer.parseInt(request.getParameter("idi"));
				error = true;
				message = "error";
				if (url != null && title != null && imageFacade.getImageById(idi) != null) {
					imageFacade.editImage(idi, url, title);
					error = false;
					message = "edit";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "deleteim":
				idi = Integer.parseInt(request.getParameter("idi"));
				error = true;
				message = "error";
				if (imageFacade.getImageById(idi) != null) {
					imageFacade.deleteImage(idi);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "listim":
				List < Image > li = imageFacade.images();
				json = new Gson().toJson(li);
				response.setContentType("application/json");
				response.getWriter().print(json);
				break;
			case "detailim":
				String ip = request.getParameter("ip");
				idi = Integer.parseInt(request.getParameter("idi"));
				String mystring = request.getParameter("date");
				Image image = imageFacade.getImageById(idi);
				if (ip != null && mystring != null && image != null) {
					int isView = imageViewFacade.isView(ip, idi);
					if (isView == 0) {
						Date date = null;
						try {
							date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mystring);
						} catch (ParseException e) {}
						imageViewFacade.addImageView(ip, date, idi);
					}
					User user = image.getUser();
					Category category = image.getCategory();
					List < Tag > tags = imageFacade.tagsByImage(idi);
					int nbrLikes = imageFacade.nbrLikesByImage(idi);
					List < ImageComment > comments = imageFacade.imageCommentsByImage(idi);
					JsonObject j = new JsonObject();
					j.addProperty("image", image.toString());
					j.addProperty("user", user.toString());
					j.addProperty("category", category.toString());
					j.addProperty("tags", tags.toString());
					j.addProperty("comments", comments.toString());
					j.addProperty("nbrLikes", nbrLikes);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;

			case "listimbycat":
				idc = Integer.parseInt(request.getParameter("idc"));
				if (imageFacade.getCategoryById(idc) != null) {
					li = imageFacade.imagesByCat(idc);
					json = new Gson().toJson(li);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}
				break;
			case "deleteimfromcat":
				idi = Integer.parseInt(request.getParameter("idi"));
				error = true;
				message = "error";
				if (imageFacade.getImageById(idi) != null) {
					imageFacade.deleteImageFromCategory(idi);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "addimtocat":
				idc = Integer.parseInt(request.getParameter("idc"));
				idi = Integer.parseInt(request.getParameter("idi"));
				error = true;
				message = "error";
				if (imageFacade.getImageById(idi) != null && imageFacade.getCategoryById(idc) != null) {
					imageFacade.addImageToCategory(idi, idc);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;

			case "imviewbyim":
				idi = Integer.parseInt(request.getParameter("idi"));
				if (imageFacade.getImageById(idi) != null) {
					List < ImageView > liv = imageViewFacade.imageViewsByImage(idi);
					json = new Gson().toJson(liv);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}
				break;
			case "delviewfromim":
				idi = Integer.parseInt(request.getParameter("idi"));
				error = true;
				message = "error";
				if (imageFacade.getImageById(idi) != null) {
					imageViewFacade.deleteImageViewFromImage(idi);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "delviewfromimbeforedate":
				idi = Integer.parseInt(request.getParameter("idi"));
				mystring = request.getParameter("date");
				error = true;
				message = "error";
				if (imageFacade.getImageById(idi) != null && mystring != null) {
					Date date = null;
					try {
						date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mystring);
					} catch (ParseException e) {}
					imageViewFacade.deleteImageViewFromImageBeforeDate(idi, date);
					error = false;
					message = "delete";
					JsonObject j = new JsonObject();
					j.addProperty("error", error);
					j.addProperty("message", message);
					response.setContentType("application/json");
					response.getWriter().print(j);
				}
				break;
			case "search":
				String chaine = request.getParameter("chaine");
				error = true;
				message = "error";
				if (chaine != null) {
					li = imageFacade.searchByTitle(chaine);
					if (li.size() > 0) {
						error = false;
						message = "search";
						JsonObject j = new JsonObject();
						j.addProperty("images", li.toString());
						j.addProperty("error", error);
						j.addProperty("message", message);
						response.setContentType("application/json");
						response.getWriter().print(j);
					} else {
						JsonObject j = new JsonObject();
						j.addProperty("error", error);
						j.addProperty("message", message);
						response.setContentType("application/json");
						response.getWriter().print(j);
					}
				}
				break;

		}
	}
}