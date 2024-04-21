package Controllers;

import Entities.Quiz;
import Service.QuizCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ModifierQuizController {

    @FXML
    private TextField nvNbquestion;

    @FXML
    private TextField nvTime;

    @FXML
    private TextField nvTitre;
    private final QuizCrud qc = new QuizCrud();
    private Quiz QuizToModify =new Quiz();

    @FXML
    void modifierQuiz(ActionEvent event) {
        try {
            // Mettre à jour les données de la borne avec les nouvelles valeurs
            QuizToModify.setTitre(nvTitre.getText());
            QuizToModify.setTemp(Integer.parseInt( nvTime.getText()));
            QuizToModify.setNb_question(Integer.parseInt( nvNbquestion.getText()));



            // Appeler la méthode de mise à jour dans le service de borne
            qc.modifier(QuizToModify );
            Stage stage = (Stage) nvTitre.getScene().getWindow();
            stage.close();
            // Afficher une alerte pour confirmer la modification
            showAlert("Modification réussie", "La borne a été modifiée avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de la borne.");
        }

    }




        public void initData(Quiz quiz) {
            this.QuizToModify = quiz;
            // Afficher les détails de la borne à modifier dans les champs de texte
            nvTitre.setText(quiz.getTitre());
            nvNbquestion.setText(String.valueOf(quiz.getNb_question()));
            nvTime.setText(String.valueOf(quiz. getTemp()));


        }


        private void showAlert(String title, String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        }






}
