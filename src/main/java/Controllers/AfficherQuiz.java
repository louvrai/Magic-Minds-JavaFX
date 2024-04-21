package Controllers;

import Entities.Quiz;
import Service.QuizCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherQuiz {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<Quiz,Void> QuestionsTc;

    @FXML
    private Button fxAdd;

    @FXML
    private TableColumn<Quiz,Integer> cNb;

    @FXML
    private TableColumn<Quiz,Integer> cTime;

    @FXML
    private TableColumn<Quiz,String> cTitre;

    @FXML
    private TableColumn<Quiz,Void> deleteTc;

    @FXML
    private TableColumn<Quiz,Void> updateTc;
    @FXML
    private TableView<Quiz> tableId;
       QuizCrud qc =new QuizCrud();
    @FXML
    public void initialize() throws SQLException {
        // Initialize TableView columns
        cTime.setCellValueFactory(new PropertyValueFactory<>("temp"));
        cNb.setCellValueFactory(new PropertyValueFactory<>("nbquestion"));
        cTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));



        configureDeleteColumn();
       configureModifyColumn();
        loadQuizData();
    }

    private void loadQuizData()throws SQLException {
        List<Quiz> q =qc.recuperer();
        tableId.getItems().addAll(q);

    }

    public void afficherQuiz() throws SQLException {
        QuizCrud qc = new QuizCrud();
        List<Quiz> q = new ArrayList<>();
        q=qc.recuperer();
        ObservableList<Quiz> list = FXCollections.observableList(q);

        tableId.setItems(list);
        cTitre.setCellValueFactory(new PropertyValueFactory<Quiz,String>("titre"));

        cNb.setCellValueFactory(new PropertyValueFactory<Quiz,Integer>("nbquestion"));
        cTime.setCellValueFactory(new PropertyValueFactory<Quiz,Integer>("temp"));


    }

    private void configureDeleteColumn() {
        // Set up the delete button column
        deleteTc.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");


            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                    deleteButton.setOnAction(event -> {
                        Quiz QuizToDelete = getTableView().getItems().get(getIndex());

                        try {
                            deleteQuiz(QuizToDelete);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }
            }
        });
    }

    private void deleteQuiz(Quiz QuizToDelete) throws SQLException {
        qc.supprimer(QuizToDelete.getId());
        tableId.getItems().remove(QuizToDelete);
    }
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void modifyBorne(Quiz borneToModify) {
        try {
            // Charger l'interface de modification
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierQuiz.fxml"));
            Parent root = loader.load();

            // Passer les données de la borne à modifier au contrôleur de l'interface de modification
            ModifierQuizController modifierController = loader.getController();
            modifierController.initData(borneToModify);

            // Afficher l'interface de modification
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier Borne");
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'ouverture de l'interface de modification.");
        }
    }

    private void configureModifyColumn() {
        updateTc.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modify");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(modifyButton);
                    modifyButton.setOnAction(event -> {
                       Quiz QuizToModify = getTableView().getItems().get(getIndex());
                        modifyBorne(QuizToModify);
                    });
                }
            }
        });
    }
    @FXML
    void addQuiz(ActionEvent event) throws IOException {
            root = FXMLLoader.load(getClass().getResource("/Ajouter.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }


