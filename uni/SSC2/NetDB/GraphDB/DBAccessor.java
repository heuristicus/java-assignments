/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author michal
 */
public class DBAccessor {

    Connection c;
    PreparedStatement s;
    private final String password;
    private final String dbLoc;
    private final String user;

    public static void main(String[] args) {
        DBAccessor a = new DBAccessor("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
        a.connect();
    }

    public DBAccessor(String dbLoc, String user, String password) {
        this.dbLoc = dbLoc;
        this.user = user;
        this.password = password;
        connect();
    }

    private void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(dbLoc, user, password);
        } catch (SQLException ex) {
            System.out.println("SQL Exception while attempting to connect database.");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to find postgres drivers.");
            ex.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            c.close();
        } catch (SQLException ex) {
            System.out.println("Unable to close database connection.");
            ex.printStackTrace();
        }
    }

    public String getUserPrivileges(String user, String password) throws SQLException {
        ResultSet details = getUserDetails(user, getPasswordHex(password));
        if (details.first()) {
            String userLevel = details.getString("rolename");
            return userLevel;
        }
        return "noauth";

    }

    // <editor-fold defaultstate="collapsed" desc="Query methods">
    private ResultSet getNumberOfStudentsOnModule(int moduleID, int academicYear) {
        try {
            s = c.prepareStatement("SELECT COUNT(universityid) "
                    + "FROM moduleregistration "
                    + "WHERE moduleid = ? "
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            s.setInt(2, academicYear);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Exception in method getNumberOfStudentsOnModule");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the user details for a user with the ID passed.
     * @param username Username of the user.
     * @param password SHA-1 hex string of the password.
     * @return
     */
    private ResultSet getUserDetails(String username, String password) {
        try {
            s = c.prepareStatement("SELECT password, rolename, universityID "
                    + "FROM schoolmember "
                    + "WHERE username = ? "
                    + "AND password = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setString(1, username);
            s.setString(2, password);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Exception in method getProgrammeNameFromUniID");
            ex.printStackTrace();
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="password hex stuff - MOVE TO CLIENT SIDE">
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
// </editor-fold>
}
