package com.StudentManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentDetails")
public class StudentDetails extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String Id = (String) req.getAttribute("StudentId"); 

        try (PrintWriter out = resp.getWriter()) {
            String url = "jdbc:mysql://localhost:3306/studentdetails";
            String dbUser = "root";
            String dbPassword = "root";

           
            String query = "SELECT StudentId, StudentName, Semester, FeeBalance, NoOfBacklogs, Attendance FROM student WHERE StudentId = '" + Id + "'";

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
                 Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                out.printf("StudentID   | StudentName         | Semester  | Fee Balance  | No Of Backlogs  | Attendance\n");
                out.println("--------------------------------------------------------------------------------------------------");

                boolean found = false;

                while (rs.next()) {
                    found = true;
                    String ID = rs.getString(1);
                    String Name = rs.getString(2);
                    int Semester = rs.getInt(3);
                    int fees = rs.getInt(4);
                    int noOfBacklogs = rs.getInt(5);
                    int attendance = rs.getInt(6);

                    out.printf("| %-10s | %-18s | %-8d | %-12d | %-14d | %-11d |\n",
                            ID, Name, Semester, fees, noOfBacklogs, attendance);
                }

                if (!found) {
                    out.println("No student found with ID: " + Id);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
