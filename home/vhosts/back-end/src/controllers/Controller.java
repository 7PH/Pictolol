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
	
	private static final long serialVersionUID = 1L;

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
            json.addProperty("csrfName", APIHelper.CSRF_TOKEN_NAME);
            json.addProperty("csrfValue", APIHelper.getCsrfToken(session));
            json.addProperty("idUser", (Integer)session.getAttribute("idUser"));
            json.addProperty("pseudoUser", (String)session.getAttribute("pseudoUser"));
            json.addProperty("ip", APIHelper.getClientIp(request));
            request.setAttribute("gaveCsrfToken", true);
            APIHelper.exit(response, false, "ok", json);
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

