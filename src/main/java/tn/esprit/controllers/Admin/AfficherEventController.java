package tn.esprit.controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import javafx.scene.control.TableCell;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class AfficherEventController {

    @FXML
    private TableView<Evenement> eventTable;

    @FXML
    private TableColumn<Evenement, Integer> idColumn;

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

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private AfficherEventController afficherEvenementController;


    public void setAfficherEvenementController(AfficherEventController controller) {
        this.afficherEvenementController = controller;
    }
    private void addEditButtonToTable() {
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");

            {
                editButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    openEditForm(evenement);
                });
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
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
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
    }

    private void addDeleteButtonToTable() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Supprimer");

            {
                deleteButton.setOnAction(event -> {
                    Evenement evenement = getTableView().getItems().get(getIndex());
                    deleteEvent(evenement);
                });
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





}
