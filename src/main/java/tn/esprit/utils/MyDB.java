package tn.esprit.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private static MyDB instance;
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/magicminds";
    private String user = "root";
    private String pass = "";

    private MyDB() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("Connection problem: " + e.getMessage());
            // You might want to handle this exception more gracefully,
            // such as logging it or throwing a custom exception.
        }
    }

    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    public Connection getCnx() {
        return connection;
    }
}
