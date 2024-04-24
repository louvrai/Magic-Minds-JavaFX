package test;

import Entity.User;
import Service.UserService;
import Utili.MyDB;


import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserService();
        String role = "[\"ROLE_USER\"]";;

        User user = new User(22,"louay","benslimen","email","88999","male","lijlnjn","jjkhjjhj",role);
        service.insert(user);
    }
}
