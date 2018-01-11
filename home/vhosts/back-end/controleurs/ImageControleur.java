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
		String op=request.getParameter("op");
		switch(op){
		
		case "addcat":
			String cat=request.getParameter("cat");
			if(cat!=null){
				imageFacade.addCategory(cat);
				List<Category> lc = imageFacade.categories();
				
				String json = new Gson().toJson(lc);
                response.setContentType("application/json");
                response.getWriter().write(json);
				
				//request.setAttribute("lc", lc);
				//request.getRequestDispatcher("categories.html").forward(request, response);
			}
			break;
		case "editcat":
			int idc=Integer.parseInt(request.getParameter("idc"));
			cat=request.getParameter("cat");
			if(cat!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.editCategory(idc,cat);
				List<Category> lc = imageFacade.categories();
				
				//String json = new Gson().toJson(lc);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("lc", lc);
				request.getRequestDispatcher("categories.html").forward(request, response);
			}
			break;
		case "deletecat":
			idc=Integer.parseInt(request.getParameter("idc"));
			if(imageFacade.getCategoryById(idc)!=null){
				imageFacade.deleteCategory(idc);
				List<Category> lc = imageFacade.categories();
				
				//String json = new Gson().toJson(lc);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("lc", lc);
				request.getRequestDispatcher("categories.html").forward(request, response);
			}
			break;
		case "listc":
			List<Category> lc = imageFacade.categories();
			
			//String json = new Gson().toJson(lc);
            //response.setContentType("application/json");
            //response.getWriter().write(json);
			
			request.setAttribute("lc", lc);
			request.getRequestDispatcher("categories.html").forward(request, response);
			break;
			
		case "addim":
			String url=request.getParameter("url");
			String title=request.getParameter("title");
			idc=Integer.parseInt(request.getParameter("idc"));
			int idu=Integer.parseInt(request.getParameter("idu"));
			if(url!=null && title!=null && imageFacade.getUserById(idu)!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.ajoutImage(url,title,idc,idu);
				List<Image> li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "editim":
			url=request.getParameter("url");
			title=request.getParameter("title");
			int idi=Integer.parseInt(request.getParameter("idi"));
			if(url!=null && title!=null && imageFacade.getImageById(idi)!=null){
				imageFacade.editImage(idi,url,title);
				List<Image> li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "deleteim":
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null){
				imageFacade.deleteImage(idi);
				List<Image> li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "listim":
			List<Image> li = imageFacade.images();
			request.setAttribute("li", li);
			request.getRequestDispatcher("images.html").forward(request, response);
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
				
				//String jsoni = new Gson().toJson(image);
				//String jsonu = new Gson().toJson(user);
				//String jsonc = new Gson().toJson(category);
				//String jsonco = new Gson().toJson(comments);
				//String jsont = new Gson().toJson(tags);
				//String jsonl = new Gson().toJson(nbrLikes);
	            //response.setContentType("application/json");
	            //response.getWriter().write(jsoni);
				//response.getWriter().write(jsonu);
				//response.getWriter().write(jsonc);
				//response.getWriter().write(jsonco);
				//response.getWriter().write(jsont);
				//response.getWriter().write(jsonl);
				
				request.setAttribute("image", image);
				request.setAttribute("user", user);
				request.setAttribute("category", category);
				request.setAttribute("comments", comments);
				request.setAttribute("tags", tags);
				request.setAttribute("nbrLikes", nbrLikes);
				request.getRequestDispatcher("imageDetail.html").forward(request, response);
			}
			break;
			
		case "listimbycat":
			idc=Integer.parseInt(request.getParameter("idc"));
			if(imageFacade.getCategoryById(idc)!=null){
				li = imageFacade.imagesByCat(idc);
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("imagesbycat.html").forward(request, response);
			}
			break;
		case "deleteimfromcat":
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null){
				imageFacade.deleteImageFromCategory(idi);
				li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "addimtocat":
			idc=Integer.parseInt(request.getParameter("idc"));
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null && imageFacade.getCategoryById(idc)!=null){
				imageFacade.addImageToCategory(idi,idc);
				li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
			
		case "imviewbyim":
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null){
				List<ImageView> liv = imageViewFacade.imageViewsByImage(idi);
				
				//String json = new Gson().toJson(liv);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("liv", liv);
				request.getRequestDispatcher("viewsByImage.html").forward(request, response);
			}
			break;
		case "delviewfromim":
			idi=Integer.parseInt(request.getParameter("idi"));
			if(imageFacade.getImageById(idi)!=null){
				imageViewFacade.deleteImageViewFromImage(idi);
				li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "delviewfromimbeforedate":
			idi=Integer.parseInt(request.getParameter("idi"));
			mystring = request.getParameter("date");
			if(imageFacade.getImageById(idi)!=null && mystring!=null){
				Date date=null;
				try {
					date = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(mystring);
				} catch (ParseException e) {}
				imageViewFacade.deleteImageViewFromImageBeforeDate(idi,date);
				li = imageFacade.images();
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
		case "search":
			String chaine = request.getParameter("chaine");
			if(chaine!=null){
				li=imageFacade.searchByTitle(chaine);
				
				//String json = new Gson().toJson(li);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("li", li);
				request.getRequestDispatcher("images.html").forward(request, response);
			}
			break;
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
