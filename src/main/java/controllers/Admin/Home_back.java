package controllers.Admin;

import controllers.Outil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Home_back implements Initializable {

    @FXML
    private Label NumC;
    @FXML
    private Button btn_corses;
    Outil outil=new Outil();

    @FXML
    void goToCourses(MouseEvent event) {
        try {
            Parent addChapPageRoot = FXMLLoader.load(getClass().getResource("/GestionCours.fxml"));
            Stage stage = (Stage) btn_corses.getScene().getWindow();
            Scene newPageScene = new Scene(addChapPageRoot,956,612);
            stage.setScene(newPageScene);
            stage.setTitle("Courses");
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NumC.setText(String.valueOf(outil.totalCourses()));
    }
}
