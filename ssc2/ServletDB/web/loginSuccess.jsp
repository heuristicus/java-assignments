<%-- 
    Document   : loginSuccess
    Created on : 18-Feb-2011, 17:19:09
    Author     : michal
--%>

<%@page import="java.io.IOException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
private static final int AUTH_LEVEL_REQ = 1;
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
        <%
        boolean authenticated = false;
        int userAuthLevel = 0;
        Object authObj = session.getAttribute("authenticated");
        Object authLevObj = session.getAttribute("authLevel");
        if (authObj != null && authLevObj != null){
            authenticated = (Boolean) session.getAttribute("authenticated");
            userAuthLevel = (Integer) (session.getAttribute("authLevel"));
        }
        if (authenticated != true || userAuthLevel < AUTH_LEVEL_REQ) {
            out.print("<body onload=setTimeout(\"location.href='index.jsp'\",2500)>");
            out.print("You do not belong here. Redirecting you to the Login page.");
        } else {
            out.print("<body onload=setTimeout(\"location.href='userPanel.jsp'\",2500)>");
            out.print("Redirecting you to the user panel.");
            out.print("<P> <a href=\"userPanel.jsp\"> Click here if you are not redirected</a></P>");
        }
    %>
    </body>
</html>
