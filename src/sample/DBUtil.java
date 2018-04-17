package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    static final String username = "root";
    static final String password = "mam2jaja";
    static final String dbUrl = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";//to get rid of SSL warning

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(dbUrl,username,password);
    }
}
