package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class JdbcUtil {
	// so that no object should be created
	private JdbcUtil() {
	};

	// loading and register the class
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	// Establish the connection
	public static Connection getJdbcConnection() throws SQLException, IOException {
		return logicalConnection();
		 //return physicalConnection();
	}

	public static Connection logicalConnection() throws SQLException {
		String fileLoc = "D:\\Pratik\\git\\MVC-Crud-repo\\MVC-Crud-App\\src\\main\\java\\com\\properties\\application.properties";
		HikariConfig config = new HikariConfig(fileLoc);
		HikariDataSource dataSource = new HikariDataSource(config);
		return dataSource.getConnection();
	}

	public static Connection physicalConnection() throws SQLException, IOException {
		FileInputStream fis = new FileInputStream(
				"D:\\Pratik\\git\\MVC-Crud-repo\\MVC-Crud-App\\src\\main\\java\\com\\properties\\application.properties");
		Properties properties = new Properties();
		properties.load(fis);

		Connection connection = DriverManager.getConnection(properties.getProperty("jdbcUrl"),
				properties.getProperty("user"), properties.getProperty("password"));
		return connection;
	}

	public static void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}