package controllers;

import Entities.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SamplePanierController {

    @FXML
    private ImageView img;

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label qt;

    @FXML
    private Label tt;
    public void setProduct(Produit produit) {
        name.setText(produit.getNom());
        price.setText(String.format("%.2f", produit.getPrix()));
        qt.setText(String.valueOf(produit.getQuantity()));
        tt.setText(String.format("%.2f", produit.getPrix() * produit.getQuantity()));
        if (!produit.getImg1().isEmpty()) {
            img.setImage(new Image(produit.getImg1()));
        }
    }
}
