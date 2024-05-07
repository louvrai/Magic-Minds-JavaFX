package tn.esprit.controllers.Parent;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceParticipation;
import tn.esprit.services.UserService;

import java.io.IOException;
import java.util.List;

public class AfficherParticipation {

    @FXML
    private VBox participationContainer;

    private final ServiceParticipation serviceParticipation = new ServiceParticipation();
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    private final UserService serviceUser = new UserService();
    @FXML
    void initialize() {
        loadParticipations();
    }

    public void loadParticipations() {
        List<Participation> participations = serviceParticipation.getAll();
        participationContainer.getChildren().clear();

        for (Participation participation : participations) {

            VBox card = createParticipationCard(participation);
            participationContainer.getChildren().add(card);
        }
    }

    private VBox createParticipationCard(Participation participation) {
        VBox card = new VBox(10);
        card.getStyleClass().add("participation-card");

        String eventName = serviceEvenement.getEventNameById(participation.getEvenementId());
        String username = serviceUser.getUsernameById(participation.getId_user_id());

        Label dateLabel = new Label("Date: " + participation.getDate());
        Label timeLabel = new Label("Time: " + participation.getHeure());
        Label eventNameLabel = new Label("Event Name: " + eventName);
        Label usernameLabel = new Label("User Name: " + username);
        Button deleteButton = new Button("Delete");

        deleteButton.setStyle("-fx-background-color: #fe5d37; -fx-font-weight: bold; -fx-text-fill: white;");



        deleteButton.setOnAction(event -> deleteParticipation(participation));
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton);

        card.getChildren().addAll(dateLabel, timeLabel,eventNameLabel,usernameLabel,deleteButton);

        return card;
    }

    private void deleteParticipation(Participation participation) {
        serviceParticipation.delete(participation);
        loadParticipations(); // Rafraîchir la liste des participations après suppression
    }
    @FXML
    void goBackeven(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementParent.fxml"));
            Parent root = loader.load();
            participationContainer.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
