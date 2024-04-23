package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private static Connection conn;
    private static final String url = "jdbc:mysql://localhost:3306/db_magic";
    private static final String user = "root";
    private static final String password = "";

    private MyDB() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database");
        } catch (SQLException e) {
            System.out.println("Connection to database failed");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new MyDB();
        }
        return conn;
    }
}
