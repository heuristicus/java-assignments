package Database;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.servlet.ServletOutputStream;

/**
 * IMPROVEMENTS:
 *
 * Take data from tables with which to fill the columns - e.g. entering a module ID
 * will grab all the module data from the database - the resultset can then be used
 * to get all the other necessary data. Only really applies to registering the
 * student on a module.
 *
 * @author michal
 */
public class DBBase {

    // Home "jdbc:postgresql://localhost:5432/postgres";
    // School "jdbc:postgresql://dbteach/ssclibrary";
    Connection c;
    BufferedReader r;
    String user;
    String pw;
    String dbLoc;
    Properties cProp;
    Reports reports;
    Queries queries;

    public static void main(String[] args) {
        DBBase b = new DBBase();
        try {
            Map m = b.reportStudent(1000001);
            System.out.println(m);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Constructor - initialises the db location and passwords, as well as the reader
     * to read from console.
     * @param dbLoc
     * @param user
     * @param pw
     */
    public DBBase(String dbLoc, String user, String pw) {
        this.dbLoc = dbLoc;
        this.user = user;
        this.pw = pw;
        r = new BufferedReader(new InputStreamReader(System.in));
        connectDB();
        initGenerators();
        waitForInput();
    }

    /**
     * Starts a connection to a default database location and waits for something
     * to happen.
     */
    public DBBase() {
        this.dbLoc = "jdbc:postgresql://localhost:5432/uschool";
        this.user = "mich";
        this.pw = "mich";
        connectDB();
        initGenerators();
    }

    //<editor-fold defaultstate="collapsed" desc="Methods to connect or disconnect from the database.">
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
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(dbLoc, cProp);
        } catch (SQLException ex) {
            System.out.println("An exception occurred when trying to connect to the database. Oops.");
            ex.printStackTrace();
//        }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Closes the connection to the database.
     */
    public final void closeConnection() {
        try {
            c.close();
        } catch (SQLException ex) {
            System.out.println("Error while closing database connection.");
            ex.printStackTrace();
        }
    }

    /**
     * Initialises the objects used for performing queries and reports.
     */
    public final void initGenerators() {
        queries = new Queries(c);
        reports = new Reports(c, queries);

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods to wait for commandline input and then deal with it..">
    /**
     * Waits for the user to input a command, and calls various methods based on
     * which command is entered.
     */
    public final void waitForInput() {
        System.out.println("This program will give you access to a postgreSQL database.\n"
                + "Commands are addmodule, regstud, repstud, repmod, exit.\n"
                + "Type one of these and then follow the instructions.");
        String cmd = "";
        System.out.print("$ ");
        while (!cmd.equals("exit")) {
            try {
                cmd = r.readLine();
                cmd = cmd.toLowerCase();
                if (cmd.equals("addmodule")) {
                    addModule();
                } else if (cmd.equals("regstud")) {
                    registerStudentToModule();
                } else if (cmd.equals("repstud")) {
                    reportStudent();
                } else if (cmd.equals("repmod")) {
                    reportModule();
                } else if (cmd.equals("exit")) {
                    System.exit(0);
                } else {
                    System.out.println("Unrecognised command.");
                    System.out.print("$ ");
                    continue;
                }
            } catch (IOException ex) {
                System.out.println("Some error ocurred with the IO while waiting for input. Try again.");
            }
            System.out.print("$ ");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Methods to do queries on the database.">
    /**
     * Gets the details of a user. Takes their usename and an MD5 of their password.
     * @param username Username.
     * @param password SHA-1 hex value of the password.
     * @return
     */
    public ResultSet getUserDetails(String username, String password) {
        return queries.getUserDetails(username, password);
    }

    /**
     * Adds a module to the database directly, without doing any checks on the data.
     * Intended for use by other classes.
     * @param modName
     * @param modID
     * @param modLevel
     * @param modCredits
     * @throws SQLException
     */
    public void addModule(String modName, int modLevel, int modCredits) throws SQLException {
        queries.addModule(modName, modLevel, modCredits);
    }

    /**
     * Adds a new module to the database with the user's input values.
     */
    private void addModule() {
        String mName = "--err--";
        int mID = -1, mLv = -1, mCv = -1;
        try {
            mName = readModName();
            mLv = readModLevel();
            mCv = readModCreditVal();
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when attempting to do a read on modules. Try again.");
            addModule();
        }
        addModuleExec(mName, mLv, mCv);
    }

    /**
     * Attempts to add a new module to the database table. To be used with the
     * commandline.
     * @param modName
     * @param modID
     * @param modLevel
     * @param modCredits
     * @throws SQLException
     */
    private void addModuleExec(String modName, int modLevel, int modCredits) {
        try {
            queries.addModule(modName, modLevel, modCredits);
        } catch (SQLException ex) {
            System.out.println("Exception when attempting to add a module. Try again.");
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
    }

    /**
     * Registers a student to a module with the user's input values.
     */
    private void registerStudentToModule() {
        String mName = "--err--", notes = "";
        int acYear = -1, mID = -1, sID = -1, regCode = -1;
        try {
            acYear = readAcademicYear();
            mID = readModID();
            sID = readStudentID();
            notes = readNotes();
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when attempting to do a read on modules. Try again.");
            addModule();
        }
        registerStudentToModuleExec(acYear, mID, sID, notes);
    }

    /**
     * Attempts to register a student onto a module. Intended for use by outside
     * classes.
     * @param acYear
     * @param mID
     * @param sID
     * @param regCode
     * @param notes
     */
    public void registerStudentToModule(int acYear, int mID, int sID, String notes) throws SQLException {
        queries.addRegistrationToModule(acYear, mID, sID, notes);
    }

    /**
     * Attempts to register a student onto a module. To be used with the commandline
     * @param acYear
     * @param mID
     * @param sID
     * @param regCode
     * @param notes
     */
    private void registerStudentToModuleExec(int acYear, int mID, int sID, String notes) {
        try {
            queries.addRegistrationToModule(acYear, mID, sID, notes);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
    }

    public void addRegistrationToModule(int mID, int sID, int acYear, String notes) throws SQLException {
        queries.addRegistrationToModule(acYear, mID, sID, notes);
    }

    /**
     * Removes a student's registration from a module.
     * @param acYear
     * @param mID
     * @param sID
     * @throws SQLException
     */
    public void removeRegistrationFromModule(int acYear, int mID, int sID) throws SQLException {
        queries.removeRegistrationFromModule(acYear, mID, sID);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Various methods to do reports">
    public Map reportStudent(int studentID) throws SQLException {
        return reports.reportStudent(studentID);
    }

    /**
     * reports on a student given the required parameters. Intended for use by servlets.
     * @param studentID
     * @param srvOut
     * @throws SQLException
     */
    public void reportStudent(int studentID, ServletOutputStream srvOut) throws SQLException {
        reports.reportStudent(studentID, srvOut);
    }

    /**
     * Creates a report for a student based on the student ID provided by the user.
     */
    private void reportStudent() {
        int sID = -1;
        try {
            sID = readStudentID();
            ServletOutputStream dummy = null; // dummy so that you can pass a null.
            reports.reportStudent(sID, dummy);
        } catch (IOException ex) {
            System.out.println("IO exception when attempting to read student id.");
            reportStudent();
        } catch (NumberFormatException e) {
            System.out.println("The student ID must be an integer.");
            reportStudent();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("error with array length in Student report.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Error while attempting to compile student report. Try again.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("other exception in student report.");
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
    }

    /**
     * Returns a map containing data about the module in that year.
     * @param modID
     * @param year
     * @return
     */
    public Map reportModule(int modID, int year) throws SQLException {
        return reports.reportModule(modID, year);
    }

    /**
     * Reports on a module given some parameters. Intended for use by servlets.
     * @param modID
     * @param year
     * @param htmlOut
     * @throws SQLException
     */
    public void reportModule(int modID, int year, ServletOutputStream htmlOut) throws SQLException {
        reports.reportModule(modID, year, htmlOut);
    }

    /**
     * Reports data about a specific module in a specific academic year.
     */
    private void reportModule() {
        int modID = -1;
        int year = -1;
        try {
            modID = readModID();
            year = readAcademicYear();
            ServletOutputStream dummy = null; // dummy so that you can pass a null.
            reports.reportModule(modID, year, dummy);
        } catch (SQLException ex) {
            System.out.println("SQL error while attempting to compile module report. Try again.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error while attempting to compile module report.Try again.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Other exception while reporting module.");
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
    }

    public Map reportCredits(int academicYear) throws SQLException{
        return reports.reportCredits(academicYear);
    }

    public void reportCredits(int academicYear, ServletOutputStream htmlOut) throws SQLException {
        reports.reportCredits(academicYear, htmlOut);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods to read data from the commandline.">
    /**
     * The following methods are used as some simple validation on the console
     * input that the user provides.
     *
     */
// TODO move these methods to a separate class
    /**
     * Reads the module name
     * @return
     * @throws IOException
     */
    private String readModName() throws IOException {
        System.out.println("Please enter the name of the new module, and then press enter.");
        System.out.print("$ ");
        String modName = r.readLine();
        return modName;
    }

    /**
     * Reads a module ID
     * @return
     * @throws IOException
     */
    private int readModID() throws IOException {
        System.out.println("Please enter the module ID.");
        System.out.print("$ ");
        int modID = -1;
        try {
            modID = Integer.parseInt(r.readLine());
        } catch (NumberFormatException e) {
            System.out.println("The value of module ID must be an integer.");
            readModID();
        }
        return modID;
    }

    /**
     * reads a module level
     * @return
     * @throws IOException
     */
    private int readModLevel() throws IOException {
        System.out.println("Please enter the module level.");
        System.out.print("$ ");
        int modLevel = -1;
        try {
            modLevel = Integer.parseInt(r.readLine());
        } catch (NumberFormatException e) {
            System.out.println("The module level must be an integer.");
        }
        return modLevel;
    }

    /**
     * Reads the moule credit value
     * @return
     * @throws IOException
     */
    private int readModCreditVal() throws IOException {
        System.out.println("Please enter the credit value of the module.");
        System.out.print("$ ");
        int modCreditVal = -1;
        try {
            modCreditVal = Integer.parseInt(r.readLine());
        } catch (NumberFormatException e) {
            System.out.println("The module credit value must be an integer.");
        }
        return modCreditVal;
    }

    /**
     * reads notes
     * @return
     * @throws IOException
     */
    private String readNotes() throws IOException {
        System.out.println("Please enter the notes that you would like to enter.");
        System.out.print("$ ");
        String notes = r.readLine();
        return notes;
    }

    /**
     * Gets a student's ID number.
     * @return
     * @throws IOException
     */
    private int readStudentID() throws IOException {
        System.out.println("Please enter the student's ID number.");
        System.out.print("$ ");
        int studentID = -1;
        try {
            String strID = r.readLine();
            if (strID.length() != 7) {
                System.out.println("The student ID number must be a 7 digit integer.");
                return readStudentID();
            }
            studentID = Integer.parseInt(strID);
        } catch (NumberFormatException e) {
            System.out.println("The student ID number must be a 7 digit integer.");
            return readStudentID();
        }
        return studentID;
    }

    /**
     * Gets the registration code of the module.
     * @return
     * @throws IOException
     */
    private int readRegistrationCode() throws IOException {
        System.out.println("Please enter the registration code.");
        System.out.print("$ ");
        int registrationCode = -1;
        try {
            registrationCode = Integer.parseInt(r.readLine());
        } catch (NumberFormatException e) {
            System.out.println("The registration code must be an integer.");
            return readRegistrationCode();
        }
        return registrationCode;
    }

    /**
     * Gets the academic year of the module. Integer between one and six.
     * @return
     * @throws IOException
     */
    private int readAcademicYear() throws IOException {
        System.out.println("Please enter the academic year of the module.");
        System.out.print("$ ");
        int academicYear = -1;
        try {
            academicYear = Integer.parseInt(r.readLine());
        } catch (NumberFormatException e) {
            System.out.println("The module academic year must be an integer. Try again");
            return readAcademicYear();
        }
        if (academicYear > Calendar.getInstance().get(Calendar.YEAR) || academicYear < 1980) {
            System.out.println("The academic year must be between 1980 and the current year.");
            return readAcademicYear();
        }
        return academicYear;

    }
    //</editor-fold>
}
