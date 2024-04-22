package tn.esprit.controllers.Professeur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.io.IOException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;

public class AfficherEvenementController {

    @FXML
    private VBox eventContainer;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    void initialize() {
        loadEvents();
    }

    public void loadEvents() {
        List<Evenement> evenements = serviceEvenement.getAll();
        eventContainer.getChildren().clear(); // Nettoyer les anciennes données

        for (Evenement evenement : evenements) {
            // Créer une carte pour chaque événement
            VBox card = createEventCard(evenement);
            eventContainer.getChildren().add(card);
        }
    }

    private VBox createEventCard(Evenement evenement) {
        VBox card = new VBox(10); // Espacement vertical entre les éléments de la carte
        card.getStyleClass().add("event-card");
        // Ajouter les détails de l'événement à la carte
        Label nomLabel = new Label("Nom: " + evenement.getNom());
        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        Label categorieLabel = new Label("Catégorie: " + evenement.getCategorie());
        Label dateDebutLabel = new Label("Date début: " + evenement.getDate_debut());
        Label dateFinLabel = new Label("Date fin: " + evenement.getDate_fin());
        Label nbParticipantsLabel = new Label("Nb Participants: " + evenement.getNb_participant());

        // Ajouter les boutons Supprimer et Modifier
        Button deleteButton = new Button("Supprimer");
        Button editButton = new Button("Modifier");

        // Associer des actions aux boutons
        deleteButton.setOnAction(event -> deleteEvent(evenement));
        editButton.setOnAction(event -> openEditForm(evenement));
// Créer une HBox pour placer les boutons devant les détails de l'événement
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(deleteButton, editButton);
        // Ajouter les éléments à la carte
        card.getChildren().addAll(
                nomLabel,
                descriptionLabel,
                localisationLabel,
                categorieLabel,
                dateDebutLabel,
                dateFinLabel,
                nbParticipantsLabel,
                buttonBox
        );

        // Centrer la carte
        card.setAlignment(Pos.CENTER);

        return card;
    }

    private void openEditForm(Evenement evenement) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditEvenement.fxml"));
            Parent root = loader.load();

            EditEvenementController editController = loader.getController();
            editController.setEvenementToEdit(evenement);

            // Passer l'instance de AfficherEvenementController
            editController.setAfficherEvenementController(this);

            // Afficher la fenêtre de modification sur la scène actuelle
            eventContainer.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteEvent(Evenement evenement) {
        serviceEvenement.delete(evenement);
        loadEvents(); // Rafraîchir la liste des événements après suppression
    }
}

