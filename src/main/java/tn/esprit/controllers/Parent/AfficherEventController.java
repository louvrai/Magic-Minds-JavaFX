package tn.esprit.controllers.Parent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.util.List;
import javafx.geometry.Pos;

public class AfficherEventController {

    @FXML
    private VBox eventContainer;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    void initialize() {
        loadEvents();
    }

    public void loadEvents() {
        List<Evenement> evenements = serviceEvenement.getAll();
        eventContainer.getChildren().clear();

        for (Evenement evenement : evenements) {
            VBox card = createEventCard(evenement);
            eventContainer.getChildren().add(card);
        }
    }

    private VBox createEventCard(Evenement evenement) {
        VBox card = new VBox(10);
        card.getStyleClass().add("event-card");

        Label nomLabel = new Label("Nom: " + evenement.getNom());
        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        Label categorieLabel = new Label("Catégorie: " + evenement.getCategorie());
        Label dateDebutLabel = new Label("Date début: " + evenement.getDate_debut());
        Label dateFinLabel = new Label("Date fin: " + evenement.getDate_fin());
        Label nbParticipantsLabel = new Label("Nb Participants: " + evenement.getNb_participant());

        card.getChildren().addAll(
                nomLabel,
                descriptionLabel,
                localisationLabel,
                categorieLabel,
                dateDebutLabel,
                dateFinLabel,
                nbParticipantsLabel
        );

        card.setAlignment(Pos.CENTER);

        return card;
    }
}
