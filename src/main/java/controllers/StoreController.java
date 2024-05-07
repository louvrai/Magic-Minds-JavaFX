package controllers;

import Entities.Produit;
import Services.ProduitCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StoreController {
    private final ProduitCRUD ps=new ProduitCRUD();
    private ArrayList<Produit>produits;
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane produitcontainer;
    int colume=0;
    int row=1;
    @FXML
    void gotopanier(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Panier.fxml"));
            Parent cartRoot = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene of the current stage to the cart view
            Scene scene = new Scene(cartRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load the cart view.");
            alert.showAndWait();
        }
    }
    public void initialize() {
        System.out.println("Initializing StoreController...");
        try {
            produits = ps.afficherAll();
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Sampleproduct.fxml"));
                HBox productLayout = fxmlLoader.load();
                SampleproductController controller = fxmlLoader.getController();
                controller.setData(produit);
                cardLayout.getChildren().add(productLayout);
            }
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Sampleprod2.fxml"));
                VBox productLayout = fxmlLoader.load();
                Sampleprod2Controller controller = fxmlLoader.getController();
                controller.setData(produit);
                if (colume==6){
                    colume=0;
                    row++;
                }
                produitcontainer.add(productLayout,colume++,row);
                GridPane.setMargin(productLayout,new Insets(10));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void gotocommand(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Command.fxml"));
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
    @FXML
    void gotochat(MouseEvent event) {
        try {
            // Load the FXML file for the statistics view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Chat.fxml"));
            Parent root = loader.load();

            // Create a new stage for the statistics view
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Chatboot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions possibly thrown by the FXML loading
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Navigation Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load the statistics view.");
            alert.showAndWait();
        }

    }
}
