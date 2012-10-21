package Database;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author michal
 */
public class userLogin extends HttpServlet {

    // student user: wxs001 pass: uGAcxCt8
    // lecturer user: exr007 pass: lecturer
    // admin user: admin pass: admin
    // change the connection details in DBBase if necessary.
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String user = request.getParameter("username");
        String password = request.getParameter("password");

        DBBase database = new DBBase();
        // Get the details of the user with the details that have been entered.
        ResultSet details = database.getUserDetails(user, getPasswordHex(password));
        try {
            if (details.next()) { // Executed only if the query returned successfully.
                String type = details.getString("rolename").trim();
                String id = details.getString("universityid");
                int authLevel = 0;
                if (type.equals("student")) {
                    authLevel = 1;
                    session.setAttribute("studentID", id);
                } else if (type.equals("lecturer")) {
                    authLevel = 2;
                } else if (type.equals("administrator")) {
                    authLevel = 3;
                }
                session.setAttribute("user", user);
                session.setAttribute("authLevel", authLevel);
                session.setAttribute("authenticated", true);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginSuccess.jsp");
                dispatcher.forward(request, response);
                return;
            } else { // Executed if the query failed.
                session.setAttribute("authenticated", false);
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/loginFailed.jsp");
                dispatcher.forward(request, response);
                return;
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception while attempting to confirm user details.");
            ex.printStackTrace();
        } finally {
            database.closeConnection();
        }
    }

    public String getPasswordHex(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
            byte[] pass = password.getBytes();
            md.reset();
            md.update(pass);
            byte[] mdByte = md.digest();
            StringBuilder passHex = new StringBuilder();
            for (int i = 0; i < mdByte.length; i++) {
                passHex.append(Integer.toHexString(0xFF & mdByte[i]));
            }
            return passHex.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Encryption algorithm not found.");
            System.exit(1);
        } catch (Exception ex) {
            System.out.println("Exception while trying to process password.");
            System.exit(1);
        }
        // Should never be reached. An exception will be thrown and the system will exit
        // or the method will return the hex string.
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
