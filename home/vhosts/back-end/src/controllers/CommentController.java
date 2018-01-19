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
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import entities.*;
import facades.*;
import utils.APIHelper;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

        /* var */
        boolean error;
        Map<String, String> data;
        Date date = new Date();
        int idUser;
        JsonArray dt = new JsonArray();

        /* Loading route parameter */
        String op = request.getParameter("do");



		switch (op) {

			case "addComment":
			    data = APIHelper.ensureParametersExists(request, response, "idImage", "text");
			    if (data == null) return;

				int idImage = Integer.parseInt(data.get("idImage"));

                idUser = (int) session.getAttribute("idUser");
                if (idUser == 0) {
                    APIHelper.exit(response, true, "Vous devez être connecté pour poster un commentaire");
                } else {
                    int idc = commentFacade.addImageComment(data.get("text"), new Date());
                    commentFacade.addCommentToImage(idc, idImage);
                    commentFacade.addImageCommentToUser(idc, idUser);
                    APIHelper.exit(response, false, "Commentaire bien enregistré");
                }
                break;

			case "editComment":
                data = APIHelper.ensureParametersExists(request, response, "idComment", "text");
                if (data == null) return;

				int idComment = Integer.parseInt(data.get("idComment"));
				String text = data.get("text");
				if (idComment > 0 && text != null) {
					commentFacade.editImageComment(idComment, text, date);
					APIHelper.exit(response, false, "Commentaire modifié");
				}
                break;

			case "deleteComment":
                data = APIHelper.ensureParametersExists(request, response, "idComment");
                if (data == null) return;

				int idDelete = Integer.parseInt(data.get("idComment"));
                commentFacade.deleteImageComment(idDelete);

                APIHelper.exit(response, false, "Commentaire supprimé");
                break;

			case "imageComments":
				data = APIHelper.ensureParametersExists(request, response, "idImage");
				if (data == null) return;

				idImage = Integer.parseInt(data.get("idImage"));
				if (idImage > 0) {
					List <ImageComment> lic = commentFacade.imageCommentsByImage(idImage);
					for (ImageComment ic: lic) {
					    dt.add(ic.toJson());
                    }
					APIHelper.exit(response, false, "ok", dt);
				}
                break;

			case "userComments":
				idUser = Integer.parseInt(request.getParameter("idUser"));
				if (idUser > 0) {
					commentFacade.imageCommentsByUser(idUser);
					List < ImageComment > luc = commentFacade.imageCommentsByUser(idUser);
					String json = new Gson().toJson(luc);
					response.setContentType("application/json");
					response.getWriter().print(json);
				}
                break;

			case "comments":
				List < ImageComment > lc = commentFacade.ImageComments();
				String json = new Gson().toJson(lc);
				response.setContentType("application/json");
				response.getWriter().print(json);
                break;

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