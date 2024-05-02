package tn.esprit.controllers.Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Evenement;
import tn.esprit.services.ServiceEvenement;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class EditEvenementController {

    private final ServiceEvenement serviceEvenement = new ServiceEvenement();
    private Evenement evenementToEdit;
    private AfficherEventController afficherEvenementController;

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

    public void setAfficherEvenementController(AfficherEventController controller) {
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
    private boolean isValidString(String input) {
        return input.matches("[a-zA-Z\\s]+");
    }

    @FXML
    void modifierEvenement(ActionEvent event) {
        if (evenementToEdit != null) {

            if (!isValidString(tfNom.getText()) || !isValidString(tfDescription.getText()) || !isValidString(tfLocalisation.getText()) || !isValidString(tfCategorie.getText())) {
                showAlert("Les champs nom, description, localisation et catégorie doivent contenir uniquement des caractères alphabétiques et des espaces.", Alert.AlertType.ERROR);
                return;
            }

            evenementToEdit.setNom(tfNom.getText());
            evenementToEdit.setDescription(tfDescription.getText());
            evenementToEdit.setLocalisation(tfLocalisation.getText());
            evenementToEdit.setCategorie(tfCategorie.getText());


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



            try {
                LocalDate dateDebut = parseDate(tfDateDebut.getText());
                LocalDate dateFin = parseDate(tfDateFin.getText());


                if (!dateDebut.isAfter(LocalDate.now())) {
                    showAlert("La date de début doit être postérieure à la date actuelle.", Alert.AlertType.ERROR);
                    return;
                }


                if (!dateFin.isAfter(dateDebut)) {
                    showAlert("La date de fin doit être postérieure à la date de début.", Alert.AlertType.ERROR);
                    return;
                }


                evenementToEdit.setDate_debut(Date.valueOf(dateDebut));
                evenementToEdit.setDate_fin(Date.valueOf(dateFin));
            } catch (ParseException e) {
                showAlert("Format de date invalide", Alert.AlertType.ERROR);
                return;
            }
            System.out.println(evenementToEdit.getId());
            serviceEvenement.update(evenementToEdit.getId(), evenementToEdit);
            System.out.println("Événement modifié avec succès !");

            afficherEvenementController.loadEvents();

            redirectToEventDisplay(event);
        }
    }

    private void redirectToEventDisplay(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEvenementAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();


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


    private LocalDate parseDate(String dateString) throws ParseException {

        String[] patterns = {"yyyy-MM-dd", "yyyy-M-dd", "yyyy-MM-d", "yyyy-M-d"};

        for (String pattern : patterns) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                return LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {

            }
        }

        throw new ParseException("Format de date invalide", 0);
    }
}
