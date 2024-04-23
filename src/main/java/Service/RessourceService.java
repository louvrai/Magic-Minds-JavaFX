package Service;

import Entity.Cours;
import Entity.Ressource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RessourceService implements CRUDInterface<Ressource> {
    Connection connection ;
    @Override
    public void insert(Ressource ressource) throws SQLException {
        PreparedStatement ste = null;
        String sql = "INSERT INTO ressource"
                + "(titre,type,url) "
                + "VALUES(?,?,?)";
        ste = connection.prepareStatement(sql);
        ste.setString(1,ressource.getTitre());
        ste.setString(2,ressource.getType());
        ste.setString(3,ressource.getUrl());
        ste.executeUpdate();
    }

    @Override
    public void update(int id, Ressource ressource) throws SQLException {
        PreparedStatement ste = null ;
        String sql = "UPDATE ressource SET titre= ?, type = ?,url = ? WHERE id = ?";
        ste = connection.prepareStatement(sql);
        ste.setString(1,ressource.getTitre());
        ste.setString(2,ressource.getType());
        ste.setString(3,ressource.getUrl());
        ste.setInt(4,id);
        ste.executeUpdate();
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement ste = null ;
        String sql ="DELETE FROM ressource WHERE id = ?";
        ste= connection.prepareStatement(sql);
        ste.setInt(1,id);
        ste.executeUpdate();
    }

    @Override
    public ObservableList<Ressource> getAll() throws SQLException {
        ObservableList<Ressource> chapters = FXCollections.observableArrayList();
        String sql = "SELECT * FROM ressource";
        try {
            Statement ste = connection.createStatement();
            ResultSet resultSet = ste.executeQuery(sql);
            while (resultSet.next()) {
                Ressource r = new Ressource();
                r.setId(resultSet.getInt("id"));
                r.setTitre(resultSet.getString("title"));
                r.setType(resultSet.getString("Type"));
                r.setUrl(resultSet.getString("File"));
                chapters.add(r);
            }
        }catch (SQLException e) {
                System.out.println(e.getMessage());}
        return chapters;
    }
}
