package controleurs;

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

import com.google.gson.Gson;

import entities.*;
import facades.*;

@WebServlet("/ImageImView")
public class ImageControleur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	ImageFacade imageFacade;
	
	@EJB
	ImageViewFacade imageViewFacade;
       
    public ImageControleur() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("do");
		switch(op){
		
		case "addcat":
			String cat=request.getParameter("cat");
			boolean error=true;
			String message="error";
			if(cat!=null){
				imageFacade.addCategory(cat);
				error=false;
				message="add";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "editcat":
			int idc=Integer.parseInt(request.getParameter("idc"));
			cat=request.getParameter("cat");
			error=true;
			message="error";
			if(cat!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.editCategory(idc,cat);
				error=false;
				message="edit";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "deletecat":
			idc=Integer.parseInt(request.getParameter("idc"));
			error=true;
			message="error";
			if(imageFacade.getCategoryById(idc)!=null){
				imageFacade.deleteCategory(idc);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "listc":
			List<Category> lc = imageFacade.categories();
			String json = new Gson().toJson(lc);
            response.setContentType("application/json");
            response.getWriter().print(json);
			break;
			
		case "addim":
			String url=request.getParameter("url");
			String title=request.getParameter("title");
			idc=Integer.parseInt(request.getParameter("idc"));
			int idu=Integer.parseInt(request.getParameter("idu"));
			error=true;
			message="error";
			if(url!=null && title!=null && imageFacade.getUserById(idu)!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.ajoutImage(url,title,idc,idu);
				error=false;
				message="add";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "editim":
			url=request.getParameter("url");
			title=request.getParameter("title");
			int idi=Integer.parseInt(request.getParameter("idi"));
			error=true;
			message="error";
			if(url!=null && title!=null && imageFacade.getImageById(idi)!=null){
				imageFacade.editImage(idi,url,title);
				error=false;
				message="edit";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "deleteim":
			idi=Integer.parseInt(request.getParameter("idi"));
			error=true;
			message="error";
			if(imageFacade.getImageById(idi)!=null){
				imageFacade.deleteImage(idi);
				List<Image> li = imageFacade.images();
				json = new Gson().toJson(li);
                response.setContentType("application/json");
                response.getWriter().print(json);
			}
			break;
		case "listim":
			List<Image> li = imageFacade.images();
			json = new Gson().toJson(li);
            response.setContentType("application/json");
            response.getWriter().print(json);
			break;
		case "detailim":
			String ip=request.getParameter("ip");
			idi=Integer.parseInt(request.getParameter("idi"));
			String mystring = request.getParameter("date");
			Image image=imageFacade.getImageById(idi);
			if(ip!=null && mystring!=null && image!=null){
				int isView=imageViewFacade.isView(ip, idi);
				if(isView==0){
					Date date=null;
					try {
						date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mystring);
					} catch (ParseException e) {}
					imageViewFacade.addImageView(ip,date,idi);
				}
				User user=image.getUser();
				Category category=image.getCategory();
				List<Tag> tags=imageFacade.tagsByImage(idi);
				int nbrLikes=imageFacade.nbrLikesByImage(idi);
				List<ImageComment> comments=imageFacade.imageCommentsByImage(idi);
				String jsoni = new Gson().toJson(image);
				String jsonu = new Gson().toJson(user);
				String jsonc = new Gson().toJson(category);
				String jsonco = new Gson().toJson(comments);
				String jsont = new Gson().toJson(tags);
				String jsonl = new Gson().toJson(nbrLikes);
	            response.setContentType("application/json");
	            response.getWriter().print(jsoni);
				response.getWriter().print(jsonu);
				response.getWriter().print(jsonc);
				response.getWriter().print(jsonco);
				response.getWriter().print(jsont);
				response.getWriter().print(jsonl);
			}
			break;
			
		case "listimbycat":
			idc=Integer.parseInt(request.getParameter("idc"));
			if(imageFacade.getCategoryById(idc)!=null){
				li = imageFacade.imagesByCat(idc);
				json = new Gson().toJson(li);
                response.setContentType("application/json");
                response.getWriter().print(json);
			}
			break;
		case "deleteimfromcat":
			idi=Integer.parseInt(request.getParameter("idi"));
			error=true;
			message="error";
			if(imageFacade.getImageById(idi)!=null){
				imageFacade.deleteImageFromCategory(idi);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "addimtocat":
			idc=Integer.parseInt(request.getParameter("idc"));
			idi=Integer.parseInt(request.getParameter("idi"));
			error=true;
			message="error";
			if(imageFacade.getImageById(idi)!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.addImageToCategory(idi,idc);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
			
		case "imviewbyim":
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null){
				List<ImageView> liv = imageViewFacade.imageViewsByImage(idi);
				json = new Gson().toJson(liv);
                response.setContentType("application/json");
                response.getWriter().print(json);
			}
			break;
		case "delviewfromim":
			idi=Integer.parseInt(request.getParameter("idi"));
			error=true;
			message="error";
			if(imageFacade.getImageById(idi)!=null){
				imageViewFacade.deleteImageViewFromImage(idi);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "delviewfromimbeforedate":
			idi=Integer.parseInt(request.getParameter("idi"));
			mystring = request.getParameter("date");
			error=true;
			message="error";
			if(imageFacade.getImageById(idi)!=null && mystring!=null){
				Date date=null;
				try {
					date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mystring);
				} catch (ParseException e) {}
				imageViewFacade.deleteImageViewFromImageBeforeDate(idi,date);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "search":
			String chaine = request.getParameter("chaine");
			error=true;
			message="error";
			if(chaine!=null){
				li=imageFacade.searchByTitle(chaine);
				if(li.size()>0){
					error=false;
					message="search";
					json = new Gson().toJson(li);
					String jsone = new Gson().toJson(error);
					String jsonm = new Gson().toJson(message);
	                response.setContentType("application/json");
	                response.getWriter().print(json);
	                response.getWriter().print(jsone);
	                response.getWriter().print(jsonm);
				}
				else{
					String jsone = new Gson().toJson(error);
					String jsonm = new Gson().toJson(message);
	                response.setContentType("application/json");
	                response.getWriter().print(jsone);
	                response.getWriter().print(jsonm);
				}
			}
			break;
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
