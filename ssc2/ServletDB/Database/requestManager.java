/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Map;
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
public class requestManager extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SessionAuth auth = new SessionAuth();
        boolean studentReport = exists(request.getParameter("studentReport"));
        boolean moduleReport = exists(request.getParameter("moduleReport"));
        boolean addModule = exists(request.getParameter("addModule"));
        boolean registerStudent = exists(request.getParameter("registerStudent"));
        boolean unregisterStudent = exists(request.getParameter("unregisterStudent"));
        boolean creditReport = exists(request.getParameter("creditReport"));
        try {
            if (studentReport) {
                auth.authenticateSession(request, response, 1);
                doStudentReport(request, response);
            } else if (moduleReport) {
                auth.authenticateSession(request, response, 2);
                doModuleReport(request, response);
            } else if (addModule) {
                auth.authenticateSession(request, response, 3);
                doAddModule(request, response);
            } else if (registerStudent) {
                auth.authenticateSession(request, response, 3);
                doRegisterStudent(request, response);
            } else if (unregisterStudent) {
                auth.authenticateSession(request, response, 3);
                doUnregisterStudent(request, response);
            } else if (creditReport) {
                auth.authenticateSession(request, response, 3);
                doCreditReport(request, response);
            }
        } catch (SQLException ex) {
            System.out.println("Exception while attempting to compile a report.");
            ex.printStackTrace();
            response.sendRedirect("reportError.jsp");
        } catch (IllegalStateException ex) {
            System.out.println("State exception while compiling report.");
            ex.printStackTrace();
            response.sendRedirect("reportError.jsp");
        } catch (Exception ex) {
            System.out.println("Other exception while compiling a report.");
            ex.printStackTrace();
            response.sendRedirect("reportError.jsp");
        }
    }

    /**
     * Method to check if a string exists or not. This is because the getparameter
     * method returns null if given a bad string, and we want a quick way of
     * getting a boolean out.
     * @param s
     * @return
     */
    public boolean exists(String s) {
        if (s == null) {
            return false;
        }
        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="Methods to execute queries or reports. For more specifics, please see the Queries and Reports classes.">
    /*
     * Executes a student report on values from the html form.
     */
    private void doStudentReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        DBBase database = new DBBase();
        HttpSession session = request.getSession();
        Enumeration a = request.getAttributeNames();
        int studentID = Integer.parseInt(request.getParameter("studentID"));
        Map studMap = database.reportStudent(studentID);
        session.setAttribute("reportMap", studMap);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/studentReport.jsp");
        dispatcher.forward(request, response);
        return;
    }

    /*
     * Executes a module report on values from the html form.
     */
    private void doModuleReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        DBBase database = new DBBase();
        HttpSession session = request.getSession();
        int moduleID = Integer.parseInt(request.getParameter("moduleID"));
        int year = Integer.parseInt(request.getParameter("academicYear"));
        Map modMap = database.reportModule(moduleID, year);
        session.setAttribute("reportMap", modMap);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/moduleReport.jsp");
        dispatcher.forward(request, response);
        return;
    }

    /*
     * Adds a module to the database with details from the html form.
     */
    private void doAddModule(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        DBBase database = new DBBase();
//        try {
        String modName = request.getParameter("modName");
        int modLevel = Integer.parseInt(request.getParameter("modLevel"));
        int credits = Integer.parseInt(request.getParameter("credits"));
        database.addModule(modName, modLevel, credits);
        response.sendRedirect("addSuccess.jsp");
    }

    /*
     * Registers a student on a module with data from the html form.
     */
    private void doRegisterStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        DBBase database = new DBBase();
//        try {
        int mID = Integer.parseInt(request.getParameter("modIDReg"));
        int sID = Integer.parseInt(request.getParameter("sIDReg"));
        int acYear = Integer.parseInt(request.getParameter("acYearReg"));
        String notes = request.getParameter("regNotes");
        database.addRegistrationToModule(mID, sID, acYear, notes);
        response.sendRedirect("addSuccess.jsp");
    }

    /*
     * unregisters a student from a module given in the html form., ServletOutputStream out
     */
    private void doUnregisterStudent(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        DBBase database = new DBBase();
//        try {
        int mID = Integer.parseInt(request.getParameter("modIDUnReg"));
        int sID = Integer.parseInt(request.getParameter("sIDUnReg"));
        int acYear = Integer.parseInt(request.getParameter("acYearUnReg"));
        database.removeRegistrationFromModule(acYear, mID, sID);
        response.sendRedirect("addSuccess.jsp");
    }

    /*
     * Creates a report on all students who are not taking 120 credits in the
     * year from the html form.
     */
    private void doCreditReport(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        DBBase database = new DBBase();
        HttpSession session = request.getSession();
        int year = Integer.parseInt(request.getParameter("acYearCredit"));
//        try {
        Map credMap = database.reportCredits(year);
        session.setAttribute("reportMap", credMap);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/creditReport.jsp");
        dispatcher.forward(request, response);
        return;
    }
    //</editor-fold>

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
