package tn.esprit.controllers.Parent;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import tn.esprit.controllers.DateComparator;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

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
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filtrerEvenementsParCategorieEtLocalisation());
        locationChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> filtrerEvenementsParCategorieEtLocalisation());
    }
    private Image generateQRCode(String eventDetails) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        int width = 300;
        int height = 300;

        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(eventDetails, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }
            return SwingFXUtils.toFXImage(bufferedImage, null);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void loadCategoryChoices() {
        List<String> categories = serviceEvenement.getAllCategories();
        categoryChoiceBox.getItems().addAll(categories);
    }

    private void loadLocationChoices() {
        List<String> locations = serviceEvenement.getAllLocations();
        locationChoiceBox.getItems().addAll(locations);
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
        // Tri des événements selon la date de début
        Collections.sort(evenements, new DateComparator());
        // Affichage des événements triés
        afficherEvenements(evenements);
    }
    private void filtrerEvenementsParCategorieEtLocalisation() {
        String selectedCategory = categoryChoiceBox.getValue();
        String selectedLocation = locationChoiceBox.getValue();
        List<Evenement> evenementsFiltres = serviceEvenement.filtrerParCategorieEtLocalisation(evenements, selectedCategory, selectedLocation);
        // Tri des événements filtrés selon la date de début
        Collections.sort(evenementsFiltres, new DateComparator());
        // Affichage des événements filtrés et triés
        afficherEvenements(evenementsFiltres);
    }

    // Dans la méthode createEventCard
    private VBox createEventCard(Evenement evenement) {
        VBox card = new VBox(10);
        // Générer le QR code à partir des détails de l'événement
        Image qrCodeImage = generateQRCode("Event Title : " + evenement.getNom() + "\n" +
                "Event Description : " + evenement.getDescription() + "\n" +
                "Event Category : " + evenement.getCategorie()+ "\n" +
                "Event Location : " + evenement.getLocalisation()+ "\n" +
                "Start Date : " + evenement.getDate_debut() + "\n" +
                "End Date : " + evenement.getDate_fin() + "\n" +
                "Number of Participants :  " + evenement.getNb_participant());

        // Créer une ImageView pour afficher le QR code
        ImageView qrCodeImageView = new ImageView(qrCodeImage);

        card.getStyleClass().add("event-card");




        Label nomLabel = new Label("Event Title : " + evenement.getNom());


        Label localisationLabel = new Label("Event Location : " + evenement.getLocalisation());
        Label categorieLabel = new Label("Event Category : " + evenement.getCategorie());
        Button detailsButton = new Button("Display Details");

        // Ajouter un gestionnaire d'événement pour le clic sur le bouton
        detailsButton.setOnAction(event -> afficherDetails(evenement));

        card.getChildren().addAll(
                nomLabel,
                localisationLabel,
                categorieLabel,
                detailsButton, // Ajouter le bouton à la liste des enfants de la carte
                qrCodeImageView

        );


        // Ajouter l'ImageView à la carte
        card.setAlignment(Pos.CENTER);

        return card;
    }

    // Méthode pour afficher les détails complets de l'événement
    private void afficherDetails(Evenement evenement) {
        // Afficher les détails complets de l'événement, par exemple dans une boîte de dialogue
        // Vous pouvez utiliser une boîte de dialogue JavaFX ou toute autre méthode de votre choix
        // Par exemple :
        showDialog("Event Details",
                "Event Description : " + evenement.getDescription() + "\n" +
                        "Start Date : " + evenement.getDate_debut() + "\n" +
                        "End Date : " + evenement.getDate_fin() + "\n" +
                        "Number of Participants : " + evenement.getNb_participant());
    }

    // Méthode pour afficher une boîte de dialogue
    private void showDialog(String title, String content) {
        // Ajoutez ici la logique pour afficher une boîte de dialogue avec le titre et le contenu donnés
        // Vous pouvez utiliser Alert dans JavaFX ou toute autre méthode de votre choix
        // Par exemple :
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
