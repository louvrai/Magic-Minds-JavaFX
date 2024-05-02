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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * FXML Controller class
 *
 * @author asus
 */
public class AjouterquestionController implements Initializable {

    @FXML
    private TextField descriptionevField;
    @FXML
    private ComboBox<String> typecb;
    @FXML
    private DatePicker dateevField;


    @FXML
    private TextField imageevField;
    @FXML
    private TextField nameField;


    @FXML
    private TableView<question> questionTv;
    @FXML
    private TableColumn<question, String> nomevTv;

    @FXML
    private TableColumn<question, String> imageevTv;
    @FXML
    private TableColumn<question, String> dateevTv;

    @FXML
    private TableColumn<question, String> descriptionevTv;
    @FXML
    private TableColumn<question, String> typeTv;


    ObservableList<question> evs;
    questionService Ev = new questionService();
    reponseService Pservice = new reponseService();


    @FXML
    private ImageView imageview;
    @FXML
    private TextField rechercher;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        //idLabel.setText("");
        getevs();
        typecb.setItems(FXCollections.observableArrayList("eleve", "parent"));
        typecb.getSelectionModel().selectFirst();
    }


    private boolean NoDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate myDate = dateevField.getValue();
        int comparisonResult = myDate.compareTo(currentDate);
        boolean test = true;
        if (comparisonResult < 0) {
            // myDate est antérieure à currentDate
            test = true;
        } else if (comparisonResult > 0) {
            // myDate est postérieure à currentDate
            test = false;
        }
        return test;
    }

    @FXML
    private void ajouterquestion(ActionEvent ev) {
        if ((nameField.getText().length() == 0) || (imageevField.getText().length() == 0) ||
                (descriptionevField.getText().length() == 0) || (typecb.getValue() == null)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");
            alert.showAndWait();
        } else if (NoDate()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("la date de date doit être après la date d'aujourd'hui");
            alert.showAndWait();
        } else {
            question e = new question();
            e.setName(nameField.getText());
            e.setCommentaire(descriptionevField.getText());
            e.setType(typecb.getValue()); // Get the selected type from ComboBox

            java.util.Date date_debut = java.util.Date.from(dateevField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date sqlDate = new Date(date_debut.getTime());
            e.setDate(sqlDate);
            e.setImage(imageevField.getText());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information ");
            alert.setHeaderText("question add");
            alert.setContentText("question added successfully!");
            alert.showAndWait();

            try {
                Ev.ajouterquestion(e);
                reset();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            getevs();
        }
    }


    //fin d ajout d'un question
    private void reset() {
        nameField.setText("");

        descriptionevField.setText("");
        typecb.cancelEdit();
        imageevField.setText("");


        dateevField.setValue(null);

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

            descriptionevTv.setCellValueFactory(new PropertyValueFactory<>("commentaire"));
            typeTv.setCellValueFactory(new PropertyValueFactory<>("type"));
            // this.delete();
        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }//get evs


    @FXML
    private void modifierquestion(ActionEvent ev) {
        if (questionTv.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a question from the table to modify.");
            alert.showAndWait();
            return;
        }

        // Get the selected question
        question selectedQuestion = questionTv.getSelectionModel().getSelectedItem();

        // Update the selected question's properties with the fields' values
        selectedQuestion.setName(nameField.getText());
        selectedQuestion.setCommentaire(descriptionevField.getText());
        selectedQuestion.setType(typecb.getValue());
        selectedQuestion.setImage(imageevField.getText());

        // If the date is needed to be updated too
        if (dateevField.getValue() != null) {
            java.util.Date date = java.util.Date.from(dateevField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date sqlDate = new Date(date.getTime());
            selectedQuestion.setDate(sqlDate);
        }

        try {
            Ev.modifierquestion(selectedQuestion);  // Assuming 'modifierquestion' handles the SQL update
            reset();
            getevs();  // Refresh the table view
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Question Updated");
            alert.setHeaderText(null);
            alert.setContentText("The question has been updated successfully.");
            alert.showAndWait();
        } catch (SQLException ex) {
            Logger.getLogger(AjouterquestionController.class.getName()).log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("Error Modifying Question");
            alert.setContentText("The question could not be updated due to a database error:\n" + ex.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    private void supprimerquestion(ActionEvent ev) {
        question e = questionTv.getItems().get(questionTv.getSelectionModel().getSelectedIndex());
        try {
            Ev.supprimerquestion(e);
        } catch (SQLException ex) {
            Logger.getLogger(AjouterquestionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information ");
        alert.setHeaderText("question delete");
        alert.setContentText("question deleted successfully!");
        alert.showAndWait();
        getevs();
    }

    @FXML
    private void afficherquestion(ActionEvent ev) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficherquestion.fxml")));
            nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void choisirev(MouseEvent ev) throws IOException {
        question selectedQuestion = questionTv.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            nameField.setText(selectedQuestion.getName());
            imageevField.setText(selectedQuestion.getImage());
            descriptionevField.setText(selectedQuestion.getCommentaire());
            typecb.setValue(selectedQuestion.getType());
            if (selectedQuestion.getDate() != null) {
                LocalDate localDate = selectedQuestion.getDate().toLocalDate();
                dateevField.setValue(localDate);
            }

            if (selectedQuestion.getImage() != null && !selectedQuestion.getImage().isEmpty()) {
                File file = new File(selectedQuestion.getImage());
                if (file.exists()) {
                    Image img = new Image(file.toURI().toString());
                    imageview.setImage(img);
                } else {
                    imageview.setImage(null); // or set to a default image
                }
            }
        }
    }


    private void repondre(ActionEvent ev) {

        User u = new User();

        reponse p = new reponse();

        //p.setquestion();

        p.setId_user(u.getId());
        Pservice.ajouterreponse(p);
        try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficherreponse.fxml")));
            nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void afficherreponses(ActionEvent ev) {
        try {
            //navigation
            Parent loader = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("afficherreponse.fxml")));
            nameField.getScene().setRoot(loader);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void uploadImage(ActionEvent ev) throws FileNotFoundException, IOException {
        Random rand = new Random();
        int x = rand.nextInt(1000);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Upload File Path");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        String FileName = file.getName().substring(file.getName().lastIndexOf('.'));
        // Adjust the path to store images inside the project's resources directory
        String relativePath = "src/main/resources/images/" + x + FileName;

        if (file != null) {
            FileInputStream sourceStream = new FileInputStream(file.getAbsolutePath());
            FileOutputStream destinationStream = new FileOutputStream(relativePath);
            BufferedInputStream bin = new BufferedInputStream(sourceStream);
            BufferedOutputStream bout = new BufferedOutputStream(destinationStream);

            System.out.println("File path: " + file.getAbsolutePath());
            Image img = new Image(file.toURI().toString());
            imageview.setImage(img);
            imageevField.setText(x + FileName);

            int byteContent;
            while ((byteContent = bin.read()) != -1) {
                bout.write(byteContent);
            }
            bin.close();
            bout.close();

            // You might need to adjust this part to work with your specific database setup
            // For example: insertFileNameIntoDatabase(file.getName(), relativePath);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Image uploaded successfully!");
            alert.showAndWait();
        } else {
            System.out.println("No file selected");
        }
    }


    @FXML
    private void rechercherev(KeyEvent ev) {

        questionService bs = new questionService();
        question b = new question();
        ObservableList<question> filter = bs.chercherev(rechercher.getText());
        populateTable(filter);
    }

    private void populateTable(ObservableList<question> branlist) {
        questionTv.setItems(branlist);

    }


}


    





    

