package controllers;
import Entities.Command;
import Entities.CurrentUser;
import Services.CommandCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CommandController {
    @FXML
    private VBox commantcontaint;

    @FXML
    void gotostore(MouseEvent event) {
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

    @FXML
    void initialize() {
        loadCommandsFromDatabase(CurrentUser.user_id);
    }

    private void loadCommandsFromDatabase(int iduser) {
        CommandCRUD commandCRUD = new CommandCRUD();
        try {
            ArrayList<Command> commands = commandCRUD.afficherAll(iduser);
            for (Command command : commands) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SimpleCommand.fxml"));
                try {
                    commantcontaint.getChildren().add(loader.load());
                    SimpleCommandController controller = loader.getController();
                    controller.setCommand(command);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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

}
