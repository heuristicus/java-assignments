<%-- 
    Document   : creditReport
    Created on : 24-Feb-2011, 19:35:45
    Author     : michal
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
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
        <h3>Students not registered on 120 credits</h3>
        <%
            // Gets the report map from the session.
            Map reportMap = (Map) session.getAttribute("reportMap");
            // Gets table headers from the map.
            String[] tableHeaders = (String[]) reportMap.get("tableHeaders");
            
            // Print table headers.
            out.println("<table border=\"1\">");
            out.println("<tr>");
            for (String header : tableHeaders) {
                out.println("<td>" + header + "</td>");
            }
            out.println("</tr>");

            // Gets the map with students in it
            Map students = (Map) reportMap.get("students");
            // get the set of keys in the results so we can get them all.
            Set studentKeys = students.keySet();
            // Loop through keys.
            for (Object student : studentKeys) {
                out.println("<tr>"); // start a new table row.
                /*
                 * Get the map object for the current student from the map
                 * containing all the students
                 */
                Map curStudent = (Map) students.get(student);
                // Go through all the table headers (the fields of each student are named the same)
                for (String header : tableHeaders) {
                    // Print a cell in the table.
                    out.println("<td>" + curStudent.get(header) + "</td>");
                }
                out.println("<tr>");
            }

            out.println("</table>");
        %>
    </body>
</html>
