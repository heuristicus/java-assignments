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
import java.util.logging.Level;
import java.util.logging.Logger;

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

    /**
     * Connects the database, if it is not already connected.
     */
    public void connect() {
        if (c == null) {
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
    }

    public void disconnect() {
        try {
            c.close();
        } catch (SQLException ex) {
            System.out.println("Unable to close database connection.");
            ex.printStackTrace();
        }
    }

    /**
     * Takes the username and password hex and checks for the user's role in the
     * database.
     * @param user
     * @param password
     * @return
     * @throws SQLException
     */
    public String getUserPrivileges(String user, String password) throws SQLException {
        ResultSet details = getUserDetails(user, password);
        if (details.first()) {
            String userLevel = details.getString("rolename");
            return userLevel.trim();
        }
        return "noauth";
    }

    public int getNumberStudents(int moduleID, int academicYear) {
        try {
            ResultSet mStudents = getNumberOfStudentsOnModule(moduleID, academicYear);
            if (mStudents.first()) {
                return mStudents.getInt("registeredstudents");
            }
        } catch (SQLException ex) {
            System.out.printf("Failed to get number of students on module %d in %d.\n", moduleID, academicYear);
        }
        return Integer.MIN_VALUE;
    }

    public int getFirstModuleYear(int moduleID) {
        try {
            ResultSet mYear = getFirstModuleYearQ(moduleID);
            if (mYear.first()) {
                return mYear.getInt("firstyear");
            }
        } catch (SQLException ex) {
            System.out.printf("Failed to get the first module year for module %d.\n", moduleID);
        }
        return Integer.MIN_VALUE;
    }

    // <editor-fold defaultstate="collapsed" desc="Query methods">
    private ResultSet getNumberOfStudentsOnModule(int moduleID, int academicYear) {
        try {
            s = c.prepareStatement("SELECT COUNT(universityid) AS registeredstudents "
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

    private ResultSet getFirstModuleYearQ(int moduleID) {
        try {
            s = c.prepareStatement("SELECT MIN(academicyear) AS firstyear "
                    + "FROM moduleregistration "
                    + "WHERE moduleid = ? ",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
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
}
