package Database;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class Queries {

    Connection c;
    PreparedStatement s;

    public Queries(Connection c) {
        this.c = c;
    }

    /**
     * Gets a resultset which contains the firstname and lastname of the student
     * with the provided ID number.
     * @param studentID
     * @return
     */
    public ResultSet getFirstLastByID(int universityID) {
        try {
            s = c.prepareStatement("SELECT firstname, lastname FROM students "
                    + "WHERE universityID = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, universityID);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Exception in method getFirstLastByID.");
            ex.printStackTrace();
        }
        return null; // failed.
    }

    /**
     * Gets the academic year when a student joined the university
     * and the length of the course.
     * @return
     */
    public ResultSet getAcYears(int universityID) {
        try {
            s = c.prepareStatement("SELECT academicyear, yearsofstudy "
                    + "FROM degreeregistration WHERE universityid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, universityID);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getAcYears.");
            ex.printStackTrace();
        }
        return null; // failed
    }

    public ResultSet getModuleIDByUIDAndYear(int universityID, int year) {
        try {
            s = c.prepareStatement("SELECT moduleid "
                    + "FROM moduleregistration WHERE universityid = ? "
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, universityID);
            s.setInt(2, year);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getModuleIDByUIDAndYear");
            ex.printStackTrace();
        }
        return null; // failed
    }

    public ResultSet getModuleIDsForYear(int universityID, int academicYear) {
        try {
            s = c.prepareStatement("SELECT moduleid "
                    + "FROM moduleregistration WHERE universityid = ? "
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, universityID);
            s.setInt(2, academicYear);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getModuleIDsForYear");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all data from the module registration table with the module ID given.
     * @param moduleID
     * @param academicYear
     * @return
     */
    public ResultSet getModRegByModIDAndYear(int moduleID, int academicYear) {
        try {
            s = c.prepareStatement("SELECT *"
                    + "FROM moduleregistration "
                    + "WHERE moduleid = ?"
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            s.setInt(2, academicYear);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getModRegByModID");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all data about a module in the modules table given a module ID.
     * @param moduleID
     * @return
     */
    public ResultSet getModByModID(int moduleID) {
        try {
            s = c.prepareStatement("SELECT * "
                    + "FROM modules "
                    + "WHERE moduleid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getModByModID");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a programme name when provided with an ID number.
     * @param universityID
     * @return
     */
    public ResultSet getProgrammeNameFromUniID(int universityID) {
        try {
            s = c.prepareStatement("SELECT programmename "
                    + "FROM degreeprogramme WHERE degreeid = "
                    + "(SELECT degreeid FROM degreeregistration WHERE universityID = ?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, universityID);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Exception in method getProgrammeNameFromUniID");
            ex.printStackTrace();
        }
        return null; // failed
    }

    /**
     * Gets the user details for a user with the ID passed.
     * @param username Username of the user.
     * @param password SHA-1 hex string of the password.
     * @return
     */
    public ResultSet getUserDetails(String username, String password) {
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

    /**
     * Gets details from student and moduleregistration tables for a
     * specific module, student and year. universityID,
     * firstname, lastname, registrationcode and notes.
     * @param moduleID
     * @param universityID
     * @param year
     * @return
     */
    public ResultSet getStudentDetailsAndModuleRegistrationDetails(int moduleID, int universityID, int year) {
        try {
            s = c.prepareStatement("SELECT students.universityid, firstname, lastname, registrationcode, notes "
                    + "FROM students, moduleregistration "
                    + "WHERE moduleid = ?"
                    + "AND students.universityID = ? "
                    + "AND moduleregistration.universityid = ? "
                    + "AND moduleregistration.academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            s.setInt(2, universityID);
            s.setInt(3, universityID);
            s.setInt(4, year);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getStudentDetailsAndModuleRegistrationDetails");
            ex.printStackTrace();
        }
        return null; // failed
    }

    /**
     * Gets the total number of credits that a student was registered for for a
     * particular year.
     * @param studentID
     * @param academicYear
     * @return
     */
    public ResultSet getCreditsForYear(int studentID, int academicYear) {
        try {
            s = c.prepareStatement("SELECT SUM(credits) AS totalcredits "
                    + "FROM modules, moduleregistration "
                    + "WHERE academicyear = ? "
                    + "AND universityID = ?"
                    + "AND modules.moduleid = moduleregistration.moduleid",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, academicYear);
            s.setInt(2, studentID);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getCreditsForYear");
            ex.printStackTrace();
        }
        return null; // failed
    }

    /**
     * Gets the next module ID to be used.
     * @return
     */
    public int getNextModuleID() {
        try {
            s = c.prepareStatement("SELECT MAX(moduleid) AS topmodcode "
                    + "FROM modules",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = s.executeQuery();
            res.first();
            int nextMID = res.getInt("topmodcode") + 1;
            return nextMID;
        } catch (SQLException ex) {
            System.out.println("Error in method getNextModuleID");
            ex.printStackTrace();
        }
        return Integer.MIN_VALUE;
    }

    public int getNextRegCode() {
        try {
            s = c.prepareStatement("SELECT MAX(registrationcode) AS topregcode "
                    + "FROM moduleregistration",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resQuery = s.executeQuery();
            resQuery.first();
            System.out.println(resQuery.getInt("topregcode"));
            int regCode = resQuery.getInt("topregcode") + 1;
            return regCode;
        } catch (SQLException ex) {
            System.out.println("Error in method getNextRegCode");
            ex.printStackTrace();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * Returns the ID numbers of all students on a specific module in a particular year.
     * @param moduleID
     * @return
     */
    public ResultSet getUniIDsOnModule(int moduleID, int year) {
        try {
            s = c.prepareStatement("SELECT universityid FROM moduleregistration "
                    + "WHERE moduleid = ? "
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            s.setInt(2, year);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getUniIDsOnModule");
            ex.printStackTrace();
        }
        return null; // failed
    }

    /**
     * Gets the number of students on a module in a specific year.
     * @param moduleID
     * @return
     */
    public ResultSet getNumberOfStudentsOnModule(int moduleID, int year) {
        try {
            s = c.prepareStatement("SELECT COUNT(universityid) AS studcount FROM moduleregistration "
                    + "WHERE moduleid = ? "
                    + "AND academicyear = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, moduleID);
            s.setInt(2, year);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getNumberOfStudentsOnModule");
            ex.printStackTrace();
        }
        return null; // failed.
    }

    /**
     * Adds a new module to the database.
     * @param modName
     * @param modLevel
     * @param modCredits
     * @throws SQLException
     */
    public void addModule(String modName, int modLevel, int modCredits) throws SQLException {
        int modID = getNextModuleID();
        System.out.println(modID);
        s = c.prepareStatement("INSERT INTO modules (modulename, moduleid, modulelevel,credits) "
                + "VALUES(?, ?, ?, ?)",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        s.setString(1, modName);
        s.setInt(2, modID);
        s.setInt(3, modLevel);
        s.setInt(4, modCredits);
        s.executeUpdate();
    }

    /**
     * Registers a student onto a module.
     * @param acYear
     * @param mID
     * @param sID
     * @param notes
     */
    public void addRegistrationToModule(int acYear, int mID, int sID, String notes) throws SQLException {
        int regCode = getNextRegCode();
        s = c.prepareStatement("INSERT INTO moduleregistration "
                + "(academicyear, moduleid, universityid, registrationcode, notes) "
                + "VALUES(?, ?, ?, ?, ?)",
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        s.setInt(1, acYear);
        s.setInt(2, mID);
        s.setInt(3, sID);
        s.setInt(4, regCode);
        s.setString(5, notes);
        s.executeUpdate();
    }

    /**
     * removes a student's registration from a module for a specific academic year.
     * @param acYear
     * @param mID
     * @param sID
     * @throws SQLException
     */
    public void removeRegistrationFromModule(int acYear, int mID, int sID) throws SQLException {
        s = c.prepareStatement("DELETE FROM moduleregistration "
                + "WHERE academicyear = ? "
                + "AND moduleid = ? "
                + "AND universityid = ?");
        s.setInt(1, acYear);
        s.setInt(2, mID);
        s.setInt(3, sID);
        s.executeUpdate();
    }

    /**
     * gets the total credits a student is registered for in the academic year
     * given to the method.
     * @param academicYear Year to return results for. If this value is -1,
     * then the current year will be used as the query year.
     * @return
     */
    public ResultSet getCreditsThisAcademicYear(int academicYear) {
        try {
            // Sets the academic year to the current year if the academic year has
            // been set to a pre-arranged value for this purpose.
//            if (academicYear == -1) {
//                academicYear = Calendar.getInstance().get(Calendar.YEAR);
//            }
            s = c.prepareStatement("SELECT SUM(credits) AS totalcredits, m1.universityid "
                    + "FROM moduleregistration m1, moduleregistration m2, modules "
                    + "WHERE m1.moduleid = m2.moduleid "
                    + "AND m1.moduleid = modules.moduleid "
                    + "AND m1.universityid = m2.universityid "
                    + "AND m1.academicyear = ? "
                    + "GROUP BY m1.universityid "
                    + "ORDER BY universityid",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, academicYear);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getCreditsThisAcademicYear");
            ex.printStackTrace();
        }
        return null; // failed
    }

    /**
     * Gets the number of credits each student was registered on in their last
     * academic year. contains the universityid, total credits and academic year.
     * @return
     */
    public ResultSet getCreditsInLastAcademicYearOfStudent() {
        try {
            s = c.prepareStatement("SELECT SUM(credits) AS totalcredits, m1.universityid, m1.academicyear "
                    + "FROM moduleregistration m1, moduleregistration m2, modules "
                    + "WHERE m1.moduleid = m2.moduleid "
                    + "AND m1.moduleid = modules.moduleid "
                    + "AND m1.universityid = m2.universityid "
                    + "AND m1.academicyear = "
                    /**
                     * Subquery gets the highest academic year for each student.
                     * This isn't necessarily the current academic year. if the
                     * student left the university, then their last academic
                     * year will be displayed.
                     */
                    + "(SELECT MAX(m1.academicyear) AS currentyear "
                    + "FROM moduleregistration m1, moduleregistration m3 "
                    + "WHERE m1.universityid = m3.universityid "
                    + "AND m2.universityid = m3.universityid)"
                    + "GROUP BY m1.universityid, m1.academicyear "
                    + "ORDER BY universityid, academicyear",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getCreditsPerAcademicYear");
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Gets the number of credits each student was registered on each academic year.
     * the resultset is sorted by university id and academic year.
     * @param studentID
     * @return
     */
    public ResultSet getCreditsEachAcademicYear() {
        try {
            s = c.prepareStatement("SELECT SUM(credits) AS totalcredits, m1.universityid, m1.academicyear "
                    + "FROM moduleregistration m1, moduleregistration m2, modules "
                    + "WHERE m1.moduleid = m2.moduleid "
                    + "AND m1.moduleid = modules.moduleid "
                    + "AND m1.universityid = m2.universityid "
                    + "GROUP BY m1.universityid, m1.academicyear "
                    + "ORDER BY universityid, academicyear",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            return s.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error in method getCreditsPerAcademicYear");
            ex.printStackTrace();
        }
        return null;
    }

    public int getRandomModule(){
        try {
            s = c.prepareStatement("SELECT moduleid FROM modules "
                    + "GROUP BY random(), moduleid "
                    + "LIMIT 1",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = s.executeQuery();
            res.first();
            return res.getInt("moduleid");
        } catch (SQLException ex) {
            System.out.println("exception while getting random uni id");
            ex.printStackTrace();
        }
        return 1;
    }

    public int getRandomUniversityID() {
        try {
            s = c.prepareStatement("SELECT universityid FROM students "
                    + "GROUP BY random(), universityid "
                    + "LIMIT 1",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res = s.executeQuery();
            res.first();
            return res.getInt("universityid");
        } catch (SQLException ex) {
            System.out.println("exception while getting random uni id");
            ex.printStackTrace();
        }
        return 1000001;
    }
}
