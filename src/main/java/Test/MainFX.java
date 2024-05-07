package Test;

import Entities.CurrentUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        CurrentUser.user_id=3;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
       //FXMLLoader loader=new FXMLLoader(getClass().getResource("/Store.fxml"));
       //FXMLLoader loader=new FXMLLoader(getClass().getResource("/CommandBack.fxml"));
        //FXMLLoader loader=new FXMLLoader(getClass().getResource("/Productstac.fxml"));
        Parent root=loader.load();
        Scene scene =new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("add prod");
        primaryStage.show();
    }

}
