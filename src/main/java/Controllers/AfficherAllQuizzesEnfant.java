package Controllers;
import Entities.Quiz;
import Service.QuizCrud;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
public class AfficherAllQuizzesEnfant {




    @FXML
    private VBox mainVbox;
    private final QuizCrud qc = new QuizCrud();


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


        VBox contentBox = new VBox(nameText, nameTextTemp, nameTextNb);

        contentBox.setAlignment(Pos.TOP_CENTER);



        stackPane.setStyle("-fx-background-color: #ed6350; -fx-background-radius: 8px;-fx-cursor: pointer");
        stackPane.setMinHeight(160);
        stackPane.setMinWidth(160);
        stackPane.getChildren().addAll(contentBox);

        stackPane.setOnMouseClicked(event -> {
            int idQuiz = quiz.getId(); // Obtenir l'ID du quiz
               int userId=1;
            // Charger la scène du quiz en passant l'ID du quiz
            loadQuizScene(idQuiz,userId);
        });

// Méthode pour charger la scène du quiz



        stackPane.setOnMouseEntered(event -> {
            // Changer la couleur de fond lorsque le curseur est positionné dessus
            stackPane.setStyle("-fx-background-color: #fef437; -fx-background-radius: 8px;-fx-cursor: pointer");
        });

        stackPane.setOnMouseExited(event -> {
            // Rétablir la couleur de fond lorsque le curseur quitte le StackPane
            stackPane.setStyle("-fx-background-color: #ed6350; -fx-background-radius: 8px;-fx-cursor: pointer");
        });


        return stackPane;
    }

    private void loadQuizScene(int idQuiz,int userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherQuizEnfant.fxml"));
            Parent root = loader.load();
            AfficherQuizEnfant controller = loader.getController();

            // Passer l'ID du quiz au contrôleur AfficherQuizEnfant
            controller.setQuizId(idQuiz);
            controller.setUserId(userId);

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Récupérer la scène actuelle
            Stage stage = (Stage) mainVbox.getScene().getWindow();

            // Afficher la nouvelle scène
            stage.setScene(scene);
            stage.show();

            // Appeler initialize() une fois que la scène est chargée
            Platform.runLater(() -> {
                controller.initialize();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
