<%-- 
    Document   : studentReport
    Created on : 24-Feb-2011, 19:35:34
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
        <%
            // Gets the report map from the session.
            Map reportMap = (Map) session.getAttribute("reportMap");
            // Gets table headers from the map.
            String[] tableHeaders = (String[]) reportMap.get("tableHeaders");
            
            // Get some details out from the map
            Map name = (Map) reportMap.get("studentName");
            String fName = (String) name.get("firstName");
            String sName = (String) name.get("secondName");
            int sID = (Integer) reportMap.get("uniID");
            String progName = (String) reportMap.get("progName");
            // Print some name and ID details about the student
            out.println("Student: " + fName + " " + sName + "<BR>");
            out.println("University ID: " + sID + "<BR>");
            out.println("Programme Name: " + progName + "<BR><BR>");

            // Get the map which stores module details for each year
            Map yearDetails = (Map) reportMap.get("years");
            // Get the keys out of this map. The key of each year is its integer value
            // AS A STRING from the start of the student's study, i.e. 1,2,3 etc.
            Set yearKeys = yearDetails.keySet();
            for (int idx = 1; idx <= yearKeys.size(); idx++) {

                // Get the maps that we need, and get some variables as well.
                Map curYear = (Map) yearDetails.get(("" + idx));
                Map modules = (Map) curYear.get("modules" + idx); // FIXME get right module
                int creditsThisYear = (Integer) curYear.get("yearCredits");

                // Print some data about this year to the html page.
                out.println("<b>Year " + idx + "</b><BR>");
                out.println("Total Credits: " + creditsThisYear + "<BR>");
                // Start the table for this year.
                out.println("<table border=\"1\">");
                out.println("<tr>");
                for (String header : tableHeaders) {
                    out.println("<td>" + header + "</td>");
                }
                out.println("</tr>");
                // gets the set of keys which has module references
                Set modKeys = modules.keySet();
                // For each module, print its data into the table.
                for (Object modKey : modKeys){
                    Map curMod = (Map) modules.get(modKey);
                    Set curModKeys = curMod.keySet();
                    // Prints out each field in the current module into a new cell.
                    out.println("<tr>");
                    for (String header : tableHeaders) {
                        // The header will return null if the value is an integer
                        // so we use a different print method.
                        if (curMod.get(header) == null) {
                            out.println("<td>" + (Integer) curMod.get(header) + "</td>");
                        } else {
                            out.println("<td>" + curMod.get(header) + "</td>");
                        }
                    }
                    out.println("</tr>");
                }
                out.println("</table><BR>");
            }
        %>

    </body>
</html>
