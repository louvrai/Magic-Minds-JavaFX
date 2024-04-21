package Controllers;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Service.QuizCrud;
import Entities.Quiz;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class AjouterQuiz {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button fxAdd;

    @FXML
    private TextField tfNbbu;

    @FXML
    private TextField tfTemps;

    @FXML
    private TextField tfTitre;

    @FXML
    void addQuiz(ActionEvent event) throws SQLException{


            if (isInputValid()) {
                String titre = tfTitre.getText();
                int temp = Integer.parseInt(tfTemps.getText());
                int nbquestion = Integer.parseInt(tfNbbu.getText());


                QuizCrud quiz = new QuizCrud();
                QuizCrud ps = new QuizCrud();



                Quiz q = new Quiz(titre,temp,nbquestion);
                ps.ajouter(q);
                System.out.println("ajouter avec succés");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficher.fxml"));
                try {
                    Parent root = loader.load();
                    tfTitre.getScene().setRoot(root);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            }



        private boolean isInputValid() {
            if (tfTitre.getText().isEmpty() || tfTemps.getText().isEmpty() || tfNbbu.getText().isEmpty()) {
                showAlert("Error", "Enter a valid content !");
                return false;
            }

            if (tfTitre.getText().matches("[0-9]+")) {
                showAlert("Error", "Title must be string not number !");
                return false;
            }




            if (tfTemps.getText().matches("[A-Z]+") || tfTemps.getText().matches("[a-z]+")) {
                showAlert("Error", "time must be numeric !");
                return false;
            }
            if (tfNbbu.getText().matches("[A-Z]+") || tfNbbu.getText().matches("[a-z]+")) {
                showAlert("Error", "number of questions must be numeric !");
                return false;
            }



            // Ajoutez des conditions similaires pour les autres champs...

            return true;
        }

            void showAlert(String title, String contentText) {
            Alert alertType = new Alert(Alert.AlertType.ERROR);
            alertType.setTitle(title);
            alertType.setHeaderText(contentText);
            alertType.show();
            }
    @FXML
    void afficher(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass()
                .getResource("/AfficherQuiz.fxml"));
        try {
            Parent root = loader.load();

            tfTitre.getScene().setRoot(root);

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
    }

}
