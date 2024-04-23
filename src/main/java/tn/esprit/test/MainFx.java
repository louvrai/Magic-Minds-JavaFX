package tn.esprit.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherEvenement.fxml "));

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setTitle("Affichage d'événement");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
