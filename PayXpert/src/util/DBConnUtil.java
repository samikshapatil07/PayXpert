package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exception.DatabaseConnectionException;

public class DBConnUtil {
    
    //  connection details
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "payxpert";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Spatil@07"; 
    
    public static Connection getConnection() throws DatabaseConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME
                    + "?user=" + DB_USER + "&password=" + DB_PASSWORD;
            return DriverManager.getConnection(connectionString);
        } catch (ClassNotFoundException e) {
            throw new DatabaseConnectionException("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Failed to connect to database: " + e.getMessage());
        }
    }

    /* Closes a database connection*/
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}