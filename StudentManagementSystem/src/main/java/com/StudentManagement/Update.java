package com.StudentManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Update")
public class Update extends HttpServlet {

    static String oldName;
    static int oldsemester;
    static int oldFee;
    static int oldBacklogs;
    static int oldAttendance;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String Id = req.getParameter("id");
        String name = req.getParameter("name");

       
        int sem = parseInteger(req.getParameter("sem"), 0);
        int fees = parseInteger(req.getParameter("fee"), 0);
        int backlogs = parseInteger(req.getParameter("backlogs"), 0);
        int attendance = parseInteger(req.getParameter("attendance"), 0);

        PrintWriter out = resp.getWriter();

        try {
            String url = "jdbc:mysql://localhost:3306/studentdetails";
            String userName = "root";
            String password = "Pradeep@17";

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, userName, password);

           
            String query = "SELECT StudentName, Semester, FeeBalance, NoOfBacklogs, Attendance FROM student WHERE StudentId=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, Id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                oldName = rs.getString(1);
                oldsemester = rs.getInt(2);
                oldFee = rs.getInt(3);
                oldBacklogs = rs.getInt(4);
                oldAttendance = rs.getInt(5);
            } else {
                out.println("Student ID not found!");
                return;
            }
            rs.close();
            pstmt.close();

            String updateQuery = "UPDATE student SET StudentName=?, Semester=?, FeeBalance=?, NoOfBacklogs=?, Attendance=? WHERE StudentId=?";
            PreparedStatement updateStmt = con.prepareStatement(updateQuery);

            updateStmt.setString(1, (isEmpty(name)) ? oldName : name);
            updateStmt.setInt(2, (sem == 0) ? oldsemester : sem);
            updateStmt.setInt(3, (fees == 0) ? oldFee : fees);
            updateStmt.setInt(4, (backlogs == 0) ? oldBacklogs : backlogs);
            updateStmt.setInt(5, (attendance == 0) ? oldAttendance : attendance);
            updateStmt.setString(6, Id);

            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated > 0) {
               resp.sendRedirect("UpdateServlet");;
            } else {
                out.println("No record updated. Please check the Student ID.");
            }

            updateStmt.close();
            con.close();

        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error: " + e.getMessage());
        }
    }

    
    private int parseInteger(String str, int defaultValue) {
        if (str == null || str.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue; 
        }
    }

    
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
