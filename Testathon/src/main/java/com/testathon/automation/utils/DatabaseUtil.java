package com.testathon.automation.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.testathon.automation.core.Config;

/**
 * This class performs database operation like:
 * 1) Connect to database.
 * 2) Execute query.
 * 3) Get the resultset.
 */
public class DatabaseUtil {

	private static Logger log = Logger.getLogger(DatabaseUtil.class);

	/**
	 * Instantiates a new database operations.
	 */
	public DatabaseUtil() {
		log.info(" : DatabaseOperations Constructor Called");
	}

	/**
	 * Connect with the database and executes the SQL query 
	 * and return the result in String form.
	 * 
	 * @param sqlQuery	sql query.
	 * @return String	result of sql query.
	 */
	public String executeSQLQuery(String sqlQuery) throws NullPointerException, SQLException{

		Connection connection = null;
		ResultSet rs = null;
		Statement stmt = null;
		String resultValue = "";

		log.info(" : ExecuteSQLQuery Method Called");
		try {
			connection = DriverManager.getConnection(Config.DBConnectionString);
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlQuery);

			while (rs.next()) {
				resultValue = rs.getString(1).toString();
			}

		}catch (SQLException sqlEx) {
			log.error("SQL Exception:" + sqlEx.getStackTrace());
		}catch (NullPointerException ex) {
			log.error(ex.getMessage());
		}catch(Exception e) {
			log.error(e.getStackTrace());
		}
		finally {
			try {
				if (connection!=null) {
					connection.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
			try {
				if (stmt!=null) {
					stmt.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
			try {
				if (rs!=null) {
					rs.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
		}
		return resultValue;
	}

	/**
	 * Connect with the database and executes the SQL query 
	 * and return the result in array list form.
	 * 
	 * @param sqlQuery		sql query.
	 * @return ArrayList	result of sql query.
	 */
	public static ArrayList<String> executeSQLQuery_List(String sqlQuery) throws SQLException, NullPointerException
	{
		Connection connection =null;
		Statement statement = null;
		ResultSet resultSet = null;

		ArrayList<String> resultValue = new ArrayList<String>();

		log.info(" : ExecuteSQLQuery Method Called");
		try {
			connection = DriverManager.getConnection(Config.DBConnectionString);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sqlQuery);

			while (resultSet.next()) {
				int columnCount = resultSet.getMetaData().getColumnCount();
				StringBuilder stringBuilder = new StringBuilder();
				for (int iCounter = 1; iCounter <= columnCount; iCounter++) {
					stringBuilder.append(resultSet.getString(iCounter).trim() + " ");
				}
				String reqValue = stringBuilder.substring(0, stringBuilder.length() - 1);
				resultValue.add(reqValue);
			}
		}catch (SQLException sqlEx) {
			log.error("SQL Exception:" + sqlEx.getStackTrace());
		}catch (NullPointerException ex) {
			log.error(ex.getMessage());
		}catch(Exception e) {
			log.error(e.getStackTrace());
		}
		finally {
			try {
				if (connection!=null) {
					connection.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
			try {
				if (statement!=null) {
					statement.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
			try {
				if (resultSet!=null) {
					resultSet.close();
					}
				}catch (SQLException se) {
					log.error("SQL Exception:" + se.getStackTrace());
				}
		}
		return resultValue;	
	}
}

