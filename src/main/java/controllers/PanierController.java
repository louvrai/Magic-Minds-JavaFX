package controllers;

import Entities.Command;
import Entities.Produit;
import Services.CartFileManager;
import Services.CommandCRUD;
import Services.ProduitCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import javafx.event.ActionEvent;

public class PanierController {

    @FXML
    private Label total;
    @FXML
    private VBox commantcontaint;
    @FXML
    void toorder(ActionEvent event) {
        CommandCRUD commandCRUD = new CommandCRUD();
        ProduitCRUD produitCrud = new ProduitCRUD();
        Map<Integer, Integer> cart = CartFileManager.loadCart();

        try {
            // Calculate the total price
            double totalPrice = 0;
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                Produit produit = produitCrud.getById(entry.getKey());
                totalPrice += produit.getPrix() * entry.getValue();
            }

            // Create a new command
            Command newCommand = new Command(0, 3, new ArrayList<>(cart.keySet()), totalPrice);
            commandCRUD.ajouter(newCommand); // Add the command to the database

            // Clear the cart
            CartFileManager.clearCart();
            commantcontaint.getChildren().clear(); // Clear the cart UI
            total.setText("0.0");
            // Show a success message
            showAlert("Order placed successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error placing order: " + e.getMessage());
        }
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void initialize() {
        loadCartProducts();

    }

    @FXML
    void gotostore(MouseEvent event) {
        // Check if the controller is being initialized
        if (commantcontaint == null) {
            return; // Exit the method without executing further code
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Store.fxml"));
            Parent storeRoot = loader.load();

            Stage stage = null;
            // Attempt to get the stage from the event source if it's a Node
            if (event.getSource() instanceof Node) {
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            }
            // If the stage could not be retrieved from the event, try the current scene's window
            if (stage == null && event.getPickResult().getIntersectedNode() != null) {
                stage = (Stage) event.getPickResult().getIntersectedNode().getScene().getWindow();
            }
            // Ensure that we have a valid stage to use
            if (stage != null) {
                Scene scene = new Scene(storeRoot);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions possibly thrown by the FXML loading
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load the store view.");
            alert.showAndWait();
        }
    }


    private void loadCartProducts() {

        Map<Integer, Integer> cart = CartFileManager.loadCart();
        ProduitCRUD produitCrud = new ProduitCRUD();

        try {
            for (Map.Entry<Integer, Integer> entry : cart.entrySet()) {
                Produit produit = produitCrud.getById(entry.getKey());
                if (produit != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SamplePanier.fxml"));
                    Node productNode = loader.load();
                    SamplePanierController controller = loader.getController();
                    produit.setQuantity(entry.getValue());
                    controller.setProduct(produit);
                    commantcontaint.getChildren().add(productNode);
                }
            }
            total.setText(String.format("%.2f", CartFileManager.calculateTotalPrice()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
