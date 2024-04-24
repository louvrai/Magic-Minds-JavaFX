package Service;

import Entity.User;
import Utili.MyDB;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements ServiceUserInterface<User> {
    Connection connection;

    public UserService() {
        connection = MyDB.getInstance().getConnection();
    }


    @Override
    public void insert(User user) throws SQLException {
        PreparedStatement ste = null;
        String sql = "INSERT INTO user "
                + "( `first_name`, `last_name`, `age`, `gender`, `password`, `tel`, `email`, `picture`,`roles`, `is_verified`, `active`) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        ste = connection.prepareStatement(sql);
        ste.setString(1,user.getFirstName());
        ste.setString(2,user.getLastName());
        ste.setInt(3,user.getAge());
        ste.setString(4,user.getGender());
        ste.setString(5,user.getPassword());
        ste.setString(6,user.getTel());
        ste.setString(7,user.getEmail());
        ste.setString(8,user.getPicture());

        ste.setString(9,user.getRoles());
        ste.setBoolean(10,user.isVerified());
        ste.setBoolean(11,user.isActive());

        ste.executeUpdate();
    }

    @Override
    public void update(User user,int id) throws SQLException {
        PreparedStatement ste = null;

        String sql = "UPDATE `user` SET first_name = ?, last_name = ?,age = ?,gender = ?,password= ?,tel= ?,email= ?,picture= ?,roles= ?,is_verified= ?,active= ? WHERE id = ?";
        ste = connection.prepareStatement(sql);
        ste.setString(1,user.getFirstName());
        ste.setString(2,user.getLastName());
        ste.setInt(3,user.getAge());
        ste.setString(4,user.getGender());
        ste.setString(5,user.getPassword());
        ste.setString(6,user.getTel());
        ste.setString(7,user.getEmail());
        ste.setString(8,user.getPicture());

        ste.setString(9,user.getRoles());
        ste.setBoolean(10,user.isVerified());
        ste.setBoolean(11,user.isActive());
        ste.setInt(12,id);
        ste.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement ste = null ;
        String sql ="DELETE FROM `user` WHERE id = ?";
        ste= connection.prepareStatement(sql);
        ste.setInt(1,id);
        ste.executeUpdate();
    }

    @Override
    public List<User> getAll() throws SQLException {
        Statement ste = null;
        ResultSet resultSet = null;
        List<User>users = new ArrayList<>();
        String sql = "SELECT * FROM `user`";
        ste = connection.createStatement();
        resultSet = ste.executeQuery(sql);
        while(resultSet.next()){
            User p = new User();
            p.setId(resultSet.getInt("id"));
            p.setFirstName(resultSet.getString("first_name"));
            p.setLastName(resultSet.getString("last_name"));
            p.setAge(resultSet.getInt("age"));
            p.setGender(resultSet.getString("gender"));
            p.setPassword(resultSet.getString("password"));
            p.setTel(resultSet.getString("tel"));
            p.setEmail(resultSet.getString("email"));
            p.setPicture(resultSet.getString("picture"));
            p.setRoles(resultSet.getString("roles"));
            p.setVerified(resultSet.getBoolean("is_verified"));
            p.setActive(resultSet.getBoolean("active"));

            users.add(p);
        }
        return users;
    }
}
