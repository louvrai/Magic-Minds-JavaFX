package Controllers;

import Entities.Questions;
import Entities.Quiz;
import Service.QuestionsCrud;
import Service.QuizCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModifierQuestionController {

    @FXML
    private TextField fxChoice1;

    @FXML
    private TextField fxChoice2;

    @FXML
    private TextField fxChoice3;

    @FXML
    private TextField fxCorrectAnswer;

    @FXML
    private TextField fxQuestion;

    @FXML
    private TextField fxQuizId;
    private final QuestionsCrud qc=new QuestionsCrud();
   private  Questions QuestionToModify=new Questions();

    @FXML
    void UpdateQuestion(ActionEvent event) {
        try {
            // Mettre à jour les données de la borne avec les nouvelles valeurs
            QuestionToModify.setQuestion(fxQuestion.getText());
            QuestionToModify.setChoix1(fxChoice1.getText());
            QuestionToModify.setChoix2(fxChoice2.getText());
            QuestionToModify.setChoix3(fxChoice3.getText());
            QuestionToModify.setReponse(fxCorrectAnswer.getText());
            QuestionToModify.setIdQuiz(Integer.parseInt( fxQuizId.getText()));




            // Appeler la méthode de mise à jour dans le service de borne
            qc.modifier(QuestionToModify );
            Stage stage = (Stage) fxQuestion.getScene().getWindow();
            stage.close();
            // Afficher une alerte pour confirmer la modification
            showAlert("Modification réussie", "La borne a été modifiée avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la modification de la borne.");
        }

    }

    public void initData(Questions questions) {
        this.QuestionToModify = questions;
        // Afficher les détails de la borne à modifier dans les champs de texte
        fxQuizId.setText(String.valueOf(questions.getIdQuiz()));
        fxQuestion.setText(questions.getQuestion());
        fxChoice1.setText(questions.getChoix1());
        fxChoice2.setText(questions.getChoix2());
        fxChoice3.setText(questions.getChoix3());
        fxCorrectAnswer.setText(questions.getReponse());




    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
