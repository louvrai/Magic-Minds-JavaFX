package tn.esprit.controllers.Professeur;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceParticipation;
import java.util.List;

public class AfficherParticipation {

    @FXML
    private VBox participationContainer;

    private final ServiceParticipation serviceParticipation = new ServiceParticipation();

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

        Label dateLabel = new Label("Date: " + participation.getDate());
        Label heureLabel = new Label("Heure: " + participation.getHeure());


        Button deleteButton = new Button("Supprimer");


        deleteButton.setOnAction(event -> deleteParticipation(participation));
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton);

        card.getChildren().addAll(dateLabel, heureLabel, deleteButton);

        return card;
    }

    private void deleteParticipation(Participation participation) {
        serviceParticipation.delete(participation);
        loadParticipations();
    }
}
