package test;

import Entity.User;
import Service.UserService;
import Utili.MyDB;


import java.sql.SQLException;

public class test {
    public static void main(String[] args) throws SQLException {
        UserService service = new UserService();
        String role = "[\"ROLE_ADMIN\"]";;
      //  String role1 = role.replaceAll("[\"","").replaceAll("]","");
        String newRole = role.replaceAll("\\[\"ROLE_.*?\"\\]", "");
        String roleName = role.replaceAll("\\[\"ROLE_(.*?)\"\\]", "$1").toLowerCase();
        System.out.println(newRole);
        System.out.println(roleName);
//        User user = new User(22,"louay","benslimen","email","88999","male","lijlnjn","jjkhjjhj",role);
//        service.insert(user);

        //System.out.println(service.getAll());
    }
}
