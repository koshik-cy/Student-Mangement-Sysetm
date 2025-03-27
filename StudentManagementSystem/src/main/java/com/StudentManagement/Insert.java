package com.StudentManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Insert")
public class Insert extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String Id = req.getParameter("id");
        String name = req.getParameter("name");
        int sem = Integer.parseInt(req.getParameter("sem"));
        int fees = Integer.parseInt(req.getParameter("fee"));
        int backlogs = Integer.parseInt(req.getParameter("backlogs"));
        int attendance = Integer.parseInt(req.getParameter("attendance"));

        try (PrintWriter out = resp.getWriter()) {
            String url = "jdbc:mysql://localhost:3306/studentdetails";
            String dbUser = "root";
            String dbPassword = "root";

            String query = "INSERT INTO student (`StudentId`, `StudentName`, `Semester`, `FeeBalance`, `NoOfBacklogs`, `Attendance`) VALUES (?, ?, ?, ?, ?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, Id);
                pstmt.setString(2, name);
                pstmt.setInt(3, sem);
                pstmt.setInt(4, fees);
                pstmt.setInt(5, backlogs);
                pstmt.setInt(6, attendance);

                int i = pstmt.executeUpdate();

                if (i > 0) {

                	resp.sendRedirect("InsertServlet");
                } else {
                    out.println("Failed to Add Data");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
