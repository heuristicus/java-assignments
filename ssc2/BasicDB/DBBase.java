/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBBase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

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
    PreparedStatement s;
    String user;
    String pw;
    String dbLoc;
    Properties cProp;

    public static void main(String[] args) {
        DBBase b = new DBBase("jdbc:postgresql://localhost:5432/uschool", "mich", "mich");
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
        waitForInput();
//        reportModule();
//        insertbadmodule();
    }

    public void insertbadmodule() {
        try {
            s = c.prepareStatement("INSERT INTO degreeprogramme (degreeid, programmename) VALUES (null, null)");
            s.execute();
        } catch (SQLException ex) {
            System.out.println("oh good!");
            ex.printStackTrace();
        }
        System.out.println("oh no!");
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
            System.out.println("An exception occurred when trying to connect to the database. Oops.");
            ex.printStackTrace();
        }
    }

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

    /**
     * Adds a new module to the database with the user's input values.
     */
    private void addModule() {
        String mName = "--err--";
        int mID = -1, mLv = -1, mCv = -1;
        try {
            mName = readModName();
            mID = readModID();
            mLv = readModLevel();
            mCv = readModCreditVal();
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when attempting to do a read on modules. Try again.");
            addModule();
        }
        addModuleExec(mName, mID, mLv, mCv);
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
            regCode = readRegistrationCode();
            notes = readNotes();
        } catch (IOException ex) {
            System.out.println("An IO exception ocurred when attempting to do a read on modules. Try again.");
            addModule();
        }
        registerStudentToModuleExec(acYear, mID, sID, regCode, notes);
    }

    /**
     * Creates a report for a student based on the student ID provided by the user.
     */
    private void reportStudent() {
        int sID = -1;
        StringBuilder builder = new StringBuilder();
        System.out.println("Enter the student ID to report on.");
        System.out.print("$ ");
        try {
            sID = Integer.parseInt(r.readLine());
        } catch (IOException ex) {
            System.out.println("IO exception when attempting to read student id.");
            reportStudent();
        } catch (NumberFormatException e) {
            System.out.println("The student ID must be an integer.");
            reportStudent();
        }
        try {
            /**
             * Gets the first and last names of the received studentID.
             */
            s = c.prepareStatement("SELECT firstname, lastname FROM students "
                    + "WHERE universityID = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, sID);
            ResultSet studentName = s.executeQuery();
            studentName.next();
            String fName = studentName.getString("firstname");
            String sName = studentName.getString("lastname");
            builder.append("Student: ");
            builder.append(fName.trim());
            builder.append(" ");
            builder.append(sName.trim());
            builder.append("\nUniversity ID: ");
            builder.append(sID);
            builder.append("\n");
            /**
             * Gets the degree programme of the student.
             */
            s = c.prepareStatement("SELECT programmename "
                    + "FROM degreeprogramme WHERE degreeid = "
                    + "(SELECT degreeid FROM degreeregistration WHERE universityID = ?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, sID);
            ResultSet programmeName = s.executeQuery();
            programmeName.next();
            String progName = programmeName.getString("programmename");
            builder.append("Degree Programme: ");
            builder.append(progName.trim());
            builder.append("\n");
            // Prints the student's details.
            System.out.println(builder);

            /**
             * gets the first academic year for the student. returns a 4 digit int
             * 
             */
            s = c.prepareStatement("SELECT academicyear, yearsofstudy "
                    + "FROM degreeregistration WHERE universityid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, sID);
            ResultSet studyYear = s.executeQuery();
            studyYear.first();
            int firstYear = studyYear.getInt("academicyear"); // first year of study
            int numberYears = studyYear.getInt("yearsofstudy"); // the number of years of study
            ResultSet[] modulesEachYear = new ResultSet[numberYears];

            /**
             * Gets the moduleids of modules that the student was registered on for each
             * academic year.
             */
            for (int i = firstYear; i < firstYear + numberYears; i++) {
                s = c.prepareStatement("SELECT moduleid "
                        + "FROM moduleregistration WHERE universityid = ? "
                        + "AND academicyear = ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                s.setInt(1, sID);
                s.setInt(2, i);
                modulesEachYear[i - firstYear] = s.executeQuery();
//                printResults(modulesEachYear[i - firstYear], true);
            }
            /**
             * Goes through the resultsets and prints module data.
             */
            int yrCount = 1; // counter to add years to string.
            for (ResultSet resultSet : modulesEachYear) {
                System.out.printf("Year %d Modules: ", yrCount);
//                System.out.printf("%s %15s %7s %20s %s\n", "Module ID", "Module Name", "Credits", "Registration Code", "Notes");
                while (resultSet.next()) {
                    int modID = resultSet.getInt("moduleid");
                    /**
                     * Gets registration code and notes from the module registration table.
                     */
                    s = c.prepareStatement("SELECT registrationcode, notes "
                            + "FROM moduleregistration "
                            + "WHERE moduleid = ?",
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    s.setInt(1, modID);
                    ResultSet modRegDetails = s.executeQuery();
                    /**
                     * gets the modulename and credits from modules table.
                     */
                    s = c.prepareStatement("SELECT modulename,credits "
                            + "FROM modules "
                            + "WHERE moduleid = ?",
                            ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    s.setInt(1, modID);
                    ResultSet modNameCred = s.executeQuery();
                    /**
                     * Grabs contents from the two resultsets and prints them.
                     */
                    modNameCred.first();
                    modRegDetails.first();
                    String mName = modNameCred.getString("modulename");
                    int credits = modNameCred.getInt("credits");
                    int registrationCode = modRegDetails.getInt("registrationcode");
                    String notes = modRegDetails.getString("notes");
                    System.out.println("\n");
                    System.out.println("Module ID: " + modID);
                    System.out.println("Module Name: " + mName);
                    System.out.println("Credits: " + credits);
                    System.out.println("Registration Code: " + registrationCode);
                    System.out.println("Notes " + notes);
//                    System.out.printf("%7d %15s %7d %20d %s\n", modID, mName.trim(), credits, registrationCode, notes.trim());
                }
                yrCount++;
            }
        } catch (SQLException ex) {
            System.out.println("Error while attempting to compile student report. Try again.");
            ex.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("error with array length.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("other exception in student report.");
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
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

            s = c.prepareStatement("SELECT universityid FROM moduleregistration "
                    + "WHERE moduleid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, modID);
            ResultSet studentIDs = s.executeQuery();

            s = c.prepareStatement("SELECT modulename FROM modules "
                    + "WHERE moduleid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, modID);
            ResultSet modName = s.executeQuery();
            String mName = modName.getString("modulename");

            System.out.println("Module ID: " + modID);
            System.out.println("Module Name: " + mName);
            System.out.println("Module Year: " + year);

            s = c.prepareStatement("SELECT COUNT(universityid) AS studcount FROM moduleregistration "
                    + "WHERE moduleid = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            s.setInt(1, modID);
            ResultSet studentCount = s.executeQuery();
            if (studentCount.first()) {
                System.out.println("Number of students on module: " + studentCount.getInt("studcount"));
            }
            printResults(studentIDs, false);
            //Goes through all the students in the results of the query above.
            System.out.printf("%13s %15s %20s %2s %s \n", "University ID", "First name", "Second name", "Registration code", "Notes");
            while (studentIDs.next()) {
                int curStudID = studentIDs.getInt("universityid");
                s = c.prepareStatement("SELECT students.universityid, firstname, lastname, registrationcode, notes "
                        + "FROM students, moduleregistration "
                        + "WHERE moduleid = ?"
                        + "AND students.universityID = ? "
                        + "AND moduleregistration.universityid = ? "
                        + "AND moduleregistration.academicyear = ?",
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                s.setInt(1, modID);
                s.setInt(2, curStudID);
                s.setInt(3, curStudID);
                s.setInt(4, year);
                ResultSet studentDetails = s.executeQuery();
                // Prepare data in sets for extraction.
                if (studentDetails.first()) {
                    int uniID = studentDetails.getInt("universityid");
                    String fname = studentDetails.getString("firstname");
                    String sname = studentDetails.getString("lastname");
                    int rCode = studentDetails.getInt("registrationcode");
                    String notes = studentDetails.getString("notes");
                    System.out.printf("%d %15s %20s %2d %s \n", uniID, fname.trim(), sname.trim(), rCode, notes.trim());
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL error while attempting to compile module report. Try again.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error while attempting to compile module report.Try again.");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Other exception in report module.");
            ex.printStackTrace();
        } finally {
            waitForInput();
        }
    }

    /**
     * Attempts to add a new module to the database table.
     * @param modName
     * @param modID
     * @param modLevel
     * @param modCredits
     * @throws SQLException
     */
    private void addModuleExec(String modName, int modID, int modLevel, int modCredits) {
        if (modName.equals("--err--")) {
            System.out.println("Some error ocurred while getting module details.");
            addModule();
        } else {
            try {
                s = c.prepareStatement("INSERT INTO modules (modulename, moduleid, modulelevel,credits) " + "VALUES(?, ?, ?, ?)");
                s.setString(1, modName);
                s.setInt(2, modID);
                s.setInt(3, modLevel);
                s.setInt(4, modCredits);
                s.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Exception when attempting to add a module. Try again.");
                ex.printStackTrace();
            } finally {
                waitForInput();
            }
        }
    }

    /**
     * Attempts to register a student onto a module.
     * @param acYear
     * @param mID
     * @param sID
     * @param regCode
     * @param notes
     */
    private void registerStudentToModuleExec(int acYear, int mID, int sID, int regCode, String notes) {
        try {
            s = c.prepareStatement("INSERT INTO moduleregistration "
                    + "(academicyear, moduleid, universityid, registrationcode, notes) "
                    + "VALUES(?, ?, ?, ?, ?)");
            s.setInt(1, acYear);
            s.setInt(2, mID);
            s.setInt(3, sID);
            s.setInt(4, regCode);
            s.setString(5, notes);
            s.executeUpdate();
        } catch (SQLException ex) {
            // TODO do something when this exception is caught.
            System.out.println(ex.getMessage());
        } finally {
            waitForInput();
        }
    }

    /**
     * Prints the results from a resultset. And then returns the pointer to the
     * start of the set so that it can be re-read.
     * @param r
     */
    private void printResults(ResultSet r, boolean concise) {
        try {
            int cCount = r.getMetaData().getColumnCount();
            int rowCount = getRowNumber(r);
            // Used for storing data from table in order to use printf better.
            String[][] values;
            // Adds on column labels
            if (!concise) {
                // rowcount increased by one to store column labels.
                values = new String[rowCount + 1][cCount];
                for (int i = 1; i <= cCount; i++) {
                    values[0][i - 1] = r.getMetaData().getColumnLabel(i);
                }
            } else {
                values = new String[rowCount][cCount];
            }
            /**
            /* Sets the result set to before the first result, in case it has been
            /* Used earlier.
             */
            r.first();
            // i starts at 0 if the report is concise, otherwise starts at 1.
            for (int i = concise ? 0 : 1; i
                    < values.length; i++) {
                for (int j = 1; j <= cCount; j++) {
                    values[i][j - 1] = r.getObject(j).toString().trim();
                }
                r.next();
            }
            // Prints all the values.
            for (String[] strings : values) {
                for (String string : strings) {
                    System.out.print(string);
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            System.out.println("Error while printing result set. Try again.");
            ex.printStackTrace();
        } finally {
            try {
                r.beforeFirst();
            } catch (SQLException ex) {
                System.out.println("Could not return to the start of the resultSet. This isn't good.");
                ex.printStackTrace();
            }
        }

    }

    /**
     * Gets the number of rows present in a specific resultset.
     * @param r resultset to check.
     * @return The number of rows the resultset has.
     */
    private int getRowNumber(ResultSet r) {
        int rowCount = 0;
        try {
            r.beforeFirst();
            while (r.next()) {
                rowCount++;
            }
            r.beforeFirst();
        } catch (SQLException ex) {
            System.out.println("Exception ocurred while attempting to get row number.");
            ex.printStackTrace();
        }
        return rowCount;
    }

    /**
     * The following methods are used as some simple validation on the console
     * input that the user provides.
     *
     */
// TODO move these methods to a separate class
    /**
     * reads module details
     * @param moduleID
     * @return
     * @throws SQLException
     */
    private ResultSet readModuleDetails(int moduleID) throws SQLException {
        s = c.prepareStatement("SELECT * FROM modules WHERE moduleID = ?");
        s.setInt(1, moduleID);
        return s.executeQuery();
    }

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
}
