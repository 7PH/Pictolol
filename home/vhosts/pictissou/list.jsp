<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,entities.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
List<User> lu = (List<User>)request.getAttribute("lu");

for (User u : lu) {
	out.println(u.getPseudo()+"<br>");
}
%>
<br/>
<a href='index.html'>home</a>
</body>
</html>