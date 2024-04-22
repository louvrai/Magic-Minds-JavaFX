package tn.esprit.controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.ParseException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjouterEvenement {
    private final ServiceEvenement serviceEvenement = new ServiceEvenement();

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfLocalisation;

    @FXML
    private TextField tfCategorie;

    @FXML
    private TextField tfNbParticipant;

    @FXML
    private TextField tfDateDebut;

    @FXML
    private TextField tfDateFin;


    @FXML
    void ajouterEvent(ActionEvent event) {
        // Vérifier si les champs de nom, description, localisation et catégorie contiennent uniquement des caractères alphabétiques et des espaces
        if (!isValidString(tfNom.getText()) || !isValidString(tfDescription.getText()) || !isValidString(tfLocalisation.getText()) || !isValidString(tfCategorie.getText())) {
            showAlert("Les champs nom, description, localisation et catégorie doivent contenir uniquement des caractères alphabétiques et des espaces.", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier si la date de début est postérieure à la date actuelle
        LocalDate dateDebut;
        try {
            dateDebut = parseDate(tfDateDebut.getText());
            if (!dateDebut.isAfter(LocalDate.now())) {
                showAlert("La date de début doit être postérieure à la date actuelle.", Alert.AlertType.ERROR);
                return;
            }
        } catch (ParseException e) {
            showAlert("Format de date de début invalide. Utilisez le format YYYY-MM-DD.", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier si la date de fin est postérieure à la date de début
        LocalDate dateFin;
        try {
            dateFin = parseDate(tfDateFin.getText());
            if (!dateFin.isAfter(dateDebut)) {
                showAlert("La date de fin doit être postérieure à la date de début.", Alert.AlertType.ERROR);
                return;
            }
        } catch (ParseException e) {
            showAlert("Format de date de fin invalide. Utilisez le format YYYY-MM-DD.", Alert.AlertType.ERROR);
            return;
        }

        // Vérifier si le nombre de participants est supérieur à 20
        try {
            int nbParticipants = Integer.parseInt(tfNbParticipant.getText());
            if (nbParticipants <= 20) {
                showAlert("Le nombre de participants doit être supérieur à 20.", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Nombre de participants invalide", Alert.AlertType.ERROR);
            return;
        }

        // Si toutes les vérifications passent, créer l'objet Evenement et l'ajouter
        Evenement evenement = new Evenement();
        evenement.setNom(tfNom.getText());
        evenement.setDescription(tfDescription.getText());
        evenement.setLocalisation(tfLocalisation.getText());
        evenement.setCategorie(tfCategorie.getText());
        evenement.setNb_participant(Integer.parseInt(tfNbParticipant.getText()));
        evenement.setDate_debut(Date.valueOf(dateDebut));
        evenement.setDate_fin(Date.valueOf(dateFin));
        serviceEvenement.add(evenement);
        System.out.println("Événement ajouté avec succès !");

        // Redirection vers la vue AfficherEvenement.fxml après l'ajout
        redirectToEventDisplay(event);
    }

    // Méthode pour vérifier si une chaîne contient uniquement des caractères alphabétiques et des espaces
    private boolean isValidString(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

    private void showAlert(String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour analyser la date avec un format flexible
    private LocalDate parseDate(String dateString) throws ParseException {
        // Modèle de format flexible
        String[] patterns = {"yyyy-MM-dd", "yyyy-M-dd", "yyyy-MM-d", "yyyy-M-d"};

        // Essayer chaque modèle de format
        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                // Ignorer et essayer le prochain modèle
            }
        }
        // Si aucun modèle de format ne correspond
        throw new ParseException("Format de date invalide", 0);
    }
    // Méthode pour rediriger vers la vue AfficherEvenement.fxml
    private void redirectToEventDisplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            ((Stage) tfNom.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
