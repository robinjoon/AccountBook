<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%if(response.getStatus()!=200){ %>
	<!DOCTYPE html>
	<html>
	<head>
	<meta charset="UTF-8">
	<title>Error</title>
	</head>
	<body>
		Error (<%=response.getStatus() %>)
	</body>
	</html>
<%}%>