package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//class only made to get connection or show error when connecting to database
public class DBUtil {

    private static final String username = "root";
    private static final String password = "mam2jaja";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";//to get rid of SSL warning

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbUrl,username,password);
    }

    public static void showError(SQLException e ){
        System.err.println("Error: " + e.getMessage() +"\nError Code: " + e.getErrorCode());
    }


}
