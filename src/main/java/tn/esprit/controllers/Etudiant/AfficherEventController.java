
package tn.esprit.controllers.Etudiant;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.InputStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceParticipation;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import javafx.event.ActionEvent;
import tn.esprit.models.Participation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;

public class AfficherEventController {

    @FXML
    private VBox eventContainer;

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceParticipation serviceParticipation = new ServiceParticipation();
    @FXML
    private VBox participationContainer;

    @FXML
    void initialize() {
        loadEvents();
        // Initialize participationContainer
        participationContainer = new VBox();
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
        ImageView imageView = new ImageView();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/images/kids-event.jpg");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                imageView.setImage(image);
                imageView.setFitWidth(100); // Réglez la largeur de l'image selon vos besoins
                imageView.setFitHeight(100); // Réglez la hauteur de l'image selon vos besoins
            } else {
                System.err.println("L'image n'a pas pu être chargée. Assurez-vous que le chemin de l'image est correct.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Label nomLabel = new Label("Nom: " + evenement.getNom());
        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        Label categorieLabel = new Label("Catégorie: " + evenement.getCategorie());
        Label dateDebutLabel = new Label("Date début: " + evenement.getDate_debut());
        Label dateFinLabel = new Label("Date fin: " + evenement.getDate_fin());
        Label nbParticipantsLabel = new Label("Nb Participants: " + evenement.getNb_participant());

        // Créer un bouton de participation
        Button participationButton = new Button("Participer");

        participationButton.setOnAction(event -> participer(evenement, 1)); // 1 est l'ID de l'utilisateur


        // Ajouter les éléments à la carte
        card.getChildren().addAll(
                nomLabel,
                descriptionLabel,
                localisationLabel,
                categorieLabel,
                dateDebutLabel,
                dateFinLabel,
                nbParticipantsLabel,
                participationButton
        );

        // Centrer la carte
        card.setAlignment(Pos.CENTER);

        return card;
    }

    private void participer(Evenement evenement, int id_user_id) {
        // Enregistrer la date et l'heure actuelles de la participation
        LocalDateTime now = LocalDateTime.now();
        Date dateParticipation = java.sql.Date.valueOf(now.toLocalDate()); // Convertir la LocalDate en Date
        Time heureParticipation = java.sql.Time.valueOf(now.toLocalTime()); // Convertir la LocalTime en Time

        // Créer une nouvelle participation
        Participation participation = new Participation();
        participation.setEvenementId(evenement.getId());
        participation.setId_user_id(id_user_id); // Utiliser l'ID de l'utilisateur
        participation.setDate(dateParticipation);
        participation.setHeure(heureParticipation);

        // Enregistrer la participation
        serviceParticipation.add(participation);

        // Rediriger vers la vue d'affichage des participations
        redirectTo("AfficherParticipationEtudiant.fxml");
    }

    private void redirectTo(String AfficherParticipationEtudiant) {
        try {
            // Charger le fichier FXML de la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParticipationEtudiant.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène avec la vue chargée
            Scene scene = new Scene(root);

            // Obtenir l'objet Stage à partir de n'importe quel nœud de la scène actuelle
            Stage stage = (Stage) eventContainer.getScene().getWindow();

            // Changer la scène vers la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private VBox createParticipationCard(Participation participation) {
        VBox card = new VBox(10); // Espacement vertical entre les éléments de la carte
        card.getStyleClass().add("participation-card");
        // Ajouter les détails de la participation à la carte
        Label dateLabel = new Label("Date: " + participation.getDate());
        Label heureLabel = new Label("Heure: " + participation.getHeure());

        // Ajouter les éléments à la carte
        card.getChildren().addAll(
                dateLabel,
                heureLabel
        );

        // Centrer la carte
        card.setAlignment(Pos.CENTER);

        return card;
    }
}