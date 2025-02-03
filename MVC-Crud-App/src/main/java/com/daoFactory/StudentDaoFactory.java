package com.daoFactory;

import com.persistence.IStudentDao;
import com.persistence.StudentDaoImpl;

public class StudentDaoFactory {

	// no object creation
	private StudentDaoFactory() {}

	private static IStudentDao studentDao = null;

	public static IStudentDao getStudentDao() {
		if (studentDao == null) {
			studentDao = new StudentDaoImpl();
		}
		return studentDao;
	}
}