package tn.esprit.controllers.Professeur;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceParticipation;

import java.io.IOException;
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
        participationContainer.getChildren().clear(); // Nettoyer les anciennes données

        for (Participation participation : participations) {
            // Créer une carte pour chaque participation
            VBox card = createParticipationCard(participation);
            participationContainer.getChildren().add(card);
        }
    }

    private VBox createParticipationCard(Participation participation) {
        VBox card = new VBox(10); // Espacement vertical entre les éléments de la carte
        card.getStyleClass().add("participation-card");
        // Ajouter les détails de la participation à la carte
        Label dateLabel = new Label("Date: " + participation.getDate());
        Label heureLabel = new Label("Heure: " + participation.getHeure());

        // Ajouter le bouton Supprimer
        Button deleteButton = new Button("Supprimer");

        // Associer une action au bouton Supprimer
        deleteButton.setOnAction(event -> deleteParticipation(participation));
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton);
        // Ajouter les éléments à la carte
        card.getChildren().addAll(dateLabel, heureLabel, deleteButton);

        return card;
    }

    private void deleteParticipation(Participation participation) {
        serviceParticipation.delete(participation);
        loadParticipations(); // Rafraîchir la liste des participations après suppression
    }
}
