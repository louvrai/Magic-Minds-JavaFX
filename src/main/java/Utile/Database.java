package Utile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Database {

    final String url = "jdbc:mysql://localhost:3306/magicminds";
    final String username = "root";
    final String password = "";
    private Connection connection;
    static Database instance;

    private Database() {
        try {
            connection =  DriverManager.getConnection(url, username, password);
            System.out.println("connected");
        }
        catch (SQLException e){
            System.err.println(e.getMessage());
        }

    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }





    public Connection getConnection() {
        return connection;
    }
}

