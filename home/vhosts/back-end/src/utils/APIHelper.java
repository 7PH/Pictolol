package utils;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APIHelper {

    public static final String CSRF_TOKEN_NAME = "api-csrf-token";

    private APIHelper() {}

    public static void exit(HttpServletResponse response, boolean error, String message, JsonElement data) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(buildResponse(error, message, data));
    }
    public static void exit(HttpServletResponse response, boolean error, String message, Object data) throws IOException {
        exit(response, error, message, new Gson().toJsonTree(data));
    }
    public static void exit(HttpServletResponse response, boolean error, String message) throws IOException {
        exit(response, error, message, new JsonObject());
    }
    public static void errorExit(HttpServletResponse response, String message) throws IOException {
        exit(response, true, message);
    }

    private static JsonObject buildResponse(boolean error, String message, JsonElement data) {
        JsonObject json = new JsonObject();
        json.addProperty("error", error);
        json.addProperty("message", message);
        json.add("data", data);
        return json;
    }

    public static void initSession(HttpSession session) {
        if (session.getAttribute(CSRF_TOKEN_NAME) == null) {
            session.setAttribute(CSRF_TOKEN_NAME, generateCsrfToken());
            session.setAttribute("idUser", 0);
        }
    }

    private static String generateCsrfToken() {
        return Double.toString(Math.floor(100000 * Math.random()));
    }

    public static String getCsrfToken(HttpSession session) {
        return (String) session.getAttribute(CSRF_TOKEN_NAME);
    }

    public static Map<String, String> ensureParametersExists(HttpServletRequest request, HttpServletResponse response, String... parameters) throws IOException {
        Map<String, String> values = new HashMap<>();
        for (String parameter: parameters) {
            if (request.getParameter(parameter) == null)
                exit(response, true, "La variable " + parameter + " n'a pas été définie");
            values.put(parameter, request.getParameter(parameter));
        }
        return values;
    }

    public static void ensureCsrf(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        Map<String, String> data = ensureParametersExists(request, response, CSRF_TOKEN_NAME);
        if (! data.equals(getCsrfToken(session)))
            exit(response, true, "Le token CSRF est invalide");
    }
}
