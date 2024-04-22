package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Evenement;
import tn.esprit.utils.MyDB;

import java.sql.*;
import java.util.ArrayList;

public class ServiceEvenement implements IService<Evenement> {

    private Connection cnx;

    public ServiceEvenement() {
        cnx = MyDB.getInstance().getCnx();
    }


    @Override
    public void add(Evenement event) {
        if (event == null || event.getDate_debut() == null) {
            System.out.println("Event or its start date is null.");
            return; // or throw an exception if necessary
        }

        // Vérifier que la date de début n'est pas null
        if (event.getDate_debut() == null) {
            System.out.println("Start date of the event is null.");
            return;
        }

        String qry = "INSERT INTO `evenement`(`nb_participant`, `nom`, `description`, `localisation`, `categorie`, `date_debut`, `date_fin`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);

            pstm.setInt(1, event.getNb_participant());
            pstm.setString(2, event.getNom());
            pstm.setString(3, event.getDescription());
            pstm.setString(4, event.getLocalisation());
            pstm.setString(5, event.getCategorie());
            pstm.setDate(6, new java.sql.Date(event.getDate_debut().getTime()));
            pstm.setDate(7, new java.sql.Date(event.getDate_fin().getTime()));

            pstm.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public ArrayList<Evenement> getAll() {
        ArrayList<Evenement> evenements = new ArrayList<>();
        String qry = "SELECT * FROM `evenement`";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(qry);

            while (rs.next()) {
                Evenement event = new Evenement();
                event.setId(rs.getInt("id"));
                event.setNb_participant(rs.getInt("nb_participant"));
                event.setNom(rs.getString("nom"));
                event.setDescription(rs.getString("description"));
                event.setLocalisation(rs.getString("localisation"));
                event.setCategorie(rs.getString("categorie"));
                event.setDate_debut(rs.getDate("date_debut"));
                event.setDate_fin(rs.getDate("date_fin"));

                evenements.add(event);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }

    @Override
    public void update(Evenement event) {
        String qry = "UPDATE `evenement` SET `nb_participant`=?, `nom`=?, `description`=?, `localisation`=?, `categorie`=?, `date_debut`=?, `date_fin`=? WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);

            pstm.setInt(1, event.getNb_participant());
            pstm.setString(2, event.getNom());
            pstm.setString(3, event.getDescription());
            pstm.setString(4, event.getLocalisation());
            pstm.setString(5, event.getCategorie());
            pstm.setDate(6, new java.sql.Date(event.getDate_debut().getTime()));
            pstm.setDate(7, new java.sql.Date(event.getDate_fin().getTime()));
            pstm.setInt(8, event.getId());

            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean delete(Evenement event) {
        String qry = "DELETE FROM `evenement` WHERE `id`=?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(qry);
            pstm.setInt(1, event.getId());
            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            // En cas d'erreur, renvoyer false pour indiquer que la suppression a échoué
            return false;
        }
    }

}
