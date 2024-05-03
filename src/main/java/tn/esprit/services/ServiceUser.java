package tn.esprit.services;

import tn.esprit.models.User;
import tn.esprit.utils.MyDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceUser {

    private Connection cnx;

    public ServiceUser() {
        cnx = MyDB.getInstance().getCnx();
    }

    public void addUser(User user) {
        String query = "INSERT INTO user (age, firstName, lastName, email, tel, gender, password, picture, roles) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, user.getAge());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getTel());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getPassword());
            preparedStatement.setString(8, user.getPicture());
            preparedStatement.setString(9, user.getRoles());
            preparedStatement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setAge(resultSet.getInt("age"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setTel(resultSet.getString("tel"));
                user.setGender(resultSet.getString("gender"));
                user.setPassword(resultSet.getString("password"));
                user.setPicture(resultSet.getString("picture"));
                user.setRoles(resultSet.getString("roles"));
                // Add other fields as needed
            }
        } catch (SQLException e) {
            System.out.println("Error getting user by ID: " + e.getMessage());
        }
        return user;
    }

    // Add other methods as needed

}
