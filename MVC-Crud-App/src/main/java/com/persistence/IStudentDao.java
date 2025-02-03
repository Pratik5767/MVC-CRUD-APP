package com.persistence;

import com.dto.Student;

public interface IStudentDao {
	// Operations to be implemented
	public String insertStudent(Student student);
	
	public Student searchStudent(Integer sid);
	
	public String updateStudent(Student student);
	
	public String deleteStudent(Integer sid);
}