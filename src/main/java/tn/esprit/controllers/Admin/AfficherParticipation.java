package tn.esprit.controllers.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceParticipation;
import javafx.scene.control.TableCell;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.util.List;

public class AfficherParticipation {

    @FXML
    private TableView<Participation> participationTable;

    @FXML
    private TableColumn<Participation, String> dateColumn;

    @FXML
    private TableColumn<Participation, String> heureColumn;

    private final ServiceParticipation serviceParticipation = new ServiceParticipation();

    @FXML
    void initialize() {

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        heureColumn.setCellValueFactory(new PropertyValueFactory<>("heure"));


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
        TableColumn<Participation, Void> deleteColumn = new TableColumn<>("Supprimer");
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
        private final Button deleteButton = new Button("Supprimer");

        ButtonCell() {
            deleteButton.setOnAction(event -> {
                Participation participation = getTableView().getItems().get(getIndex());
                deleteParticipation(participation);
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
    }

}
