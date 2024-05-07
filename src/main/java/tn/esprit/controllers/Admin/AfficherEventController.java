package tn.esprit.controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AfficherEventController {

    @FXML
    private TableView<Evenement> eventTable;



    @FXML
    private TableColumn<Evenement, String> nomColumn;

    @FXML
    private TableColumn<Evenement, String> descriptionColumn;

    @FXML
    private TableColumn<Evenement, String> localisationColumn;

    @FXML
    private TableColumn<Evenement, String> categorieColumn;

    @FXML
    private TableColumn<Evenement, java.sql.Date> dateDebutColumn;

    @FXML
    private TableColumn<Evenement, java.sql.Date> dateFinColumn;

    @FXML
    private TableColumn<Evenement, Integer> nbParticipantColumn;

    @FXML
    private TableColumn<Evenement, Void> deleteColumn;

    @FXML
    private TableColumn<Evenement, Void> editColumn;
    @FXML
    private VBox chartContainer; // Nouveau conteneur pour le diagramme

    @FXML
    private ChoiceBox<String> categorieChoiceBox; // Nouveau ChoiceBox pour les catégories
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private AfficherEventController afficherEvenementController;


    public void setAfficherEvenementController(AfficherEventController controller) {
        this.afficherEvenementController = controller;
    }
    private void addEditButtonToTable() {
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Update");

            {
                editButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    openEditForm(evenement);
                });
                editButton.setStyle("-fx-background-color: #f3f35b; -fx-font-weight: bold; ");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                }
            }
        });
    }

    @FXML
    void initialize() {


        nbParticipantColumn.setCellValueFactory(new PropertyValueFactory<>("nb_participant"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        localisationColumn.setCellValueFactory(new PropertyValueFactory<>("localisation"));
        categorieColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        dateDebutColumn.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateFinColumn.setCellValueFactory(new PropertyValueFactory<>("date_fin"));

        addDeleteButtonToTable();
        addEditButtonToTable();

        loadEvents();
        generateStatistics(new ActionEvent());
    }
    @FXML
    void generateStatistics(ActionEvent event) {
        // Charger tous les événements
        List<Evenement> events = serviceEvenement.getAll();

        // Calculer le nombre total d'événements
        int totalEvents = events.size();

        // Calculer les statistiques pour chaque catégorie
        Map<String, Integer> categoryCounts = new HashMap<>();
        for (Evenement evt : events) {
            String category = evt.getCategorie();
            categoryCounts.put(category, categoryCounts.getOrDefault(category, 0) + 1);
        }

        // Créer une liste de données pour le diagramme
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            String category = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / totalEvents * 100; // Calculer le pourcentage
            pieChartData.add(new PieChart.Data(category + " (" + String.format("%.2f", percentage) + "%)", count));
        }

        // Créer le diagramme à partir des données
        createPieChart(pieChartData);
    }
    private void createPieChart(ObservableList<PieChart.Data> pieChartData) {
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Percentage of Events by Category");
        chartContainer.getChildren().clear();
        chartContainer.getChildren().add(chart);
        System.out.println("Pie Chart Data: " + pieChartData);
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    deleteEvent(evenement);
                });
                deleteButton.setStyle("-fx-background-color: #f3f35b; -fx-font-weight: bold; ");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void openEditForm(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditEvenementAdmin.fxml"));
            Parent root = loader.load();

            EditEvenementController editController = loader.getController();
            editController.setEvenementToEdit(evenement);


            editController.setAfficherEvenementController(this);

            eventTable.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() {
        List<Evenement> evenements = serviceEvenement.getAll();
        eventTable.getItems().setAll(evenements);
    }


    private void deleteEvent(Evenement evenement) {
        serviceEvenement.delete(evenement);
        loadEvents();
    }


    @FXML
    void goBackEA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEvenementAdmin.fxml"));
            Parent root = loader.load();

            // Vous pouvez ajouter d'autres configurations si nécessaire

            // Changer la scène pour afficher la vue AjouterEvenementAdmin
            eventTable.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void goBackPA(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParticipationAdmin.fxml"));
            Parent root = loader.load();

            // Vous pouvez ajouter d'autres configurations si nécessaire

            // Changer la scène pour afficher la vue AjouterEvenementAdmin
            eventTable.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
