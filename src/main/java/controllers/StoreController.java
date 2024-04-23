package controllers;

import Entities.Produit;
import Services.ProduitCRUD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class StoreController {
    private final ProduitCRUD ps=new ProduitCRUD();
    private ArrayList<Produit>produits;
    @FXML
    private HBox cardLayout;
    @FXML
    private GridPane produitcontainer;
    int colume=0;
    int row=1;
    public void initialize() {
        System.out.println("Initializing StoreController...");
        try {
            produits = ps.afficherAll();
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Sampleproduct.fxml"));
                HBox productLayout = fxmlLoader.load();
                SampleproductController controller = fxmlLoader.getController();
                controller.setData(produit);
                cardLayout.getChildren().add(productLayout);
            }
            for (Produit produit : produits) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Sampleprod2.fxml"));
                VBox productLayout = fxmlLoader.load();
                Sampleprod2Controller controller = fxmlLoader.getController();
                controller.setData(produit);
                if (colume==6){
                    colume=0;
                    row++;
                }
                produitcontainer.add(productLayout,colume++,row);
                GridPane.setMargin(productLayout,new Insets(10));
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
