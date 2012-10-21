package Database;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author michal
 */
public class Reports {

    Connection c;
    PreparedStatement s;
    Queries queries;

    public Reports(Connection c, Queries queries) {
        this.c = c;
        this.queries = queries;
    }

    //<editor-fold defaultstate="collapsed" desc="Student reports">
    /**
     * Returns the student report as a map.
     * The structure of the map is as follows:
     * t
     * @param studentID
     * @return
     * @throws SQLException
     */
    public Map reportStudent(int studentID) throws SQLException {
        // Hashmap to store all results of the report.
        Map studentMap = new HashMap<String, String>();
        // THE HEADERS ***MUST*** BE THE SAME AS THE KEYS LATER ON
        String[] tableHeaders = {"Module ID", "Module Name", "Credits", "Registration Code", "Notes"};
        studentMap.put("tableHeaders", tableHeaders);
        /**
         * Gets the first and last names of the received studentID.
         */
        ResultSet name = queries.getFirstLastByID(studentID);
        name.first();
        String fName = name.getString("firstname");
        String sName = name.getString("lastname");


        // Creates a map to hold the first and last names
        Map studentName = new HashMap<String, String>();
        // Puts names into the map
        studentName.put("firstName", fName.trim());
        studentName.put("secondName", sName.trim());
        // Puts the map into the main map
        studentMap.put("studentName", studentName);
        studentMap.put("uniID", studentID);

        /**
         * Gets the degree programme of the student.
         */
        ResultSet programmeName = queries.getProgrammeNameFromUniID(studentID);
        programmeName.first();
        String progName = programmeName.getString("programmename");

        // Put the programme name into the map
        studentMap.put("progName", progName);

        /**
         * gets the first academic year for the student.
         *
         */
        ResultSet studyYear = queries.getAcYears(studentID);
        studyYear.first();
        int firstYear = studyYear.getInt("academicyear"); // first year of study
        int numberYears = studyYear.getInt("yearsofstudy"); // the number of years of study
        ResultSet[] modulesEachYear = new ResultSet[numberYears];
        ResultSet[] creditsEachYear = new ResultSet[numberYears];

        /**
         * Gets the moduleids of modules that the student was registered on for each
         * academic year.
         */
        for (int i = firstYear; i < firstYear + numberYears; i++) {
//            System.out.println(i);
            modulesEachYear[i - firstYear] = queries.getModuleIDByUIDAndYear(studentID, i);
            creditsEachYear[i - firstYear] = queries.getCreditsForYear(studentID, i);
        }
        /**
         * Goes through the resultsets and prints module data.
         */
        // Hashmap to keep all of the data for each year in.
        Map yearMap = new HashMap<String, String>();
        int yrCount = 1; // counter to add years to string.
        for (ResultSet resultSet : modulesEachYear) {
            creditsEachYear[yrCount - 1].first();
            int numCredits = creditsEachYear[yrCount - 1].getInt("totalcredits");

            /**
             * Creates a hashmap for each year.
             * The intended structure is as follows:
             *
             * yearMap
             *     |- year credits - string
             *     |- modules
             *            |- module (can be many of these)
             *                  |- modID
             *                  |- modName
             *                  |- credits
             *                  |- regCode
             *                  |- notes
             */
            Map curYearMap = new HashMap<String, String>();
            curYearMap.put("yearCredits", numCredits);

            // Create a hashmap in which to put each module
            Map moduleMap = new HashMap<String, String>();

            // Goes through the current resultset and gets all the module details from it.
            int modCount = 1; // counter to assist with map naming.
            while (resultSet.next()) {
                int modID = resultSet.getInt("moduleid");
                //Gets registration code and notes from the module registration table.
                ResultSet modRegDetails = queries.getModRegByModIDAndYear(modID, firstYear - 1 + yrCount);
                // gets the modulename and credits from modules table.
                ResultSet modNameCred = queries.getModByModID(modID);
                /**
                 * Grabs contents from the two resultsets.
                 */
                modNameCred.first();
                modRegDetails.first();
                String mName = modNameCred.getString("modulename");
                int credits = modNameCred.getInt("credits");
                int registrationCode = modRegDetails.getInt("registrationcode");
                String notes = modRegDetails.getString("notes");
                // Create a map to store the stuff in.
                Map curModMap = new HashMap<String, String>();
                // THESE KEYS ***MUST*** BE THE SAME AS THE STRINGS IN THE TABLEHEADER
                curModMap.put("Module ID", modID);
                curModMap.put("Module Name", mName.trim());
                curModMap.put("Credits", credits);
                curModMap.put("Registration Code", registrationCode);
                curModMap.put("Notes", notes.trim());
                // Put the current module into the map of all modules this year.
                moduleMap.put("mod" + modCount, curModMap);
                modCount++;
            }
            // Put this year's modules into the current year map, making sure that
            // the key name is different.
            curYearMap.put("modules" + yrCount, moduleMap);
            // Finished this year, so put the current year map into the main one.
            yearMap.put("" + yrCount, curYearMap);
            yrCount++;
        }
        // Puts the map containing all the data for every year into the main map.
        studentMap.put("years", yearMap);
        return studentMap;
    }

    /**
     * Creates a series of result sets from which the data required to compile
     * a student report can be extracted. Can take just a student ID and a null
     * value for the servlet stream, which will simply print the data to console,
     * otherwise it will put data into HTML tags and write it to the output stream.
     * @param studentID
     * @param htmlOut The servlet output stream to output HTML tags to.
     */
    public void reportStudent(int studentID, ServletOutputStream htmlOut) throws SQLException {
        //!!!!!!!!TAKE CARE, htmlOut CAN (AND WILL) BE NULL!!!!!!!!
        boolean toConsole;

        if (htmlOut == null) {
            toConsole = true; // going to be outputting to the console
        } else {
            toConsole = false;// outputting in html.
        }
        /**
         * Gets the first and last names of the received studentID.
         */
        ResultSet studentName = queries.getFirstLastByID(studentID);
        StringBuilder builder = new StringBuilder();
        studentName.first();
        String fName = studentName.getString("firstname");
        String sName = studentName.getString("lastname");
        if (toConsole) { //writing to stringbuilder to out to console later.
            builder.append("Student: ");
            builder.append(fName.trim());
            builder.append(" ");
            builder.append(sName.trim());
            builder.append("\nUniversity ID: ");
            builder.append(studentID);
            builder.append("\n");
        } else { // writing html output to the servlet.
            try {
                // writing html output to the servlet.
                htmlOut.println("<html>");
                htmlOut.println("<head>");
                htmlOut.println("<title>studentReport</title>");
                htmlOut.println("</head>");
                htmlOut.println("<body>");
                htmlOut.println("Student Name: " + fName.trim() + " " + sName.trim() + "<BR>");
                htmlOut.println("University ID: " + studentID + "<BR>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing student details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing student details to servlet.");

            }
        }
        /**
         * Gets the degree programme of the student.
         */
        ResultSet programmeName = queries.getProgrammeNameFromUniID(studentID);
        programmeName.first();
        String progName = programmeName.getString("programmename");
        if (toConsole) { // To console.
            builder.append("Degree Programme: ");
            builder.append(progName.trim());
            builder.append("\n");
            // Prints the student's details.
            System.out.println(builder);
        } else { // To HTML.
            try {
                htmlOut.print("Degree Programme: " + progName.trim() + "<BR>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing student details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing student details to servlet.");

            }
        }

        /**
         * gets the first academic year for the student.
         *
         */
        ResultSet studyYear = queries.getAcYears(studentID);
        studyYear.first();
        int firstYear = studyYear.getInt("academicyear"); // first year of study
        int numberYears = studyYear.getInt("yearsofstudy"); // the number of years of study
        ResultSet[] modulesEachYear = new ResultSet[numberYears];
        ResultSet[] creditsEachYear = new ResultSet[numberYears];

        /**
         * Gets the moduleids of modules that the student was registered on for each
         * academic year.
         */
        for (int i = firstYear; i < firstYear + numberYears; i++) {
            modulesEachYear[i - firstYear] = queries.getModuleIDByUIDAndYear(studentID, i);
            creditsEachYear[i - firstYear] = queries.getCreditsForYear(studentID, i);
        }
        /**
         * Goes through the resultsets and prints module data.
         */
        int yrCount = 1; // counter to add years to string.
        for (ResultSet resultSet : modulesEachYear) {
            creditsEachYear[yrCount - 1].first();
            int numCredits = creditsEachYear[yrCount - 1].getInt("totalcredits");
            if (toConsole) {
                System.out.printf("Year %d Modules: ", yrCount);
                System.out.printf("Credits registered: %d\n", numCredits);
            } else {
                try {
                    htmlOut.println("Year " + yrCount + "<BR>");
                    htmlOut.println("Total Credits: " + numCredits + "<BR>");
                    htmlOut.println("Modules:");
                    htmlOut.println("<table border=\"1\">");
                    htmlOut.println("<tr>");
                    htmlOut.println("<td>Module ID</td>");
                    htmlOut.println("<td>Module Name</td>");
                    htmlOut.println("<td>Credits</td>");
                    htmlOut.println("<td>Registration Code</td>");
                    htmlOut.println("<td>Notes</td>");
                    htmlOut.println("</tr>");
                } catch (IOException ex) {
                    System.out.println("IO exception while writing student details to servlet.");
                } catch (Exception ex) {
                    System.out.println("Other exception while writing student details to servlet.");
                }
            }
            while (resultSet.next()) {
                int modID = resultSet.getInt("moduleid");
                //Gets registration code and notes from the module registration table.
                ResultSet modRegDetails = queries.getModRegByModIDAndYear(modID, firstYear - 1 + yrCount);
                // gets the modulename and credits from modules table.
                ResultSet modNameCred = queries.getModByModID(modID);
                /**
                 * Grabs contents from the two resultsets and prints them.
                 */
                modNameCred.first();
                modRegDetails.first();
                String mName = modNameCred.getString("modulename");
                int credits = modNameCred.getInt("credits");
                int registrationCode = modRegDetails.getInt("registrationcode");
                String notes = modRegDetails.getString("notes");
                if (toConsole) {
                    System.out.println("\n");
                    System.out.println("Module ID: " + modID);
                    System.out.println("Module Name: " + mName);
                    System.out.println("Credits: " + credits);
                    System.out.println("Registration Code: " + registrationCode);
                    System.out.println("Notes " + notes);
//                    System.out.printf("%7d %15s %7d %20d %s\n", modID, mName.trim(), credits, registrationCode, notes.trim());
                } else {
                    try {
                        htmlOut.println("<tr>");
                        htmlOut.println("<td>" + modID + "</td>");
                        htmlOut.println("<td>" + mName.trim() + "</td>");
                        htmlOut.println("<td>" + credits + "</td>");
                        htmlOut.println("<td>" + registrationCode + "</td>");
                        htmlOut.println("<td>" + notes + "</td>");
                        htmlOut.println("</tr>");
                    } catch (IOException ex) {
                        System.out.println("IO exception while writing student details to servlet.");
                    } catch (Exception ex) {
                        System.out.println("Other exception while writing student details to servlet.");
                    }
                }
            }
            yrCount++;
            if (!toConsole) {
                try {
                    htmlOut.println("</table>");
                } catch (IOException ex) {
                    System.out.println("IO exception while writing student details to servlet.");
                } catch (Exception ex) {
                    System.out.println("Other exception while writing student details to servlet.");
                }
            }

        }
        if (!toConsole) {
            try {
                htmlOut.println("</table>");
                htmlOut.println("</body>");
                htmlOut.println("</html>");
                htmlOut.close();
            } catch (IOException ex) {
                System.out.println("IO exception while writing student details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing student details to servlet.");
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Module Reports">
    /**
     * returns a map of all students on a module with all their details.
     * @param modID
     * @param year
     * @return
     * @throws SQLException
     */
    public Map reportModule(int modID, int year) throws SQLException {
        Map moduleMap = new HashMap<String, String>();
        ResultSet studentIDs = queries.getUniIDsOnModule(modID, year);
        ResultSet modName = queries.getModByModID(modID);
        modName.first();
        String mName = modName.getString("modulename").trim();
        moduleMap.put("moduleName", mName);
        moduleMap.put("year", year);
        // THE HEADERS ***MUST*** BE THE SAME AS THE KEYS LATER ON
        String[] tableHeaders = {"University ID", "First Name", "Second Name", "Registration Code", "Notes"};
        moduleMap.put("tableHeaders", tableHeaders);

        ResultSet studentCount = queries.getNumberOfStudentsOnModule(modID, year);
        if (studentCount.first()) {
            int numStudents = studentCount.getInt("studcount");
            moduleMap.put("studentCount", numStudents);
        }

        //Goes through all the student ids registered on the module
        Map students = new HashMap<String, String>();
        int studNo = 1; // to name the key
        while (studentIDs.next()) {
            Map student = new HashMap<String, String>();
            int curStudID = studentIDs.getInt("universityid");
            // get student details
            ResultSet studentDetails = queries.getStudentDetailsAndModuleRegistrationDetails(modID, curStudID, year);
            // Prepare data in sets for extraction.
            if (studentDetails.first()) {
                int uniID = studentDetails.getInt("universityid");
                String fname = studentDetails.getString("firstname").trim();
                String sname = studentDetails.getString("lastname").trim();
                int rCode = studentDetails.getInt("registrationcode");
                String notes = studentDetails.getString("notes").trim();
                // THESE KEYS ***MUST*** BE THE SAME AS THE STRINGS IN THE TABLEHEADER
                student.put("University ID", uniID);
                student.put("First Name", fname);
                student.put("Second Name", sname);
                student.put("Registration Code", rCode);
                student.put("Notes", notes);
                students.put("student" + studNo, student);
            }
            studNo++;
        }
        moduleMap.put("students", students);
        return moduleMap;
    }

    public void reportModule(int modID, int year, ServletOutputStream htmlOut) throws SQLException {
        //!!!!!!!!TAKE CARE, htmlOut CAN (AND WILL) BE NULL!!!!!!!!
        boolean toConsole;
        if (htmlOut == null) {
            toConsole = true; // going to be outputting to the console
        } else {
            toConsole = false;// outputting in html.
        }
        // Gets required data about the modules.
        ResultSet studentIDs = queries.getUniIDsOnModule(modID, year);
        ResultSet modName = queries.getModByModID(modID);
        modName.first();

        String mName = modName.getString("modulename");
        if (toConsole) { // output the module details to console.
            System.out.println("Module ID: " + modID);
            System.out.println("Module Name: " + mName);
            System.out.println("Module Year: " + year);
        } else {// output html head and module info to the page
            try {
                htmlOut.println("<html>");
                htmlOut.println("<body>");
                htmlOut.println("Module ID: " + modID + "<BR>");
                htmlOut.println("Module Name: " + mName + "<BR>");
                htmlOut.println("Module Year: " + year + "<BR>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing module details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing module details to servlet.");
            }
        }

        // Get the number of students on the module, and add this data to the output area.
        ResultSet studentCount = queries.getNumberOfStudentsOnModule(modID, year);
        if (studentCount.first()) {
            int numStudents = studentCount.getInt("studcount");
            if (toConsole) {
                System.out.println("Number of students on module: " + numStudents);
            } else {
                try {
                    htmlOut.println("Number of students on module: " + numStudents + "<BR>");
                } catch (IOException ex) {
                    System.out.println("IO exception while writing module details to servlet.");
                } catch (Exception ex) {
                    System.out.println("Other exception while writing module details to servlet.");
                }
            }
        }

        // output the tables headers to the output area.
        if (toConsole) {
            System.out.printf("%13s %15s %20s %2s %s \n", "University ID", "First name", "Second name", "Registration code", "Notes");
        } else {
            try {
                htmlOut.println("<table border=\"1\">");
                htmlOut.println("<tr>");
                htmlOut.println("<td>University ID</td>");
                htmlOut.println("<td>First Name</td>");
                htmlOut.println("<td>Last Name</td>");
                htmlOut.println("<td>Registration Code</td>");
                htmlOut.println("<td>Notes</td>");
                htmlOut.println("</tr>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing module details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing module details to servlet.");
            }
        }
        //Goes through all the student ids registered on the module
        while (studentIDs.next()) {
            int curStudID = studentIDs.getInt("universityid");
            // get student details
            ResultSet studentDetails = queries.getStudentDetailsAndModuleRegistrationDetails(modID, curStudID, year);
            // Prepare data in sets for extraction.
            if (studentDetails.first()) {
                int uniID = studentDetails.getInt("universityid");
                String fname = studentDetails.getString("firstname");
                String sname = studentDetails.getString("lastname");
                int rCode = studentDetails.getInt("registrationcode");
                String notes = studentDetails.getString("notes");
                // output all student data to the tables in output area.
                if (toConsole) {
                    System.out.printf("%d %15s %20s %2d %s \n", uniID, fname.trim(), sname.trim(), rCode, notes.trim());
                } else {
                    try {
                        htmlOut.println("<tr>");
                        htmlOut.println("<td>" + uniID + "</td>");
                        htmlOut.println("<td>" + fname.trim() + "</td>");
                        htmlOut.println("<td>" + sname.trim() + "</td>");
                        htmlOut.println("<td>" + rCode + "</td>");
                        htmlOut.println("<td>" + notes.trim() + "</td>");
                        htmlOut.println("</tr>");
                    } catch (IOException ex) {
                        System.out.println("IO exception while writing module details to servlet.");
                    } catch (Exception ex) {
                        System.out.println("Other exception while writing module details to servlet.");
                    }
                }
            }
        }

        // close the html.
        if (!toConsole) {
            try {
                htmlOut.println("</table>");
                htmlOut.println("</body>");
                htmlOut.println("</html>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing module details to servlet.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing module details to servlet.");
            }

        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Credit reports">
    /**
     * Returns a report of students who are not studying 120 credits in a specific
     * academic year.
     * @param academicYear
     * @return
     */
    public Map reportCredits(int academicYear) throws SQLException {
        // Map to store data in
        Map creditsMap = new HashMap<String, String>();
        /*
         * Table headers for use when making tables from these results. Note that
         * these headers should have the same names as data that is in the student
         * map.
         */
        // THE HEADERS ***MUST*** BE THE SAME AS THE KEYS LATER ON
        String[] tableHeaders = {"University ID", "Total Credits"};
        creditsMap.put("tableHeaders", tableHeaders);
        // Get the details for all student's credits for this year.
        ResultSet creditsThisYear = queries.getCreditsThisAcademicYear(academicYear);
        int studentNo = 1; // used for creating different keys.

        Map students = new HashMap<String, String>();
        // Go through the results of the query (if any).
        while (creditsThisYear.next()) {
            Map student = new HashMap<String, String>();
            int totalCredits = creditsThisYear.getInt("totalcredits");
            // check if the student is not on 120 credits - only want to do stuff
            // if this is indeed the case.
            if (totalCredits != 120) {
                int studentID = creditsThisYear.getInt("universityid");
                /*
                 * Add student data to the student map, and then put it into the
                 * main map.
                 */
                // THESE KEYS ***MUST*** BE THE SAME AS THE STRINGS IN THE TABLEHEADER
                student.put("Total Credits", totalCredits);
                student.put("University ID", studentID);
                students.put("student" + studentNo, student);
            }
        }
        // Puts the students from the query into the credit map.
        creditsMap.put("students", students);
        return creditsMap;
    }

    /**
     * Creates a report on all students who are not registered for 120 credits
     * in their current year of study.
     */
    public void reportCredits(int academicYear, ServletOutputStream htmlOut) throws SQLException {
        boolean toConsole;
        if (htmlOut == null) {
            toConsole = true; // going to be outputting to the console
        } else {
            toConsole = false;// outputting in html.
        }

        if (!toConsole) { // setting up html top bit and table headers.
            try {
                htmlOut.println("<html>");
                htmlOut.println("<head>");
                htmlOut.println("</head>");
                htmlOut.println("<body>");
                htmlOut.println("<table border=\"1\">");
                htmlOut.println("<tr>");
                htmlOut.println("<td>University ID</td>");
                htmlOut.println("<td>Credits</td>");
                htmlOut.println("</tr>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing credit query.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing credit query.");
            }
        }
        ResultSet creditsThisYear = queries.getCreditsThisAcademicYear(academicYear);
        System.out.println(creditsThisYear);
        while (creditsThisYear.next()) {
            int totalCredits = creditsThisYear.getInt("totalcredits");
            // check if the student is not on 120 credits - only want to do stuff
            // if this is indeed the case.
            if (totalCredits != 120) {
                int studentID = creditsThisYear.getInt("universityid");
                if (toConsole) { // printing to console
                    System.out.println(creditsThisYear.getInt("totalcredits"));
                } else { // outputting html
                    try {
                        htmlOut.println("<tr>");
                        htmlOut.println("<td>" + studentID + "</td>");
                        htmlOut.println("<td>" + totalCredits + "</td>");
                        htmlOut.println("</tr>");
                    } catch (IOException ex) {
                        System.out.println("IO exception while writing credit query.");
                    } catch (Exception ex) {
                        System.out.println("Other exception while writing credit query.");
                    }
                }
            }
        }

        if (!toConsole) {//close html
            try {
                htmlOut.println("</table>");
                htmlOut.println("</body>");
                htmlOut.println("</html>");
            } catch (IOException ex) {
                System.out.println("IO exception while writing credit query.");
            } catch (Exception ex) {
                System.out.println("Other exception while writing credit query.");
            }
        }

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Utility methods">
    /**
     * Prints the results from a resultset. And then returns the pointer to the
     * start of the set so that it can be re-read.
     * @param r
     */
    public void printResults(ResultSet r, boolean concise) {
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
                    System.out.print(string.trim() + "\t");
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
    //</editor-fold>
}
