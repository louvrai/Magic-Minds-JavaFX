package tn.esprit.controllers.Parent;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class AfficherEventController {

    @FXML
    private VBox eventContainer;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> locationChoiceBox;


    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private List<Evenement> evenements; // Déclarer la variable evenements

    @FXML
    void initialize() {
        loadEvents();
        // Charger les choix pour la catégorie et la localisation
        loadCategoryChoices();
        loadLocationChoices();

        // Ajouter des écouteurs de changement pour les ChoiceBox
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filtrerEvenements());
        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filtrerEvenements());
    }
    private void loadCategoryChoices() {
        List<String> categories = serviceEvenement.getAllCategories();
        categoryChoiceBox.getItems().addAll(categories);
    }

    private void loadLocationChoices() {
        List<String> locations = serviceEvenement.getAllLocations();
        locationChoiceBox.getItems().addAll(locations);
    }

    private void filtrerEvenements() {
        String selectedCategory = categoryChoiceBox.getValue();
        String selectedLocation = locationChoiceBox.getValue();
        List<Evenement> evenementsFiltres = serviceEvenement.filtrerParCategorieEtLocalisation(evenements, selectedCategory, selectedLocation);
        afficherEvenements(evenementsFiltres);
    }

    private void afficherEvenements(List<Evenement> evenements) {
        eventContainer.getChildren().clear();

        for (Evenement evenement : evenements) {
            VBox card = createEventCard(evenement);
            eventContainer.getChildren().add(card);
        }
    }
    public void loadEvents() {
        evenements = serviceEvenement.getAll();
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
