package tn.esprit.controllers.Admin;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceParticipation;
import tn.esprit.services.UserService;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class AfficherParticipation {

    @FXML
    private TableView<Participation> participationTable;

    @FXML
    private TableColumn<Participation, String> dateColumn;
    @FXML
    private TableColumn<Participation, String> NomuserColumn;
    @FXML
    private TableColumn<Participation, String> NomeventColumn;

    @FXML
    private TableColumn<Participation, String> heureColumn;

    private final ServiceParticipation serviceParticipation = new ServiceParticipation();
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    private final UserService serviceUser = new UserService();
    @FXML
    void initialize() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));
        NomuserColumn.setCellValueFactory(cellData -> {
            String username = serviceUser.getUsernameById(cellData.getValue().getId_user_id());
            return new SimpleStringProperty(username);
        });

        // Cell factory pour le nom de l'événement
        NomeventColumn.setCellValueFactory(cellData -> {
            String eventName = serviceEvenement.getEventNameById(cellData.getValue().getEvenementId());
            return new SimpleStringProperty(eventName);
        });


        List<Participation> participations = serviceParticipation.getAll();


        participationTable.getItems().addAll(participations);


        addButtonToTable();
    }

    @FXML
    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void addButtonToTable() {
        TableColumn<Participation, Void> deleteColumn = new TableColumn<>("Delete" );
        deleteColumn.setMinWidth(80);

        deleteColumn.setCellFactory(param -> new ButtonCell());

        deleteColumn.setOnEditCommit(event -> {
            Participation participation = event.getRowValue();
            deleteParticipation(participation);
        });

        participationTable.getColumns().add(deleteColumn);
    }

    @FXML
    private void deleteParticipation(Participation participation) {
        if (participation != null) {
            serviceParticipation.delete(participation);
            participationTable.getItems().remove(participation);
        } else {
            // Afficher un message d'erreur si aucune participation n'est sélectionnée
            showAlert("Veuillez sélectionner une participation à supprimer.", Alert.AlertType.ERROR);
        }
    }


    private class ButtonCell extends TableCell<Participation, Void> {
        private final Button deleteButton = new Button("Delete");

        ButtonCell() {
            deleteButton.setOnAction(event -> {

                Participation participation = getTableView().getItems().get(getIndex());
                deleteParticipation(participation);
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
    }

}
