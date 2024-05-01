package Services;

import Entities.Command;
import Utils.MyDB;

import java.sql.*;
import java.util.ArrayList;

public class CommandCRUD implements PService<Command> {
    private Connection connection;
    public CommandCRUD(){
        connection=MyDB.getConnection();
    }
    //INSERT INTO `commande`(`id`, `iduser_id`, `totalprice`) VALUES ('[value-1]','[value-2]','[value-3]')
    //INSERT INTO `commande_produit`(`commande_id`, `produit_id`) VALUES ('[value-1]','[value-2]')
    @Override
    public void ajouter(Command command) throws SQLDataException {
        String commandeQuery = "INSERT INTO commande (iduser_id, totalprice) VALUES (?, ?)";

        try {
            PreparedStatement commandePs = connection.prepareStatement(commandeQuery, Statement.RETURN_GENERATED_KEYS);
            commandePs.setInt(1, command.getId_user());
            commandePs.setDouble(2, command.getTotal());
            commandePs.executeUpdate();

            // Retrieve the generated id from the inserted row
            ResultSet generatedKeys = commandePs.getGeneratedKeys();
            if (generatedKeys.next()) {
                int commandeId = generatedKeys.getInt(1); // Get the auto-generated id

                // Now insert into the commande_produit table for each product ID
                String commandeProduitQuery = "INSERT INTO commande_produit (commande_id, produit_id) VALUES (?, ?)";
                try (PreparedStatement commandeProduitPs = connection.prepareStatement(commandeProduitQuery)) {
                    for (int produitId : command.getId_produit()) {
                        commandeProduitPs.setInt(1, commandeId);
                        commandeProduitPs.setInt(2, produitId);
                        commandeProduitPs.executeUpdate();
                    }
                }
            } else {
                throw new SQLException("Failed to retrieve auto-generated key for commande.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifier(Command command) throws SQLDataException {

    }

    @Override
    public void supprimer(Command command) throws SQLDataException {
        String req="DELETE FROM commande WHERE id=?";
        try {
            PreparedStatement ps=connection.prepareStatement(req);
            ps.setInt(1,command.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void afficher(Command command) throws SQLDataException {

    }

    @Override
    public ArrayList<Command> afficherAll() throws SQLException {
        return null;
    }
}
