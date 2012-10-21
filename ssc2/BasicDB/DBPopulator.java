/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michal
 */
public class DBPopulator {

    Connection c;
    PreparedStatement s;
    String user;
    String pw;
    String dbLoc;
    Properties cProp;
    String homeLoc = "jdbc:postgresql://localhost:5432/";
    String uniLoc = "jdbc:postgresql://dbteach/";
    Location connLoc;

    public enum Location {

        HOME, UNI
    };

    public static void main(String[] args) {
        DBPopulator b = new DBPopulator("mich", "mich", Location.HOME);
        b.createAndPopulate();
//        for (int i = 0; i < 10; i++) {
//            System.out.println(b.makeRandomPassword(8));
//        }
    }

    public DBPopulator(String user, String pw, Location l) {
        if (l == Location.HOME) {
            dbLoc = homeLoc + "postgres";
            connLoc = Location.HOME;
        } else if (l == Location.UNI) {
            dbLoc = uniLoc + "ssclibrary";
            connLoc = Location.UNI;
        }
        this.user = user;
        this.pw = pw;
        connectDB();
    }

    /**
     * Initialises the properties object based on values passed in the constructor.
     */
    private void initProps() {
        cProp = new Properties();
        cProp.setProperty("user", user);
        cProp.setProperty("password", pw);
    }

    /**
     * Connects to the database.
     */
    public final void connectDB() {
        initProps();
        c = null;
        try {
            c = DriverManager.getConnection(dbLoc, cProp);
        } catch (SQLException ex) {
            System.out.println("An exception occurred when trying to connect to the database.");
            ex.printStackTrace();
        }
    }

    /**
     * Creates a database and fills it with default data.
     */
    public void createAndPopulate() {
        createDatabase();
        createTables();
        populateDB();
    }

    /**
     * Populates the database.
     */
    public void populateDB() {
        int students = 100;
        int modules = 20;
        int degrees = 5;
        populateStudents(students);
        populateModules(modules);
        populateDegrees(degrees);
        populateDetailed();
        populateSchoolMember(students);
    }

    /**
     * Creates a database and then resets the connection to allow modification
     * of that database.
     */
    public void createDatabase() {
        try {
            s = c.prepareStatement("CREATE DATABASE uschool");
            s.execute();
        } catch (SQLException ex) {
            System.out.println("Unable to create database.");
            ex.printStackTrace();
        }

        try {
            if (connLoc == Location.HOME) {
                dbLoc = homeLoc + "uschool";
            } else if (connLoc == Location.UNI) {
                dbLoc = uniLoc + "uschool";
            }
            c = DriverManager.getConnection(dbLoc, cProp);
        } catch (SQLException ex) {
            System.out.println("Failed when trying to switch connection to new database.");
            ex.printStackTrace();
        }

    }

    /**
     * Creates tables. The individual tables which require fields to be set as non-null
     * are done so within their creation, rather than added with the other constraints.
     */
    public void createTables() {
        try {
            createDegreeProgrammes();
            createStudents();
            createModules();
            createDegreeRegistration();
            createModuleRegistration();
            createStudentRegistration();
            createSchoolMember();
            addConstraints();
        } catch (SQLException ex) {
            System.out.println("An error ocurred while attempting to create tables.");
            ex.printStackTrace();
        }
    }

    /**
     * Creates degree programmes table.
     * @throws SQLException
     */
    public void createDegreeProgrammes() throws SQLException {
        s = c.prepareStatement("CREATE TABLE DegreeProgramme ("
                + " degreeID integer PRIMARY KEY,"
                + " programmeName character(40) NOT NULL)");
        s.execute();
    }

    /**
     * Creates students table.
     * @throws SQLException
     */
    private void createStudents() throws SQLException {
        s = c.prepareStatement("CREATE TABLE Students ("
                + " firstName character(20) NOT NULL,"
                + " lastName character(40) NOT NULL,"
                + " universityID integer PRIMARY KEY)");
        s.execute();
    }

    /**
     * Creates modules table.
     * @throws SQLException
     */
    private void createModules() throws SQLException {
        s = c.prepareStatement("CREATE TABLE Modules ("
                + " moduleID integer PRIMARY KEY,"
                + " moduleName character(80) NOT NULL,"
                + " moduleLevel integer NOT NULL,"
                + " credits integer NOT NULL)");
        s.execute();
    }

    /**
     * Creates degree registration table.
     * @throws SQLException
     */
    private void createDegreeRegistration() throws SQLException {
        s = c.prepareStatement("CREATE TABLE DegreeRegistration ("
                + " degreeID integer NOT NULL,"
                + " universityID integer NOT NULL,"
                + " academicYear integer NOT NULL,"
                + " yearsOfStudy integer NOT NULL)");
        s.execute();
    }

    /**
     * Creates module registration table.
     * @throws SQLException
     */
    private void createModuleRegistration() throws SQLException {
        s = c.prepareStatement("CREATE TABLE ModuleRegistration ("
                + " academicYear integer NOT NULL,"
                + " moduleID integer NOT NULL,"
                + " universityID integer NOT NULL,"
                + " registrationCode integer NOT NULL,"
                + " notes text)");
        s.execute();
    }

    /**
     * Creates student registration table.
     * @throws SQLException
     */
    private void createStudentRegistration() throws SQLException {
        s = c.prepareStatement("CREATE TABLE StudentRegistration ("
                + " universityID integer NOT NULL,"
                + " yearOfStudy integer NOT NULL,"
                + " creditsRequired integer NOT NULL,"
                + " creditsRegistered integer NOT NULL "
                + "CONSTRAINT regCheck CHECK (creditsRegistered <= creditsRequired))");
        s.execute();
    }

    private void createSchoolMember() throws SQLException {
        // No constraints are added for university ID in the addConstraints, since
        // we do not store anyone's details other than students.
        s = c.prepareStatement("CREATE TABLE schoolMember ("
                + " username character(20) NOT NULL,"
                + " password character(20) NOT NULL,"
                + " universityID integer NOT NULL,"
                + " roleName character(20) NOT NULL)");
        s.execute();
    }

    /**
     * Adds constraints to the database.
     */
    private void addConstraints() {
        try {
            //degree registration constraint on university id
            s = c.prepareStatement("ALTER TABLE degreeregistration "
                    + "ADD CONSTRAINT sIDCons FOREIGN KEY (universityid) "
                    + "REFERENCES students (universityid) "
                    + "MATCH SIMPLE "
                    + "ON UPDATE NO ACTION "
                    + "ON DELETE NO ACTION");
            s.execute();

            // degree registration constraint on degree id
            s = c.prepareStatement("ALTER TABLE degreeregistration "
                    + "ADD CONSTRAINT degIDCons FOREIGN KEY (degreeid) "
                    + "REFERENCES degreeprogramme (degreeid) "
                    + "MATCH SIMPLE "
                    + "ON UPDATE NO ACTION "
                    + "ON DELETE NO ACTION");
            s.execute();

            // module registration constraint on module id
            s = c.prepareStatement("ALTER TABLE moduleregistration "
                    + "ADD CONSTRAINT mIDCons FOREIGN KEY (moduleid) "
                    + "REFERENCES modules (moduleid) "
                    + "MATCH SIMPLE "
                    + "ON UPDATE NO ACTION "
                    + "ON DELETE NO ACTION");
            s.execute();

            // module registration constraint on student id
            s = c.prepareStatement("ALTER TABLE moduleregistration "
                    + "ADD CONSTRAINT sIDCons FOREIGN KEY (universityid) "
                    + "REFERENCES students (universityid) "
                    + "MATCH SIMPLE "
                    + "ON UPDATE NO ACTION "
                    + "ON DELETE NO ACTION");
            s.execute();

            // student registration constraint on student id
            s = c.prepareStatement("ALTER TABLE studentregistration "
                    + "ADD CONSTRAINT mIDCons FOREIGN KEY (universityid) "
                    + "REFERENCES students (universityid) "
                    + "MATCH SIMPLE "
                    + "ON UPDATE NO ACTION "
                    + "ON DELETE NO ACTION");
            s.execute();
        } catch (SQLException ex) {
            System.out.println("Exception while attempting to create constraints.");
            ex.printStackTrace();
        }
    }

    /**
     * Populates the database with students.
     * @param number
     */
    private void populateStudents(int number) {
        try {
            s = c.prepareStatement("INSERT INTO students (firstname, lastname, universityid) VALUES ('Will', 'Smith', 1000001)");
            s.execute();
            s = c.prepareStatement("INSERT INTO students (firstname, lastname, universityid) VALUES ('Joe', 'Bloggs', 1000002)");
            s.execute();
            // Inserts dummy students. Leaves room for the first two created students
            s = c.prepareStatement("INSERT INTO students (universityid, firstname, lastname) VALUES (?, ?, ?)");
            for (int i = 1; i <= number; i++) {
                s.setInt(1, 1000002 + i);
                s.setString(2, "student" + i);
                s.setString(3, "surname" + i);
                s.execute();
            }
        } catch (SQLException ex) {
            System.out.println("Error while adding student");
            ex.printStackTrace();
        }
    }

    /**
     * Populates modules with basic names and random values as the credit value etc.
     * Also creates seven real CS modules
     * @param number
     */
    private void populateModules(int number) {
        try {
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, ?, ?, ?)");
            for (int i = 1; i <= number; i++) {
                s.setInt(1, i);
                s.setString(2, "module" + i);
                // 6 years of university, so 6 levels
                s.setInt(3, (int) (Math.random() * 6));
                // Random credit value rounded to nearest 10 (upwards only)
                int cr = (int) (Math.random() * 30);
                cr = (cr / 10) + 1;
                cr = cr * 10;
                s.setInt(4, cr);
                s.execute();
            }
            /**
             * Creates some 'real' modules
             */
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'SSC1', 2, 10)");
            s.setInt(1, number + 1);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'SSC2', 2, 20)");
            s.setInt(1, number + 2);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'Comm', 2, 10)");
            s.setInt(1, number + 3);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'Team', 2, 10)");
            s.setInt(1, number + 4);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'Japanese', 2, 20)");
            s.setInt(1, number + 5);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'CSA', 2, 10)");
            s.setInt(1, number + 6);
            s.execute();
            s = c.prepareStatement("INSERT INTO modules (moduleid, modulename, modulelevel, credits) VALUES (?, 'Models', 2, 10)");
            s.setInt(1, number + 7);
            s.execute();
        } catch (SQLException ex) {
            System.out.println("Error while adding module");
            ex.printStackTrace();
        }
    }

    private void populateDegrees(int number) {
        try {
            s = c.prepareStatement("INSERT INTO degreeprogramme (degreeid, programmename) VALUES (1, 'CS AI')");
            s.execute();
            s = c.prepareStatement("INSERT INTO degreeprogramme (degreeid, programmename) VALUES (2, 'CS SA')");
            s.execute();
            s = c.prepareStatement("INSERT INTO degreeprogramme (degreeid, programmename) VALUES (?, ?)");
            for (int i = 3; i <= number; i++) {
                s.setInt(1, i);
                s.setString(2, "degree" + i);
                s.execute();
            }
        } catch (SQLException ex) {
            System.out.println("Error while adding degree programme");
            ex.printStackTrace();
        }
    }

    private void populateDetailed() {
        try {

            // real student 1
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,3,1000001,1, '')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,4,1000001,1, 't')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,5,1000001,1, 'm')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,6,1000001,1, 'd')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,7,1000001,1, 'r')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,8,1000001,1, 'y')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,9,1000001,1, 't')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,10,1000001,1, 'x')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,2,1000001,1, '')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,21,1000001,1, 'a')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,22,1000001,1, 'b')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,23,1000001,1, 'c')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,24,1000001,1, 'd')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,25,1000001,1, 'e')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,26,1000001,1, 'f')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,27,1000001,1, 'z')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000001, 1, 120, 120)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000001, 2, 120, 120)");
            s.executeUpdate();

            s = c.prepareStatement("INSERT INTO degreeregistration "
                    + "(degreeid, universityid, academicyear, yearsofstudy) "
                    + "VALUES (1, 1000001, 2008, 3)");
            s.executeUpdate();
            // end of student 1

            // real student 2
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,3,1000002,1, '')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,4,1000002,1, 't')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,5,1000002,1, 'm')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,6,1000002,1, 'd')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,7,1000002,1, 'r')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,8,1000002,1, 'y')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,9,1000002,1, 't')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2008,10,1000002,1, 'x')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,2,1000002,1, '')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,21,1000002,1, 'a')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,22,1000002,1, 'b')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,23,1000002,1, 'c')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,24,1000002,1, 'd')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,25,1000002,1, 'e')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,26,1000002,1, 'f')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,27,1000002,1, 'z')");
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000002, 1, 120, 120)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000002, 2, 120, 120)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO degreeregistration "
                    + "(degreeid, universityid, academicyear, yearsofstudy) "
                    + "VALUES (2, 1000002, 208, 3)");
            s.executeUpdate();
// end of student 2 

            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000001, 2, 100, 100)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000002, 1, 100, 100)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO studentregistration "
                    + "(universityID, yearOfStudy, creditsRequired, creditsRegistered) "
                    + "VALUES (1000003, 4, 120, 100)");
            s.executeUpdate();


            s = c.prepareStatement("INSERT INTO degreeregistration "
                    + "(degreeid, universityid, academicyear, yearsofstudy) "
                    + "VALUES (3, 1000003, 2007, 4)");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2010,4,1000001,3, 'yes')");
            s.executeUpdate();
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicYear, moduleid, universityid, registrationCode, notes) "
                    + "VALUES (2009,2,1000001,2, 'maybe')");
            s.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error while creating some additional data.");
            ex.printStackTrace();
        }
    }

    private void populateSchoolMember(int number) {
        try {
            s = c.prepareStatement("SELECT * FROM students");
            ResultSet allStudents = s.executeQuery();
            s = c.prepareStatement("INSERT INTO schoolmember "
                    + "(username, password, universityid, rolename) "
                    + "VALUES (?,?,?,?)");
            while (allStudents.next()) {
                String fname = allStudents.getString("firstname").toLowerCase();
                String lname = allStudents.getString("lastname").toLowerCase();
                int id = allStudents.getInt("universityid");
                /**
                 * Creates a username from student data. E.g. username of Bill Bilson
                 * with id number 1000321 would be bxb321. This does leave some small
                 * chance for collisions, but that can be worried about later.
                 */
                String username = fname.charAt(0) + "x" + lname.charAt(0) + Integer.toString(id).substring(4);
                String password = makeRandomPassword(8);
                String rolename = "student";
                s.setString(1, username);
                s.setString(2, password);
                s.setInt(3, id);
                s.setString(4, rolename);
                s.execute();
            }
            // Adds a member with a lecturer role
            s = c.prepareStatement("INSERT INTO schoolmember "
                    + "(username, password, universityid, rolename) "
                    + "VALUES ('exr007','tester',4000001,'lecturer')");
            s.execute();
            // Adds an administrator
            s = c.prepareStatement("INSERT INTO schoolmember "
                    + "(username, password, universityid, rolename) "
                    + "VALUES ('admin','admin',9999999,'administrator')");
            s.execute();
        } catch (SQLException ex) {
            System.out.println("SQL error when populating school members.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Something broke when populating school member");
            ex.printStackTrace();
        }
    }

    /**
     * Makes a random password.
     */
    private String makeRandomPassword(int length) {
        String[] alph = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        int[] num = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        StringBuilder pass = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * 36);
            if (rand < 26) { // rand is within alphabet range
                double moreRand = Math.random(); // another random value
                if (moreRand < 0.5) {
                    // append the uppercase letter rather than lowercase
                    pass.append(alph[rand].toUpperCase());
                } else {
                    // append lowercase
                    pass.append(alph[rand]);
                }
            } else { // in number range
                pass.append(num[rand - 26]); // append number
            }
        }
        return pass.toString();
    }
}
