package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.IOException;
import javafx.fxml.FXMLLoader;


public class EditEvenementController {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private Evenement evenementToEdit;
    private tn.esprit.controllers.AfficherEvenementController afficherEvenementController;

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

    public void setEvenementToEdit(Evenement evenement) {
        this.evenementToEdit = evenement;
        populateFields();
    }

    public void setAfficherEvenementController(tn.esprit.controllers.AfficherEvenementController controller) {
        this.afficherEvenementController = controller;
    }

    private void populateFields() {
        if (evenementToEdit != null) {
            tfNom.setText(evenementToEdit.getNom());
            tfDescription.setText(evenementToEdit.getDescription());
            tfLocalisation.setText(evenementToEdit.getLocalisation());
            tfCategorie.setText(evenementToEdit.getCategorie());
            tfNbParticipant.setText(String.valueOf(evenementToEdit.getNb_participant()));
            tfDateDebut.setText(evenementToEdit.getDate_debut().toString());
            tfDateFin.setText(evenementToEdit.getDate_fin().toString());
        }
    }

    @FXML
    void modifierEvenement(ActionEvent event) {
        if (evenementToEdit != null) {
            evenementToEdit.setNom(tfNom.getText());
            evenementToEdit.setDescription(tfDescription.getText());
            evenementToEdit.setLocalisation(tfLocalisation.getText());
            evenementToEdit.setCategorie(tfCategorie.getText());

            // Convertir le nombre de participants en entier
            try {
                evenementToEdit.setNb_participant(Integer.parseInt(tfNbParticipant.getText()));
            } catch (NumberFormatException e) {
                showAlert("Nombre de participants invalide", Alert.AlertType.ERROR);
                return; // Arrêter ici si le nombre de participants n'est pas valide
            }

            // Convertir les dates de début et de fin en LocalDate
            try {
                LocalDate dateDebut = parseDate(tfDateDebut.getText());
                LocalDate dateFin = parseDate(tfDateFin.getText());

                // Affecter les dates converties à votre objet Evenement
                evenementToEdit.setDate_debut(Date.valueOf(dateDebut));
                evenementToEdit.setDate_fin(Date.valueOf(dateFin));
            } catch (ParseException e) {
                showAlert("Format de date invalide", Alert.AlertType.ERROR);
                return; // Arrêter ici si le format de date n'est pas valide
            }

            // Mettre à jour l'événement
            serviceEvenement.update(evenementToEdit);
            System.out.println("Événement modifié avec succès !");

            // Actualiser l'affichage des événements dans le contrôleur AfficherEvenementController
            afficherEvenementController.loadEvents();

            // Redirection vers la vue AfficherEvenement.fxml après la modification
            redirectToEventDisplay(event);
        }
    }

    private void redirectToEventDisplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenement.fxml"));
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
}
