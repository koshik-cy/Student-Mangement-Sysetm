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

@WebServlet("/Delete")
public class Delete extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String Id = req.getParameter("id");

        try (PrintWriter out = resp.getWriter()) {
            String url = "jdbc:mysql://localhost:3306/studentdetails";
            String name = "root";
            String password = "root";
            String query = "DELETE FROM student WHERE StudentId=?";

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(url, name, password);
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, Id);

                int i = pstmt.executeUpdate();

                if (i > 0) {
                    
                	resp.sendRedirect("DeleteServlet");
                	
                } else {
                    out.println(Id + " Data Not Deleted");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
