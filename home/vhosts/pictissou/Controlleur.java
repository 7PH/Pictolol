package controleur;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import facades.*;

@WebServlet("/Controlleur")
public class Controlleur extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	UserFacade userFacade;
	
	@EJB
	ImageFacade imageFacade;
	
	@EJB
	CollectionFacade collectionFacade;
       
    public Controlleur() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op=request.getParameter("op");
		switch(op){
		case "addu":
			String pseudo=request.getParameter("pseudo");
			String email=request.getParameter("email");
			String password=request.getParameter("password");
			userFacade.addUser(pseudo, email, password);
			request.getRequestDispatcher("index.html").forward(request, response);
			break;
		case "addc":
			String description=request.getParameter("description");
			int like=Integer.parseInt(request.getParameter("likee"));
			collectionFacade.addCollection(description, like);
			request.getRequestDispatcher("index.html").forward(request, response);
			break;
		case "cbyu":
			int idUser=Integer.parseInt(request.getParameter("idUser"));
			List<Collection> cbyu = collectionFacade.collectionsByUser(idUser);
			request.setAttribute("cbyu", cbyu);
			request.getRequestDispatcher("cbyu.jsp").forward(request, response);
			break;
		case "addctou":
			int idc=Integer.parseInt(request.getParameter("idc"));
			int idu=Integer.parseInt(request.getParameter("idu"));
			collectionFacade.addCollectionToUser(idc, idu);
			request.getRequestDispatcher("index.html").forward(request, response);
			break;
		case "deletecfromu":
			idc=Integer.parseInt(request.getParameter("idc"));
			idu=Integer.parseInt(request.getParameter("idu"));
			collectionFacade.deleteCollectionFromUser(idc);
			request.getRequestDispatcher("index.html").forward(request, response);
			break;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
