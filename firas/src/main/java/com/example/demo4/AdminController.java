/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this temquestione file, choose Tools | Temquestiones
 * and open the temquestione in the editor.
 */
package com.example.demo4;

import com.example.demo4.entities.User;
import com.example.demo4.entities.question;
import com.example.demo4.entities.reponse;
import com.example.demo4.services.questionService;
import com.example.demo4.services.reponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class AdminController implements Initializable {


    @FXML
    private TableView<reponse> tablereponse;
    questionService ab = new questionService();
    @FXML
    private TableColumn<reponse, Integer> iduserTv;
    @FXML
    private TableColumn<reponse, Integer> idevTv;
    @FXML
    private TableColumn<reponse, Date> datePartTv;
    @FXML
    private TableColumn<reponse, String> descriptionevTv;
    @FXML
    private TableColumn<reponse, String> fullnameevTv;

    @FXML
    private TableView<question> questionTv;
    @FXML
    private TableColumn<question, String> nomevTv;

    @FXML
    private TableColumn<question, String> imageevTv;
    @FXML
    private TableColumn<question, String> dateevTv;

    @FXML
    private TableColumn<question, String> descriptionevTvv;
    @FXML
    private TableColumn<question, String> typeTv;

    reponseService Ps = new reponseService();


    ObservableList<question> evs;
    questionService Ev = new questionService();
    reponseService Pservice = new reponseService();


    @FXML
    private ImageView imageview;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //idLabel.setText("");
        getevs();
        getReponse();
    }


    public void getevs() {
        try {
            // TODO
            List<question> question = Ev.recupererquestion();
            ObservableList<question> olp = FXCollections.observableArrayList(question);
            questionTv.setItems(olp);
            nomevTv.setCellValueFactory(new PropertyValueFactory<>("name"));

            imageevTv.setCellValueFactory(new PropertyValueFactory<>("image"));
            dateevTv.setCellValueFactory(new PropertyValueFactory<>("date"));

            descriptionevTvv.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            typeTv.setCellValueFactory(new PropertyValueFactory<>("type"));


            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get evs


    @FXML
    private void supprimerquestion(ActionEvent ev) {
        question e = questionTv.getItems().get(questionTv.getSelectionModel().getSelectedIndex());
        try {
            Ev.supprimerquestion(e);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("question delete");
        alert.setContentText("question deleted successfully!");
        alert.showAndWait();
        getevs();
    }


    @FXML
    private void supprimerreponse(ActionEvent ev) {
        reponse p = tablereponse.getItems().get(tablereponse.getSelectionModel().getSelectedIndex());

        try {
            Ps.Deletereponse(p);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterquestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("reponse delete");
        alert.setContentText("reponse deleted successfully!");
        alert.showAndWait();
        getReponse();

    }

    public void getReponse() {
        try {


            // TODO
            List<reponse> part = Ps.recupererComment();
            ObservableList<reponse> olp = FXCollections.observableArrayList(part);
            tablereponse.setItems(olp);
            iduserTv.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            idevTv.setCellValueFactory(new PropertyValueFactory<>("id"));

            descriptionevTv.setCellValueFactory(new PropertyValueFactory<>("description"));
            fullnameevTv.setCellValueFactory(new PropertyValueFactory<>("fullname"));
            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

  // statistique entre parent et eleve
    @FXML
    public void showStat(ActionEvent actionEvent) {
        try {
            // Fetch the data
            Map<String, Integer> data = ab.countQuestionsByType();
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            data.forEach((type, count) -> pieChartData.add(new PieChart.Data(type, count)));

            PieChart pieChart = new PieChart(pieChartData);
            pieChart.setTitle("Questions per Type");

            // Create a new scene and stage
            Scene scene = new Scene(pieChart, 800, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Statistics");
            stage.show();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, "Failed to fetch statistics", ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Error fetching data");
            alert.setContentText("Unable to fetch statistics from the database.");
            alert.showAndWait();
        }
    }

}


    





    

