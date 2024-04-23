package Service;

import Entity.Categorie;
import Entity.Cours;
import Utili.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements CRUDInterface<Cours> {
    private Connection connection ;
    public CoursService(){
        connection = MyDB.getInstance().getCnx();
    }
    @Override
    public void insert(Cours cours) throws SQLException {
        PreparedStatement ste = null;
        String sql = "INSERT INTO cours (duree,nb_chapitre,titre,description,status,categorie_id) VALUES(?,?,?,?,?,?)";
        ste = connection.prepareStatement(sql);
        ste.setInt(1,cours.getDuree());
        ste.setInt(2,cours.getNb_chapitre());
        ste.setString(3,cours.getTitre());
        ste.setString(4,cours.getDescription());
        ste.setString(5,cours.getStatus());
        ste.setInt(6,cours.getCategorie_id());
        ste.executeUpdate();
    }

    @Override
    public void update(int id, Cours cours) {
        String sql = "UPDATE cours SET duree = ?, nb_chapitre = ?, titre = ?, description = ?, status = ?, categorie_id = ? WHERE id = ?";
        try {
            PreparedStatement ste = connection.prepareStatement(sql);
            ste.setInt(1, cours.getDuree());
            ste.setInt(2, cours.getNb_chapitre());
            ste.setString(3, cours.getTitre());
            ste.setString(4, cours.getDescription());
            ste.setString(5, cours.getStatus());
            ste.setInt(6, cours.getCategorie_id());
            ste.setInt(7, id);
            ste.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement ste = null ;
        String sql ="DELETE FROM cours WHERE id = ?";
        ste= connection.prepareStatement(sql);
        ste.setInt(1,id);
        ste.executeUpdate();
    }

    @Override
    public ObservableList<Cours> getAll() throws SQLException {
        ObservableList<Cours> cours = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cours";
        try {
            Statement ste = connection.createStatement();
            ResultSet resultSet = ste.executeQuery(sql);
            while (resultSet.next()){
                Cours c = new Cours();
                c.setId(resultSet.getInt("id"));
                c.setDuree(resultSet.getInt("duree"));
                c.setNb_chapitre(resultSet.getInt("nb_chapitre"));
                c.setTitre(resultSet.getString("titre"));
                c.setDescription(resultSet.getString("Description"));
                c.setStatus(resultSet.getString("Status"));
                c.setCategorie_id(resultSet.getInt("categorie_id"));
                cours.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cours;
    }

}
