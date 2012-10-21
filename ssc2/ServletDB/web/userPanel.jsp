<%-- 
    Document   : test
    Created on : 16-Feb-2011, 20:20:55
    Author     : michal
--%>

<%@page import="Database.SessionAuth"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%!
private static final int AUTH_LEVEL_REQ = 1;
SessionAuth auth = new SessionAuth();
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <%
                boolean authenticated = false;
                int userAuthLevel = 0;
                String redirectLoc = "";
                /**
                 * Messing around with objects because they won't play nicely.
                 * eventually you get the correct values out.
                 */
                Object authObj = session.getAttribute("authenticated");
                Object authLevObj = session.getAttribute("authLevel");
                if (authObj != null && authLevObj != null) {
                    authenticated = (Boolean) session.getAttribute("authenticated");
                    userAuthLevel = (Integer) session.getAttribute("authLevel");
                }
                // Redirects the unauthenticated or unauthorised used to the index. They
                // will be unable to see any of the query data.
                if (authenticated != true || userAuthLevel < AUTH_LEVEL_REQ) {
                    out.print("<body onload=setTimeout(\"location.href='index.jsp'\",2500)>");
                    out.print("You do not belong here. Redirecting you to the Login page.");
                }

                if (authenticated == true && userAuthLevel >= AUTH_LEVEL_REQ) {
                    out.println("<body>");
                    out.println("<form action=\"userLogout\" method=\"POST\">");
                    out.println("<P class=\"submit\"> <input type=\"submit\" value = \"Log Out\" /></P>");
                    out.println("</form>");
                    if (userAuthLevel >= 1) {
                        // Adds html for the non-students. They can enter a value of student id to check
                        if (userAuthLevel == 2 || userAuthLevel == 3) {
                            out.println("<h2> Create a student report </h2>");
                            out.println("<form action=\"requestManager\" method=\"POST\">");
                            out.println("<span>Student ID: </span>");
                            out.println("<input type=\"text\" name=\"studentID\"/>");
                            out.println("<P class=\"submit\"> <input type=\"submit\" name=\"studentReport\"value=\"Student Report\" /></P>");
                            out.println("</form>");
                        } else { // student version. Can only view their own report.
                            out.println("<h2> Create a student report </h2>");
                            out.println("<form action=\"requestManager\" method=\"POST\">");
                            out.println("<span> Check the modules that you've been registered on each year.</span><BR>");
                            out.println("<span>Student ID: </span>");
                            // Create a disabled textbox which displays the userid. Only
                            // really for ease of processing, and to provide some visuals.
                            out.println("<input type=\"text\" name=\"studentID\" readonly=\"readonly\""
                                    + " value=\""
                                    + (String) session.getAttribute("studentID") + "\"/>");
                            out.println("<P class=\"submit\"> <input type=\"submit\" name=\"studentReport\" value=\"Student Report\" /></P>");
                            out.println("</form>");
                        }
                    }
                    if (userAuthLevel >= 2) { // Module report for lecturer and above.
                        out.println("<h2> Create a module report </h2>");
                        out.println("<form action=\"requestManager\" method=\"POST\">");
                        out.println("<span> Module ID: </span>");
                        out.println("<input type=\"text\" name=\"moduleID\"><BR>");
                        out.println("<span> Year: </span>");
                        out.println("<input type=\"text\" name=\"academicYear\"><BR>");
                        out.println("<P class=\"submit\"> <input type=\"submit\" name=\"moduleReport\"value=\"Module Report\" /></P>");
                        out.println("</form>");
                    }
                    if (userAuthLevel == 3) { // admin only stuff
                        // add a module
                        out.println("<h2> Add a new module </h2>");
                        out.println("<form action=\"requestManager\" method=\"POST\">");
                        out.println("<span> Module Name: </span>");
                        out.println("<input type=\"text\" name=\"modName\"/><BR>");
                        out.println("<span> Module Level: </span>");
                        out.println("<input type=\"text\" name=\"modLevel\"/><BR>");
                        out.println("<span> Credits: </span>");
                        out.println("<input type=\"text\" name=\"credits\"/>");
                        out.println("<P class=\"submit\"> <input type=\"submit\" name=\"addModule\"value=\"Add Module\" /></P>");
                        out.println("</form>");

                        // Register a student on a module
                        out.println("<h2> Register a student to a module </h2>");
                        out.println("<form action=\"requestManager\" method=\"POST\">");
                        out.println("<span> Register a student onto a specific module in an academic year. </span><BR>");
                        out.println("<span> Module ID: </span>");
                        out.println("<input type=\"text\" name=\"modIDReg\"/><BR>");
                        out.println("<span> Student ID: </span>");
                        out.println("<input type=\"text\" name=\"sIDReg\"/><BR>");
                        out.println("<span> Academic Year: </span>");
                        out.println("<input type=\"text\" name=\"acYearReg\"/><BR>");
                        out.println("<span> Notes: </span><BR>");
                        out.println("<textarea name=\"regNotes\"> </textarea><BR>");
                        out.println("<P class=\"submit\"> <input type=\"submit\" name=\"registerStudent\"value=\"Register Student\" /></P>");
                        out.println("</form>");

                        //Unregister a student from a module
                        out.println("<h2> Unegister  a student from a module </h2>");
                        out.println("<form action=\"requestManager\" method=\"POST\">");
                        out.println("<span> Remove a student from a specific module in an academic year. </span><BR>");
                        out.println("<span> Module ID: </span>");
                        out.println("<input type=\"text\" name=\"modIDUnReg\"/><BR>");
                        out.println("<span> Student ID: </span>");
                        out.println("<input type=\"text\" name=\"sIDUnReg\"/><BR>");
                        out.println("<span> Academic Year: </span>");
                        out.println("<input type=\"text\" name=\"acYearUnReg\"/><BR>");
                        out.println("<P class=\"submit\"> <input type=\"submit\" name=\"unregisterStudent\"value=\"Unregister Student\" /></P>");
                        out.println("</form>");

                        // Report on credits.
                        out.println("<h2> Create a credit report</h2>");
                        out.println("<form action=\"requestManager\" method=\"POST\">");
                        out.println("<span> List students who are not registered on 120 credits this year. </span><BR>");
                        out.println("<span> Academic Year: </span>");
                        out.println("<input type=\"text\" name=\"acYearCredit\" value=\"" + Calendar.getInstance().get(Calendar.YEAR)+ "\" /><BR>");
                        out.println("<P class=\"submit\"> <input type=\"submit\" name=\"creditReport\"value=\"Credit Report\" /></P>");
                        out.println("</form>");
                    }
                }
    %>
</body>
</html>
