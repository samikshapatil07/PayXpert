package util;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnUtil {
	
	private static final String fileName="db.properties";
	public static Connection getConnection() {
		Connection con=null;
		String connString=null;
		try {
		connString=DBPropertyUtil.getConnection(fileName);
		}catch (IOException e) {
			System.out.println("Connection String Creation Failed");
			e.printStackTrace();
		}
		if(connString!=null) {
			try {
				con=DriverManager.getConnection(connString);
			}catch (SQLException e) {
				System.out.println("Error While Establishing DBConnection........");
				e.printStackTrace();
			}
		}
		return con;
	}
}
