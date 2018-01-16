package controllers;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.*;
import facades.*;
import utils.APIHelper;

@WebServlet("/ImageControleur")
public class ImageControleur extends Controller {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private
	ImageFacade imageFacade;
	
	@EJB
	private
	ImageViewFacade imageViewFacade;
       
	private Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* init session, etc.. */
        super.doGet(request, response);

        /* Get session instance */
        HttpSession session = request.getSession();

        /* Ensure csrf token is there :) */
        if (! APIHelper.checkCsrf(request, response, session) && request.getAttribute("gaveCsrfToken") == null) {
            APIHelper.exit(response, true, "Le token CSRF est invalide");
            return;
        }

        /* Loading route parameter */
        String op = request.getParameter("do");

        /* Data used in the controller */
        JsonObject json = new JsonObject();
        Map<String, String> data;
        User user;

        /* Basic routing */
		switch(op){
		
		/* Category add */
        case "addcategory":
            data = APIHelper.ensureParametersExists(request, response, "description");
            imageFacade.addCategory(data.get("description"));
            APIHelper.exit(response, false, "Vous avez bien créé votre catégorie");
            break;
            
		
		/* Category edit */
        case "editcategory":
            data = APIHelper.ensureParametersExists(request, response, "idc", "description");
            Category category = imageFacade.getCategoryById(Integer.parseInt(data.get("idc")));
            if (category == null) {
                APIHelper.errorExit(response, "Cette categorie n'existe pas");
            }else {
            	imageFacade.editCategory(Integer.parseInt(data.get("idc")),data.get("description"));
            	APIHelper.exit(response, false, "Modification bien effectuée");
            }
            break;
            
            
        /* Category delete */
        case "deletecategory":
            data = APIHelper.ensureParametersExists(request, response, "idc");
            if (session.getAttribute("idUser") == "0") {
                APIHelper.errorExit(response, "Vous n'êtes pas connecté");
            } else {
	            imageFacade.deleteCategory(Integer.parseInt(data.get("idc")));
	            APIHelper.exit(response, false, "Suppression bien effectuée");
            }
            break;
            
       
        /* Categories */
        case "categories":
        	List<Category> categories = imageFacade.categories();
            APIHelper.exit(response, false, "ok", categories);
            break;
			
            
        /* Image add */
        case "addimage":
            data = APIHelper.ensureParametersExists(request, response, "url","title","idc","idu");
            imageFacade.ajoutImage(data.get("url"),data.get("title"),Integer.parseInt(data.get("idc")),Integer.parseInt(data.get("idu")));
            APIHelper.exit(response, false, "Vous avez bien créé votre image");
            break;
            
            
        /* Image edit */
        case "editimage":
            data = APIHelper.ensureParametersExists(request, response, "url","title","idi");
            Image image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Cette image n'existe pas");
            } else if (image.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                APIHelper.errorExit(response, "Cette image ne vous appartient pas");
            } else {
            	imageFacade.editImage(Integer.parseInt(data.get("idi")),data.get("url"),data.get("title"));
                APIHelper.exit(response, false, "Vous avez bien modifié votre image");
            }
            break;
            
            
        /* Image delete */
        case "deleteimage":
            data = APIHelper.ensureParametersExists(request, response,"idi");
            image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Cette image n'existe pas");
            } else if (image.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                APIHelper.errorExit(response, "Cette image ne vous appartient pas");
            } else {
            	imageFacade.deleteImage(Integer.parseInt(data.get("idi")));
                APIHelper.exit(response, false, "Vous avez bien supprimé votre image");
            }
            break;
            
            
        /* Images */
        case "images":
        	List<Image> images = imageFacade.images();
            APIHelper.exit(response, false, "ok", images);
            break;

        
        /* Specific image info */
        case "detailimage":
            data = APIHelper.ensureParametersExists(request, response, "ip","idi","date");
            image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Unable to find this image");
            } else {
            	int isView=imageViewFacade.isView(data.get("ip"), Integer.parseInt(data.get("idi")));
				if(isView==0){
					Date date=null;
					try {
						date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(data.get("date"));
					} catch (ParseException e) {}
					imageViewFacade.addImageView(data.get("ip"),date,Integer.parseInt(data.get("idi")));
				}
				user=image.getUser();
				category=image.getCategory();
				List<Tag> tags=imageFacade.tagsByImage(Integer.parseInt(data.get("idi")));
				int nbrLikes=imageFacade.nbrLikesByImage(Integer.parseInt(data.get("idi")));
				List<ImageComment> comments=imageFacade.imageCommentsByImage(Integer.parseInt(data.get("idi")));
                json.add("image", gson.toJsonTree(image));
                json.add("user", gson.toJsonTree(user));
                json.add("category", gson.toJsonTree(category));
                json.add("tags", gson.toJsonTree(tags));
                json.add("comments", gson.toJsonTree(comments));
                json.add("nbrLikes", gson.toJsonTree(nbrLikes));
                APIHelper.exit(response, false, "ok", json);
            }
            break;
            
            
        /* Category's images */
        case "imagesbycategory":
            data = APIHelper.ensureParametersExists(request, response, "idc");
            category = imageFacade.getCategoryById(Integer.parseInt(data.get("idc")));
            if (category == null) {
                APIHelper.errorExit(response, "Unable to find this category");
            } else {
                images = imageFacade.imagesByCat(Integer.parseInt(data.get("idc")));
                APIHelper.exit(response, false, "ok", images);
            }
            break;
		
			
        /* Add image to category */
        case "addimagetocategory":
            data = APIHelper.ensureParametersExists(request, response,"idc","idi");
            category = imageFacade.getCategoryById(Integer.parseInt(data.get("idc")));
            if (category == null) {
                APIHelper.errorExit(response, "Unable to find this category");
            } else {
	            imageFacade.addImageToCategory(Integer.parseInt(data.get("idc")),Integer.parseInt(data.get("idi")));
	            APIHelper.exit(response, false, "Vous avez bien affecté le catégorie à votre image");
            }
            break;
            
            
        /* Delete image from category */
        case "deleteimagefromcategory":
            data = APIHelper.ensureParametersExists(request, response,"idi");
            image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Cette image n'existe pas");
            } else if (image.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                APIHelper.errorExit(response, "Cette image ne vous appartient pas");
            } else {
            	imageFacade.deleteImageFromCategory(Integer.parseInt(data.get("idi")));
                APIHelper.exit(response, false, "Vous avez bien suuprimé le catégorie de votre image");
            }
            break;
		
      
        /* Image's views */
        case "viewsbyimage":
            data = APIHelper.ensureParametersExists(request, response, "idi");
            image = imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Unable to find this image");
            } else {
            	List<ImageView> views = imageViewFacade.imageViewsByImage(Integer.parseInt(data.get("idi")));
                APIHelper.exit(response, false, "ok", views);
            }
            break;
		

        /* Delete view from image */
        case "deleteviewfromimage":
            data = APIHelper.ensureParametersExists(request, response,"idi");
            image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Cette image n'existe pas");
            } else if (image.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                APIHelper.errorExit(response, "Cette image ne vous appartient pas");
            } else {
            	imageViewFacade.deleteImageViewFromImage(Integer.parseInt(data.get("idi")));
                APIHelper.exit(response, false, "Vous avez bien suuprimé une vue de votre image");
            }
            break;
		

        /* Delete view from image */
        case "deleteviewfromimagebeforedate":
            data = APIHelper.ensureParametersExists(request, response,"idi","date");
            image=imageFacade.getImageById(Integer.parseInt(data.get("idi")));
            if (image == null) {
                APIHelper.errorExit(response, "Cette image n'existe pas");
            } else if (image.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                APIHelper.errorExit(response, "Cette image ne vous appartient pas");
            } else {
            	Date date=null;
    			try {
    				date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(data.get("date"));
    			} catch (ParseException e) {}
                imageViewFacade.deleteImageViewFromImageBeforeDate(Integer.parseInt(data.get("idi")),date);
                APIHelper.exit(response, false, "Vous avez bien suuprimé les vues de votre image avant cette date");
            }
            break;   

            
        /* Search images */
        case "searchimages":
        	data = APIHelper.ensureParametersExists(request, response,"chaine");
        	images = imageFacade.searchByTitle(data.get("chaine"));
            APIHelper.exit(response, false, "ok", images);
            break;    
		
            
        /* Wrong API Call */
        default:
            if (request.getAttribute("gaveCsrfToken") == null) {
                APIHelper.exit(response, true, "Route not found!");
            }
            break;
			
		}
	}

}

