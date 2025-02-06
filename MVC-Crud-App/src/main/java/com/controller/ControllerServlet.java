package com.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dto.Student;
import com.services.IStudentService;
import com.servicesFactory.StudentServiceFactory;

@WebServlet("/controller/*")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IStudentService studentService = StudentServiceFactory.getStudentService();
		RequestDispatcher dispature = null;
		
		// Create
		if (request.getRequestURI().endsWith("addform")) {
			String age = request.getParameter("sage");
			String name = request.getParameter("sname");
			String address = request.getParameter("saddr");

			Student std = new Student();
			std.setSname(name);
			std.setSage(Integer.parseInt(age));
			std.setSaddress(address);

			String status = studentService.addStudent(std);
			
			if (status.equals("success")) {
				request.setAttribute("status", "success");
				dispature = request.getRequestDispatcher("../insertResult.jsp");
				dispature.forward(request, response);
			} else {
				request.setAttribute("status", "failure");
				dispature = request.getRequestDispatcher("../insertResult.jsp");
				dispature.forward(request, response);
			}
		}

		// Read
		if (request.getRequestURI().endsWith("searchform")) {
			String sid = request.getParameter("sid");

			Student student = studentService.searchStudent(Integer.parseInt(sid));
			request.setAttribute("student", student); // keeping student obj in request scope
			dispature = request.getRequestDispatcher("../display.jsp");
			dispature.forward(request, response);
		}

		// Delete
		if (request.getRequestURI().endsWith("deleteform")) {
			String sid = request.getParameter("sid");

			String status = studentService.deleteStudent(Integer.parseInt(sid));

			if (status.equals("success")) {
				request.setAttribute("status", "success");
				dispature = request.getRequestDispatcher("../deleteResult.jsp");
				dispature.forward(request, response);
			} else if (status.equals("failure")) {
				request.setAttribute("status", "failure");
				dispature = request.getRequestDispatcher("../deleteResult.jsp");
				dispature.forward(request, response);
			} else {
				request.setAttribute("status", "notFound");
				dispature = request.getRequestDispatcher("../deleteResult.jsp");
				dispature.forward(request, response);
			}
		}

		// Update
		if (request.getRequestURI().endsWith("editform")) {
			String sid = request.getParameter("sid");
			Student student = studentService.searchStudent(Integer.parseInt(sid));
			
			if (student != null) {
				request.setAttribute("student", student);
				dispature  = request.getRequestDispatcher("../updateForm.jsp");
				dispature.forward(request, response);
			}
		}
		
		if (request.getRequestURI().endsWith("updateRecord")) {
			String id = request.getParameter("sid");
			String name = request.getParameter("sname");
			String age = request.getParameter("sage");
			String addr = request.getParameter("saddress");
			
			Student student = new Student();
			student.setSid(Integer.parseInt(id));
			student.setSname(name);
			student.setSage(Integer.parseInt(age));
			student.setSaddress(addr);
			
			String status = studentService.updateStudent(student);
			if (status.equals("success")) {
				request.setAttribute("status", "success");
				dispature = request.getRequestDispatcher("../../updateResult.jsp");
				dispature.forward(request, response);
			} else {
				request.setAttribute("status", "failed");
				dispature = request.getRequestDispatcher("../../updateResult.jsp");
				dispature.forward(request, response);
			}
		}
	}
}