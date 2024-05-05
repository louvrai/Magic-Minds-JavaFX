package Controllers;

import Entities.Quiz;
import Service.QuizCrud;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherQuizProf {

    @FXML
    private VBox mainVbox;
    private final QuizCrud qc = new QuizCrud();
    private Quiz quizToUpdate;

    public void initialize() {
        try {
            List<Quiz> quizzes = qc.recuperer();

            mainVbox.setSpacing(10);
            int maxColumns = 4;
            for (int i = 0; i < quizzes.size(); i += maxColumns) {
                HBox rowHBox = new HBox();
                rowHBox.setSpacing(20);
                for (int j = i; j < Math.min(i + maxColumns, quizzes.size()); j++) {

                    Quiz quiz = quizzes.get(j);
                    StackPane userCard = createQuizCard(quiz);
                    rowHBox.getChildren().add(userCard);
                }
                mainVbox.getChildren().add(rowHBox);
            }

            Button addQuizButton = new Button("Add quiz");
            addQuizButton.setStyle("-fx-background-radius: 10px");
            addQuizButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterQuizProf.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) addQuizButton.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            Button showQuestionsButton = new Button("Show questions");
            showQuestionsButton.setStyle("-fx-background-radius: 10px");
            showQuestionsButton.setOnAction(event -> {
                try {
                    // Charger la vue de la nouvelle interface depuis le fichier FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuestionProf.fxml"));
                    Parent root = loader.load();

                    // Créer une nouvelle scène avec la vue chargée
                    Scene scene = new Scene(root);

                    // Obtenir la référence à la fenêtre actuelle
                    Stage stage = (Stage) showQuestionsButton.getScene().getWindow();

                    // Changer la scène de la fenêtre pour afficher la nouvelle interface
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer les exceptions liées au chargement de la vue
                }

            });

            Button showHistoryButton = new Button("Show quiz history");
            showHistoryButton.setStyle("-fx-background-radius: 10px");
          showHistoryButton.setOnAction(event -> {
                try {
                    // Charger la vue de la nouvelle interface depuis le fichier FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuizHistoryProf.fxml"));
                    Parent root = loader.load();

                    // Créer une nouvelle scène avec la vue chargée
                    Scene scene = new Scene(root);

                    // Obtenir la référence à la fenêtre actuelle
                    Stage stage = (Stage) showHistoryButton.getScene().getWindow();

                    // Changer la scène de la fenêtre pour afficher la nouvelle interface
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer les exceptions liées au chargement de la vue
                }

            });
            mainVbox.getChildren().addAll(addQuizButton,showQuestionsButton,showHistoryButton);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    private StackPane createQuizCard(Quiz quiz) {
        StackPane stackPane = new StackPane();

        Text nameText = new Text(quiz.getTitre());
        int temp = quiz.getTemp();
        Text nameTextTemp = new Text("Time:  " + Integer.toString(temp));
        int nb = quiz.getNb_question();
        Text nameTextNb = new Text("Nb questions:  " + Integer.toString(nb));

        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        nameText.setTranslateY(10);
        nameTextTemp.setTranslateY(25);
        nameTextNb.setTranslateY(35);

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-radius: 10px");
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-radius: 10px");

        VBox contentBox = new VBox(nameText, nameTextTemp, nameTextNb, updateButton,deleteButton);

        contentBox.setAlignment(Pos.TOP_CENTER);
        updateButton.setTranslateY(50);
        deleteButton.setTranslateY(80);
        updateButton.setOnAction(event -> {
            quizToUpdate = quiz;

            loadUpdateQuizScene();
        });


        stackPane.setStyle("-fx-background-color: #ed6350; -fx-background-radius: 8px;-fx-cursor: pointer");
        stackPane.setMinHeight(180);
        stackPane.setMinWidth(150);
        stackPane.getChildren().addAll(contentBox);
        deleteButton.setOnAction(event -> {
            try {
                qc.supprimer(quiz.getId());
                mainVbox.getChildren().remove(stackPane);
                refreshDisplay(); // Rafraîchir l'affichage après la suppression
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Gérer l'erreur de suppression de la question
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Error deleting question: " + ex.getMessage());
                alert.showAndWait();
            }

        });



        return stackPane;
    }
    private void refreshDisplay() {
        // Effacer le contenu actuel de la VBox
        mainVbox.getChildren().clear();
        // Recharger les quizzes et réafficher
        initialize();
    }

    private void loadUpdateQuizScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierquizProf.fxml"));
            Parent root = loader.load();
            ModifierquizProf controller = loader.getController();
            controller.setQuizToModify(quizToUpdate);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
            refreshDisplay();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
