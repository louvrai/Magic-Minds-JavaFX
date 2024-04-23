package Utili;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    Connection connection;
    String url = "jdbc:mysql://localhost:3306/magicminds";
    String user = "root";
    String pass = "";
    static MyDB Instance;

    public MyDB() {
        try {
            this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
            System.out.println("Connection");
        } catch (SQLException var2) {
            System.out.println("Connection problem");
        }

    }

    public static MyDB getInstance() {
        if (Instance == null) {
            Instance = new MyDB();
        }

        return Instance;
    }

    public Connection getCnx() {
        return connection;
    }

}
