package controleurs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import entities.*;
import facades.*;

@WebServlet("/UserControleur")
public class UserControleur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	UserFacade userFacade;
	
	@EJB
	CollectionFacade collectionFacade;
       
    public UserControleur() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		switch(op){
		
		case "login":
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			if(email!=null && email!=null){
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded+="@live.fr";
				if(userFacade.verifyUser(email,encoded)!=null){
					HttpSession session = request.getSession();
					session.setAttribute("email",email);
					request.getRequestDispatcher("index.html").forward(request, response);
				}
			}
			break;
		case "logout":
			HttpSession session = request.getSession();
	        session.invalidate();
	        response.sendRedirect("index.html");
			break;
		case "addu":
			String pseudo=request.getParameter("pseudo");
			email=request.getParameter("email");
			password=request.getParameter("password");
			if(pseudo!=null && email!=null && email!=null){
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded+="@live.fr";
				userFacade.addUser(pseudo,email,encoded);
				List<User> lu = userFacade.users();
				
				String json = new Gson().toJson(lu);
                response.setContentType("application/json");
                response.getWriter().write(json);

				//request.setAttribute("lu", lu);
				//request.getRequestDispatcher("users.html").forward(request, response);
			}
			break;
		case "editu":
			int idu=Integer.parseInt(request.getParameter("idu"));
			pseudo=request.getParameter("pseudo");
			email=request.getParameter("email");
			password=request.getParameter("password");
			if(pseudo!=null && email!=null && email!=null && userFacade.getUserById(idu)!=null){
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded+="@live.fr";
				userFacade.editUser(idu,pseudo,email,encoded);
				List<User> lu = userFacade.users();
				
				//String json = new Gson().toJson(lu);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("lu", lu);
				request.getRequestDispatcher("users.html").forward(request, response);
			}
			break;
		case "deleteu":
			idu=Integer.parseInt(request.getParameter("idu"));
			if(userFacade.getUserById(idu)!=null){
				userFacade.deleteUser(idu);
				List<User> lu = userFacade.users();
				
				//String json = new Gson().toJson(lu);
                //response.setContentType("application/json");
                //response.getWriter().write(json);
				
				request.setAttribute("lu", lu);
				request.getRequestDispatcher("users.html").forward(request, response);
			}
			break;
		case "listu":
			List<User> lu = userFacade.users();
			
			//String json = new Gson().toJson(lu);
            //response.setContentType("application/json");
            //response.getWriter().write(json);
			
			request.setAttribute("lu", lu);
			request.getRequestDispatcher("users.html").forward(request, response);
			break;
			
		case "detailu":
			idu=Integer.parseInt(request.getParameter("idu"));
			User user=userFacade.getUserById(idu);
			if(user!=null){
				List<Collection> collections=collectionFacade.collectionsByUser(idu);
				
				//String jsonu = new Gson().toJson(user);
				//String jsonc = new Gson().toJson(collections);
	            //response.setContentType("application/json");
	            //response.getWriter().write(jsonu);
				//response.getWriter().write(jsonc);
				
				request.setAttribute("user", user);
				request.setAttribute("collections", collections);
				request.getRequestDispatcher("userDetail.html").forward(request, response);
			}
			break;
		case "imbyu":
			idu=Integer.parseInt(request.getParameter("idu"));
			if(userFacade.getUserById(idu)!=null){
				List<Image> images=userFacade.imagesByUser(idu);
				
				//String json = new Gson().toJson(images);
	            //response.setContentType("application/json");
	            //response.getWriter().write(json);
				
				request.setAttribute("images", images);
				request.getRequestDispatcher("imagesByUser.html").forward(request, response);
			}
			break;
			
			case "addc":
				String description=request.getParameter("description");
				if(description!=null){
					collectionFacade.addCollection(description);
					List<Collection> lc = collectionFacade.collections();
					
					//String json = new Gson().toJson(lc);
		            //response.setContentType("application/json");
		            //response.getWriter().write(json);
					
					request.setAttribute("lc", lc);
					request.getRequestDispatcher("collections.html").forward(request, response);
				}
				break;
			case "editc":
				int idc=Integer.parseInt(request.getParameter("idc"));
				description=request.getParameter("description");
				if(description!=null && collectionFacade.getCollectionById(idc)!=null){
					collectionFacade.editCollection(idc,description);
					List<Collection> lc = collectionFacade.collections();
					
					//String json = new Gson().toJson(lc);
		            //response.setContentType("application/json");
		            //response.getWriter().write(json);
					
					request.setAttribute("lc", lc);
					request.getRequestDispatcher("collections.html").forward(request, response);
				}
				break;
			case "deletec":
				idc=Integer.parseInt(request.getParameter("idu"));
				if(collectionFacade.getCollectionById(idc)!=null){
					collectionFacade.deleteCollection(idc);
					List<Collection> lc = collectionFacade.collections();
					
					//String json = new Gson().toJson(lc);
		            //response.setContentType("application/json");
		            //response.getWriter().write(json);
					
					request.setAttribute("lc", lc);
					request.getRequestDispatcher("collections.html").forward(request, response);
				}
				break;
			case "listc":
				List<Collection> lc = collectionFacade.collections();
				
				//String json = new Gson().toJson(lc);
	            //response.setContentType("application/json");
	            //response.getWriter().write(json);
				
				request.setAttribute("lc", lc);
				request.getRequestDispatcher("collections.html").forward(request, response);
				break;
			case "detailc":
				idc=Integer.parseInt(request.getParameter("idc"));
				Collection collection=collectionFacade.getCollectionById(idc);
				if(collection!=null){
					user=collection.getUser();
					List<Image> images=collectionFacade.imagesByCollection(idc);
					
					//String jsonc = new Gson().toJson(collection);
					//String jsonu = new Gson().toJson(user);
					//String jsoni = new Gson().toJson(images);
		            //response.setContentType("application/json");
		            //response.getWriter().write(jsonc);
					//response.getWriter().write(jsonu);
					//response.getWriter().write(jsoni);
					
					request.setAttribute("collection", collection);
					request.setAttribute("user", user);
					request.setAttribute("images", images);
					request.getRequestDispatcher("userDetail.html").forward(request, response);
				}
				break;
			
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
