package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import entities.*;
import facades.*;
import utils.APIHelper;

@WebServlet("/Users")
public class UserController extends Controller {
    private static final long serialVersionUID = 1L;

    @EJB
    private
    UserFacade userFacade;

    @EJB
    private
    CollectionFacade collectionFacade;

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
        String message = "";
        JsonObject json = new JsonObject();
        Map<String, String> data;
        User user;

        /* Basic routing */
        switch (op) {

            /* Fetch info about current user */
            case "fetch":
                if (session.getAttribute("idUser") != "0") {
                    int idUser = (int) session.getAttribute("idUser");
                    user = userFacade.getUserById(idUser);
                } else {
                    user = new User();
                    user.setId(0);
                    user.setPseudo("*Guest");
                }
                APIHelper.exit(response, false, "ok", user);
                break;


            /* Log out */
            case "logout":
                session.invalidate();
                APIHelper.exit(response, false, "Vous avez été déconnecté");
                break;


            /* Inscription */
            case "register":
                data = APIHelper.ensureParametersExists(request, response, "pseudo", "email", "password");
                userFacade.addUser(data.get("pseudo"), data.get("email"), User.hashPassword(data.get("password")));
                APIHelper.exit(response, false, "Vous avez bien créé votre compte");
                break;

            /* Login */
            case "login":
                data = APIHelper.ensureParametersExists(request, response, "pseudo", "password");

                // prevent bruteforce attack
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {  }

                user = userFacade.verifyUser(data.get("pseudo"), User.hashPassword(data.get("password")));
                if (user == null)
                    APIHelper.errorExit(response, "Données incorrectes");
                else {
                    session.setAttribute("idUser", user.getId());
                    session.setAttribute("pseudoUser", user.getPseudo());
                    APIHelper.exit(response, false, "ok", user);
                }
                break;


            /* User edit */
            case "edit":
                data = APIHelper.ensureParametersExists(request, response, "pseudo", "email", "password");

                if (session.getAttribute("idUser") == "0") {
                    APIHelper.errorExit(response, "L'utilisateur n'existe pas");
                } else {
                    userFacade.editUser(
                            (Integer) session.getAttribute("idUser"),
                            data.get("pseudo"),
                            data.get("email"),
                            User.hashPassword(data.get("password")));
                    APIHelper.exit(response, false, "Modification bien effectuée");
                }
                break;


            /* Delete account */
            case "delete":
                if (session.getAttribute("idUser") == "0") {
                    APIHelper.errorExit(response, "Vous n'êtes pas connecté");
                } else {
                    userFacade.deleteUser((Integer) session.getAttribute("idUser"));
                    APIHelper.exit(response, false, "Compte supprimé");
                }
                break;


            /* Users */
            case "list":
                List <User> users = userFacade.users();
                APIHelper.exit(response, false, "ok", users);
                break;


            /* Specific user info */
            case "detail":
                data = APIHelper.ensureParametersExists(request, response, "id");

                user = userFacade.getUserById(Integer.parseInt(data.get("id")));
                if (user == null) {
                    APIHelper.errorExit(response, "Unable to find this user");
                } else {
                    List < Collection > collections = collectionFacade.collectionsByUser(Integer.parseInt(data.get("id")));
                    json.add("user", gson.toJsonTree(user));
                    json.add("collections", gson.toJsonTree(collections));
                    APIHelper.exit(response, false, "ok", json);
                }
                break;


            /* User's images */
            case "images":
                data = APIHelper.ensureParametersExists(request, response, "id");

                user = userFacade.getUserById(Integer.parseInt(data.get("id")));
                if (user == null) {
                    APIHelper.errorExit(response, "Unable to find this user");
                } else {
                    List <Image> images = userFacade.imagesByUser(Integer.parseInt(data.get("id")));
                    APIHelper.exit(response, false, "ok", images);
                }
                break;


            /* Add collection */
            case "addcollection":
                data = APIHelper.ensureParametersExists(request, response, "description");
                collectionFacade.addCollection(data.get("description"));
                APIHelper.exit(response, false, "ok");
                break;


            /* Edit collection */
            case "editcollection":
                data = APIHelper.ensureParametersExists(request, response, "id", "description");

                Collection collection = collectionFacade.getCollectionById(Integer.parseInt(data.get("id")));
                if (collection == null) {
                    APIHelper.errorExit(response, "Cette collection n'existe pas");
                } else if (collection.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                    APIHelper.errorExit(response, "Cette collection ne vous appartient pas");
                } else {
                    collectionFacade.editCollection(Integer.parseInt(data.get("id")), data.get("description"));
                    APIHelper.exit(response, false, "ok");
                }
                break;


            /* Delete collection */
            case "deletecollection":
                data = APIHelper.ensureParametersExists(request, response, "id");

                collection = collectionFacade.getCollectionById(Integer.parseInt(data.get("id")));
                if (collection == null) {
                    APIHelper.errorExit(response, "Cette collection n'existe pas");
                } else if (collection.getUser().getId() != (Integer)session.getAttribute("idUser")) {
                    APIHelper.errorExit(response, "Cette collection ne vous appartient pas");
                } else {
                    collectionFacade.deleteCollection(Integer.parseInt(data.get("id")));
                    APIHelper.exit(response, false, "ok");
                }
                break;


            /* List collections */
            case "listcollections":
                APIHelper.exit(response, false, "ok", collectionFacade.collections());
                break;


            /* Specific collection info */
            case "collection":
                data = APIHelper.ensureParametersExists(request, response, "id");

                collection = collectionFacade.getCollectionById(Integer.parseInt(data.get("id")));
                if (collection == null) {

                } else {
                    user = collection.getUser();
                    List <Image> images = collectionFacade.imagesByCollection(Integer.parseInt(data.get("id")));
                    json.add("collection", gson.toJsonTree(collection));
                    json.add("user", gson.toJsonTree(user));
                    json.add("images", gson.toJsonTree(images));
                    APIHelper.exit(response, false, "ok", json);
                }
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