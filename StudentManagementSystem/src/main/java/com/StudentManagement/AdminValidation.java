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

@WebServlet("/AdminValidation")
public class AdminValidation extends HttpServlet {
		
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		int Id = Integer.parseInt(req.getParameter("id"));
		
		String url = "jdbc:mysql://localhost:3306/admindetails";
		String dbUser = "root"; 
		String dbPassword = "root";
		
		try (PrintWriter out = resp.getWriter()) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			try (Connection con = DriverManager.getConnection(url, dbUser, dbPassword);
				 Statement stmt = con.createStatement()) {

				
				String query = "SELECT name FROM admin WHERE id = " + Id;
				
			
				try (ResultSet rs = stmt.executeQuery(query)) {
					if (rs.next()) {
						
						resp.sendRedirect("HomesHtml.html");
						
					} else {
						out.println("No admin found with ID: " + Id);
					}
				}
			}
		} 
		catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
