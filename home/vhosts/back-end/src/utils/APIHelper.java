package utils;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
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
        return System.currentTimeMillis() + "-" + Math.random();
    }

    public static String getCsrfToken(HttpSession session) {
        return (String) session.getAttribute(CSRF_TOKEN_NAME);
    }

    public static Map<String, String> ensureParametersExists(HttpServletRequest request, HttpServletResponse response, String... parameters) throws IOException {
        Map<String, String> values = new HashMap<>();
        for (String parameter: parameters) {
            if (request.getParameter(parameter) == null) {
                exit(response, true, "La variable " + parameter + " n'a pas été définie");
                return null;
            }
            values.put(parameter, request.getParameter(parameter));
        }
        return values;
    }

    public static JsonArray toJsonArray(List<Jsonable> objects) {
        JsonArray jsonArray = new JsonArray();
        for (Jsonable jsonable: objects) {
            jsonArray.add(jsonable.toJson());
        }
        return jsonArray;
    }

    public static boolean checkCsrf(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        return true;
        //return request.getParameter(CSRF_TOKEN_NAME) != null && request.getParameter(CSRF_TOKEN_NAME).equals(getCsrfToken(session));
    }

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}
