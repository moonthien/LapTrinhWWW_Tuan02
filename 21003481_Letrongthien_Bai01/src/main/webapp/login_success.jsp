<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	//allow access only if session exists
	String user = (String) session.getAttribute("userIdSession");

	String userName = null;
	String sessionID = null;
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("userIdCookie")) {
		userName = cookie.getValue();
			}

			if (cookie.getName().equals("JSESSIONID")) {
		sessionID = cookie.getValue();
			}
		}
	}
	%>
	<h3>
		Hi
		<%=userName%>
		(cookie), Login successful. Your Session ID=<%=sessionID%></h3>
	<br> User Session =<%=user%>
	<br>
	<a href="checkout_page.jsp">Checkout Page</a>
	<form action="LogoutServlet" method="post">
		<input type="submit" value="Logout">
	</form>
</body>
</html>