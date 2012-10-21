/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author michal
 */
public class SessionAuth {

    public void authenticateSession(HttpServletRequest request, HttpServletResponse response, int authLevel) {
        boolean authenticated = false;
        int userAuthLevel = 0;
        HttpSession session = request.getSession();
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
        if (authenticated != true || userAuthLevel < authLevel) {
            redirect(request, response);
        }
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response){
        try {
            response.sendRedirect("naughty.jsp");
        } catch (IOException ex) {
            System.out.println("IO error while redirecting naughties.");
        } catch (Exception ex) {
            System.out.println("Other error whle redirecting naughties.");
        }
    }

}
