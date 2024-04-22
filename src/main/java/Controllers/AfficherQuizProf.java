package Controllers;

import Entities.Quiz;
import Service.QuizCrud;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.List;

public class AfficherQuizProf {
    @FXML
    private GridPane quizGrid;

    private final QuizCrud quizCrud = new QuizCrud();

    @FXML
    public void initialize() {
        afficherQuizzes();
    }

    private void afficherQuizzes() {
        try {
            List<Quiz> quizzes = quizCrud.recuperer();
            int row = 0;
            int col = 0;

            for (Quiz quiz : quizzes) {
                Label titleLabel = new Label(quiz.getTitre());
                Label tempLabel = new Label("Temp: " + quiz.getTemp());
                Label nbQuestionsLabel = new Label("Nombre de questions: " + quiz.getNb_question());

                quizGrid.add(titleLabel, col, row);
                quizGrid.add(tempLabel, col, row + 1);
                quizGrid.add(nbQuestionsLabel, col, row + 2);

                col++;
                if (col == 2) {
                    col = 0;
                    row += 3; // Move to the next row
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
