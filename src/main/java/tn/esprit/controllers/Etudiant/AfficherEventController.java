package tn.esprit.controllers.Etudiant;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tn.esprit.models.Evenement;
import tn.esprit.models.Participation;
import tn.esprit.services.ServiceEvenement;
import tn.esprit.services.ServiceParticipation;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;


public class AfficherEventController {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private final ServiceParticipation serviceParticipation = new ServiceParticipation();

    @FXML
    private VBox eventContainer;

    @FXML
    private Button calendarButton;


    @FXML
    void initialize() {
        loadCalendarButton();
        loadEvents();
        calendarButton.setVisible(true); // Rendre le bouton visible après le chargement des événements
    }
    @FXML
    private void voirCalendrier() {
        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("Le bouton voirCalendrier() a été déclenché.");

        redirectToFullCalendar();
    }
    private void loadCalendarButton() {
        calendarButton.setOnAction(event -> redirectToFullCalendar());
    }

    private void redirectToFullCalendar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FullCalendar.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) eventContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() {
        List<Evenement> evenements = serviceEvenement.getAll();
        eventContainer.getChildren().clear();

        int eventsPerRow = 5;
        int rowCount = (int) Math.ceil((double) evenements.size() / eventsPerRow);

        for (int i = 0; i < rowCount; i++) {
            TilePane rowPane = new TilePane();
            rowPane.setOrientation(Orientation.HORIZONTAL);
            rowPane.setPrefColumns(eventsPerRow);
            rowPane.setAlignment(Pos.CENTER);
            rowPane.setPadding(new Insets(10));
            rowPane.setHgap(10);
            rowPane.setVgap(10);

            int startIndex = i * eventsPerRow;
            int endIndex = Math.min(startIndex + eventsPerRow, evenements.size());

            for (int j = startIndex; j < endIndex; j++) {
                Evenement evenement = evenements.get(j);
                VBox card = createEventCard(evenement);
                rowPane.getChildren().add(card);
            }

            eventContainer.getChildren().add(rowPane);
        }
    }

    private VBox createEventCard(Evenement evenement) {
        VBox card = new VBox();
        card.getStyleClass().add("event-card");

        ImageView imageView = loadEventImage();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Label nomLabel = new Label("Nom: " + evenement.getNom());
        Label descriptionLabel = new Label("Description: " + evenement.getDescription());
        Label localisationLabel = new Label("Localisation: " + evenement.getLocalisation());
        Label categorieLabel = new Label("Catégorie: " + evenement.getCategorie());
        Label dateDebutLabel = new Label("Date début: " + evenement.getDate_debut());
        Label dateFinLabel = new Label("Date fin: " + evenement.getDate_fin());
        Label nbParticipantsLabel = new Label("Nb Participants: " + evenement.getNb_participant());
        Button participationButton = new Button("Participer");

        participationButton.setOnAction(event -> participer(evenement, 1));


        card.getChildren().addAll(
                imageView,
                nomLabel,
                descriptionLabel,
                localisationLabel,
                categorieLabel,
                dateDebutLabel,
                dateFinLabel,
                nbParticipantsLabel,
                participationButton
        );


        return card;
    }

    private ImageView loadEventImage() {
        ImageView imageView = new ImageView();
        try {
            InputStream inputStream = getClass().getResourceAsStream("/images/kids-event.jpg");
            if (inputStream != null) {
                Image image = new Image(inputStream);
                imageView.setImage(image);
            } else {
                System.err.println("L'image n'a pas pu être chargée. Assurez-vous que le chemin de l'image est correct.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageView;
    }

    private void participer(Evenement evenement, int id_user_id) {
        // Enregistrer la date et l'heure actuelles de la participation
        LocalDateTime now = LocalDateTime.now();
        Date dateParticipation = java.sql.Date.valueOf(now.toLocalDate()); // Convertir la LocalDate en Date
        Time heureParticipation = java.sql.Time.valueOf(now.toLocalTime()); // Convertir la LocalTime en Time


        Participation participation = new Participation();
        participation.setEvenementId(evenement.getId());
        participation.setId_user_id(id_user_id);
        participation.setDate(dateParticipation);
        participation.setHeure(heureParticipation);


        serviceParticipation.add(participation);


        redirectTo("AfficherParticipationEtudiant.fxml");
    }

    private void redirectTo(String AfficherParticipationEtudiant) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherParticipationEtudiant.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);


            Stage stage = (Stage) eventContainer.getScene().getWindow();


            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
