package Conrtoller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class AZIZ {

    @FXML
    private Button louay;

    @FXML
    void click(MouseEvent event) {
       louay.setStyle("-fx-background-color: RED");
    }

}