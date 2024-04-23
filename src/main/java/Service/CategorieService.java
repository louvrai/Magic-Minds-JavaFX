package Service;

import Entity.Categorie;
import Utili.MyDB;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

import java.sql.*;
import java.sql.SQLException;


public class CategorieService implements CRUDInterface <Categorie> {
    private Connection connection ;

    public CategorieService(){
        connection = MyDB.getInstance().getCnx();
    }

    @Override
    public void insert(Categorie categorie)  {
        String sql = "INSERT INTO categorie "
                + "(titre,description,image) "
                + "VALUES(?,?,?)";
        try {
            PreparedStatement ste =connection.prepareStatement(sql);
            ste.setString(1,categorie.getTitre());
            ste.setString(2,categorie.getDescription());
            ste.setString(3,categorie.getImage());
            ste.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(int id, Categorie categorie) {

        String sql = "UPDATE categorie SET titre= ?, description = ?,image = ? WHERE id = ?";
        try {
            PreparedStatement ste =connection.prepareStatement(sql);
            ste.setString(1,categorie.getTitre());
            ste.setString(2,categorie.getDescription());
            ste.setString(3,categorie.getImage());
            ste.setInt(4,id);
            ste.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql ="DELETE FROM categorie WHERE id = ?";
        try {
            PreparedStatement ste =connection.prepareStatement(sql);
            ste= connection.prepareStatement(sql);
            ste.setInt(1,id);
            ste.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Categorie> getAll() throws SQLException {
        ObservableList<Categorie> categories = FXCollections.observableArrayList();
        String sql = "SELECT * FROM categorie";
        try {
            Statement ste = connection.createStatement();

            ResultSet resultSet = ste.executeQuery(sql);

            while (resultSet.next()){
                Categorie c = new Categorie();
                c.setId(resultSet.getInt("id"));
                c.setTitre(resultSet.getString("titre"));
                c.setDescription(resultSet.getString("description"));
                c.setImage(resultSet.getString("image"));
                categories.add(c);
            } } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return categories;
    }
}
