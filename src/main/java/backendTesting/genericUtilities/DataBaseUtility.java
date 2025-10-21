package backendTesting.genericUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class DataBaseUtility {
	Connection conn = null;
	/**
	 * This method is used to connect to the db
	 */
	public void getConnectionFromDataBase() {
		try {
			Driver d = new Driver();
			DriverManager.registerDriver(d);
			String url ="jdbc:mysql://localhost:3306/seleniumpractise";
			conn = DriverManager.getConnection(url, "root", "root");
			System.out.println("=Connection Done=");
		}
		catch (Exception e) {
			System.out.println("Error Occurred");
		}
	}
	/**
	 * This method is used to close the connection
	 * @throws SQLException
	 */
	public void closeConnectionFromDataBase() throws SQLException {
		conn.close();
		System.out.println("Connection Closed");
	}
	/**
	 * This method is used to Read data from database based on user name
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public ResultSet readDataFromDB(String user) throws SQLException {
		Statement stmt = conn.createStatement();
		ResultSet result = stmt.executeQuery("Select * from students where name ="+user);
		return result;
	}
	/**
	 * This method is used to Insert data into db with query as parameter
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public String InsertDataIntoDB(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		int result = stmt.executeUpdate(query);
		return "no. of rows affected : "+result;
	}
}
