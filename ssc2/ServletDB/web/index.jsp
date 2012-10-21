<%-- 
    Document   : index
    Created on : 16-Feb-2011, 20:00:30
    Author     : michal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome to the School database.</h1>
        <form action="userLogin" method="POST">
            <span> username: </span>
            <input type="text" name="username" value="" /> <BR>
            <span> password: </span>
            <input type="password" name="password" value="" />
            <P class="submit"> <input type="submit" value="Login" /></P>
        </form>
    </body>
</html>
