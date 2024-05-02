/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temquestione file, choose Tools | Temquestiones
 * and open the temquestione in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.question;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import com.example.demo4.services.questionService;

/**
 * FXML Controller class
 *
 * @author asus
 */
public class AfficherquestionController implements Initializable {

    @FXML
    private GridPane gridev;

    questionService ab=new questionService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        afficherquestion();

    }




    public void afficherquestion(){
         try {
            List<question> question = ab.recupererquestion();
            gridev.getChildren().clear();
            int row = 0;
            int column = 0;
            for (int i = 0; i < question.size(); i++) {
                //chargement dynamique d'une interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("question.fxml"));
                AnchorPane pane = loader.load();

                //passage de parametres
                questionController controller = loader.getController();
                controller.setQuestion(question.get(i));
                gridev.add(pane, column, row);
                column++;
                if (column > 1) {
                    column = 0;
                    row++;
                }

            }
        } catch (SQLException | IOException ex) {
             ex.printStackTrace();
        }
    }







}
