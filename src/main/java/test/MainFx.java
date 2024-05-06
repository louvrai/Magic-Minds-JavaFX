package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/CoursesEnfant.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Courses");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println("test2");
        launch();
    }
}
