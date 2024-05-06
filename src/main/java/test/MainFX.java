package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent parent   = FXMLLoader.load(getClass().getResource("/statsPageUserController.fxml"));
       // statsPageUserController.fxml
       ///UserManagementController.fxml
        Scene scene = new Scene(parent);

        stage.setTitle("Dashboard");

       // stage.initStyle(StageStyle.UTILITY);
        stage.setScene(scene);

        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
