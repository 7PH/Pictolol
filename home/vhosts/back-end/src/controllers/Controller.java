package controllers;

import com.google.gson.JsonObject;
import utils.APIHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {

    public static final boolean PRODUCTION = false;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* Session initialisation */
        HttpSession session = request.getSession();
        APIHelper.initSession(session);

        /* Loading route parameter */
        String op = request.getParameter("do");

        /* Only action allowed without csrf token */
        if (op != null && op.equals("load")) {
            JsonObject json = new JsonObject();
            json.addProperty("csrf_name", APIHelper.CSRF_TOKEN_NAME);
            json.addProperty("csrf_value", APIHelper.getCsrfToken(session));
            APIHelper.exit(response, true, "ok", json);
        }

        if (! PRODUCTION) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    /* Redirects post request to get request */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
