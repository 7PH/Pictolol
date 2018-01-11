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
		String op=request.getParameter("do");
		switch(op){
		
		case "signin":
			String pseudo=request.getParameter("pseudo");
			String password=request.getParameter("password");
			boolean error=true;
			String message="error";
			if(pseudo!=null && password!=null){
				password+="@live.fr";
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded=encoded.substring(0,encoded.lastIndexOf('='));
				User user=userFacade.verifyUser(pseudo,encoded);
				if(user!=null){
					HttpSession session = request.getSession();
					session.setAttribute("idUser",user.getId());
					error=false;
					message="signin";
				}
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "fetch":
			HttpSession session = request.getSession();
			User user=null;
			if(session.getAttribute("idUser")!=null){
				int idUser=(int) session.getAttribute("idUser");
				user=userFacade.getUserById(idUser);
			}
			else{
				user=new User();
				user.setId(0);
				user.setPseudo("*Guest");
			}
			String json = new Gson().toJson(user);
            response.setContentType("application/json");
            response.getWriter().print(json);
			break;
		case "logout":
			message="logout";
			session = request.getSession();
	        session.invalidate();
	        json = new Gson().toJson(message);
            response.setContentType("application/json");
            response.getWriter().print(json);
			break;
		case "register":
			pseudo=request.getParameter("pseudo");
			String email=request.getParameter("email");
			password=request.getParameter("password");
			error=true;
			message="error";
			if(pseudo!=null && email!=null && password!=null){
				password+="@live.fr";
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded=encoded.substring(0,encoded.lastIndexOf('='));
				userFacade.addUser(pseudo,email,encoded);
				error=false;
				message="register";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "editu":
			int idu=Integer.parseInt(request.getParameter("idu"));
			pseudo=request.getParameter("pseudo");
			email=request.getParameter("email");
			password=request.getParameter("password");
			password+="@live.fr";
			error=true;
			message="error";
			if(pseudo!=null && email!=null && email!=null && userFacade.getUserById(idu)!=null){
				MessageDigest digest=null;
				try {
					digest = MessageDigest.getInstance("SHA-256");
				} catch (NoSuchAlgorithmException e) {}
				byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
				String encoded = Base64.getEncoder().encodeToString(hash);
				encoded=encoded.substring(0,encoded.lastIndexOf('='));
				userFacade.editUser(idu,pseudo,email,encoded);
				error=false;
				message="edit";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "deleteu":
			idu=Integer.parseInt(request.getParameter("idu"));
			error=true;
			message="error";
			if(userFacade.getUserById(idu)!=null){
				userFacade.deleteUser(idu);
				error=false;
				message="delete";
				String jsone = new Gson().toJson(error);
				String jsonm = new Gson().toJson(message);
                response.setContentType("application/json");
                response.getWriter().print(jsone);
                response.getWriter().print(jsonm);
			}
			break;
		case "listu":
			List<User> lu = userFacade.users();
			json = new Gson().toJson(lu);
            response.setContentType("application/json");
            response.getWriter().print(json);
			break;

		case "detailu":
			idu=Integer.parseInt(request.getParameter("idu"));
			user=userFacade.getUserById(idu);
			if(user!=null){
				List<Collection> collections=collectionFacade.collectionsByUser(idu);
				String jsonu = new Gson().toJson(user);
				String jsonc = new Gson().toJson(collections);
	            response.setContentType("application/json");
	            response.getWriter().print(jsonu);
				response.getWriter().print(jsonc);
			}
			break;
		case "imbyu":
			idu=Integer.parseInt(request.getParameter("idu"));
			if(userFacade.getUserById(idu)!=null){
				List<Image> images=userFacade.imagesByUser(idu);
				json = new Gson().toJson(images);
	            response.setContentType("application/json");
	            response.getWriter().print(json);
			}
			break;
			
			case "addcol":
				String description=request.getParameter("description");
				error=true;
				message="error";
				if(description!=null){
					collectionFacade.addCollection(description);
					error=false;
					message="add";
					String jsone = new Gson().toJson(error);
					String jsonm = new Gson().toJson(message);
	                response.setContentType("application/json");
	                response.getWriter().print(jsone);
	                response.getWriter().print(jsonm);
				}
				break;
			case "editcol":
				int idc=Integer.parseInt(request.getParameter("idc"));
				description=request.getParameter("description");
				error=true;
				message="error";
				if(description!=null && collectionFacade.getCollectionById(idc)!=null){
					collectionFacade.editCollection(idc,description);
					error=false;
					message="edit";
					String jsone = new Gson().toJson(error);
					String jsonm = new Gson().toJson(message);
	                response.setContentType("application/json");
	                response.getWriter().print(jsone);
	                response.getWriter().print(jsonm);
				}
				break;
			case "deletecol":
				idc=Integer.parseInt(request.getParameter("idu"));
				error=true;
				message="error";
				if(collectionFacade.getCollectionById(idc)!=null){
					collectionFacade.deleteCollection(idc);
					error=false;
					message="delete";
					String jsone = new Gson().toJson(error);
					String jsonm = new Gson().toJson(message);
	                response.setContentType("application/json");
	                response.getWriter().print(jsone);
	                response.getWriter().print(jsonm);
				}
				break;
			case "listcol":
				List<Collection> lc = collectionFacade.collections();
				json = new Gson().toJson(lc);
	            response.setContentType("application/json");
	            response.getWriter().print(json);
				break;
			case "detailcol":
				idc=Integer.parseInt(request.getParameter("idc"));
				Collection collection=collectionFacade.getCollectionById(idc);
				if(collection!=null){
					user=collection.getUser();
					List<Image> images=collectionFacade.imagesByCollection(idc);
					String jsonc = new Gson().toJson(collection);
					String jsonu = new Gson().toJson(user);
					String jsoni = new Gson().toJson(images);
		            response.setContentType("application/json");
		            response.getWriter().print(jsonc);
					response.getWriter().print(jsonu);
					response.getWriter().print(jsoni);
				}
				break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
