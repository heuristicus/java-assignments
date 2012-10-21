<%-- 
    Document   : moduleReport
    Created on : 24-Feb-2011, 17:49:46
    Author     : michal
--%>

<%@page import="java.util.HashMap"%>
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
        <%
            // Gets the report map from the session.
            Map reportMap = (Map) session.getAttribute("reportMap");
            // Gets table headers from the map.
            String[] tableHeaders = (String[]) reportMap.get("tableHeaders");
            // Gets the first few bits of data from the top level of the map.
            int year = (Integer) reportMap.get("year");
            int numberStudents = (Integer) reportMap.get("studentCount");
            String moduleName = (String) reportMap.get("moduleName");

            // Print some of the top level module details to the HTML form.
            out.println("Module Name: " + moduleName + "<BR>");
            out.println("Year: " + year + "<BR>");
            out.println("Number of Students Registered: " + numberStudents + "<BR>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            for (String header : tableHeaders) {
                out.println("<td>" + header + "</td>");
            }
            out.println("</tr>");

            // Gets the map which contains the details of all students from the map.
            Map students = (Map) reportMap.get("students");
            /*
             * Gets the names of all the keys in the map. This allows us to make
             * sure that we check all the students.
             */
            Set studentKeys = students.keySet();
            // Loop through all the student keys
            for (Object obj : studentKeys) {
                out.println("<tr>"); // start a new table row.
                /*
                 * Get the map object for the current student from the map
                 * containing all the students
                 */
                Map curStudent = (Map) students.get(obj);
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
