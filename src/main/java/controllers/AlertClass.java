package controllers;

import javafx.scene.control.Alert;

public class AlertClass {
    public void showEAlert(String title, String contentText) {
        Alert alertType = new Alert(Alert.AlertType.ERROR);
        alertType.setTitle(title);
        alertType.setHeaderText(contentText);
        alertType.show();
    }
    public void showSAlert(String title, String contentText) {
        Alert alertType = new Alert(Alert.AlertType.INFORMATION);
        alertType.setTitle(title);
        alertType.setHeaderText(contentText);
        alertType.getDialogPane().setGraphic(null);
        alertType.show();
    }
}
