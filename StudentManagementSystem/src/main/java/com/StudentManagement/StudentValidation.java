package com.StudentManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentValidation")
public class StudentValidation extends HttpServlet {   
		
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String sId=req.getParameter("id");
		
		PrintWriter out=resp.getWriter();
		
		try {
			
			String url="jdbc:mysql://localhost:3306/studentdetails";
			
			String name="root";
			
			String password="root";
			
			String query="Select `StudentId` from `student` ";
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection(url,name,password);
			
			Statement stmt=con.createStatement();	
			
			ResultSet rs=stmt.executeQuery(query);
				
			boolean found=false;
			while(rs.next()) {
					
				String Id=rs.getString(1);
				
				if(Id.equals(sId)) {
						
					found=true;
					break;
				}
			}
			if(found) {
				
				req.setAttribute("StudentId", sId);
				RequestDispatcher rd=req.getRequestDispatcher("/StudentDetails");
				
				rd.forward(req, resp);
				
			}
			else  {
				
				out.println("No student found with ID: " + sId);
			}
		}
		
		catch(ClassNotFoundException c) {
			
			c.printStackTrace();
		}
		
		catch(SQLException s) {
			
			s.printStackTrace();
		}
		
	}
}
