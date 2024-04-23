package controllers;

import Entities.Produit;
import Services.ProduitCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

public class AjouterProduitContoller {
    private final ProduitCRUD ps=new ProduitCRUD();
    @FXML
    private Button btn_corses;

    @FXML
    private Button btn_events;

    @FXML
    private Button btn_forum;

    @FXML
    private Button btn_quizzs;

    @FXML
    private Button btn_store;

    @FXML
    private Button btn_user;


    @FXML
    private TableColumn<Produit, String> categoryColumn;

    @FXML
    private TextField categorypro;

    @FXML
    private TableColumn<Produit, String> descColumn;

    @FXML
    private TextField descpro;

    @FXML
    private TextField img1pro;

    @FXML
    private TextField img2pro;

    @FXML
    private TextField img3pro;

    @FXML
    private TableColumn<Produit, String> imgColumn;

    @FXML
    private Pane inner_pane;

    @FXML
    private Button mc;

    @FXML
    private TableColumn<Produit, String> nameColumn;

    @FXML
    private TextField namepro;

    @FXML
    private TableColumn<Produit, Double> priceColumn;

    @FXML
    private TextField pricepro;

    @FXML
    private TableView<Produit> productTable;

    @FXML
    private TableColumn<Produit, Integer> quantityColumn;

    @FXML
    private TextField quantitypro;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_bar;
    @FXML
    private Button showbtn;

    @FXML
    private TextField txt_search;
    @FXML
    void showcommant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowCommantBack.fxml"));
            Parent root = loader.load();
            ShowCommantBackController controller = loader.getController();
            Produit selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                controller.initData(selectedProduct);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Comments for " + selectedProduct.getNom());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addPro(ActionEvent event) {
        try {
            ps.ajouter(new Produit(
                    Integer.parseInt(quantitypro.getText()),
                    Double.parseDouble(pricepro.getText()),
                    namepro.getText(),
                    descpro.getText(),
                    img1pro.getText(),
                    img2pro.getText(),
                    img3pro.getText(),
                    categorypro.getText()
            ));
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Product Added");
            successAlert.setContentText("The product was successfully added.");
            successAlert.showAndWait();
            clearFields(); // Clear fields after adding
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please check your number inputs.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setContentText("There was a problem accessing the database. Please try again.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        quantitypro.clear();
        pricepro.clear();
        namepro.clear();
        descpro.clear();
        img1pro.clear();
        img2pro.clear();
        img3pro.clear();
        categorypro.clear();
        loadTableData();
    }

    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<>("img1"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        loadTableData();
        img1pro.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 255) { // Adjust 255 to your desired character limit
                img1pro.setText(newValue.substring(0, 255)); // This truncates any input past the character limit
            }
        });
        img2pro.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 255) { // Adjust 255 to your desired character limit
                img2pro.setText(newValue.substring(0, 255)); // This truncates any input past the character limit
            }
        });
        img3pro.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.length() > 255) { // Adjust 255 to your desired character limit
                img3pro.setText(newValue.substring(0, 255)); // This truncates any input past the character limit
            }
        });

        productTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                namepro.setText(newSelection.getNom());
                pricepro.setText(String.valueOf(newSelection.getPrix()));
                descpro.setText(newSelection.getDescription());
                img1pro.setText(newSelection.getImg1());
                img2pro.setText(newSelection.getImg2());
                img3pro.setText(newSelection.getImg3());
                categorypro.setText(newSelection.getCategorie());
                quantitypro.setText(String.valueOf(newSelection.getQuantity()));
            }

        });

    }

    private void loadTableData() {
        try {
            ArrayList<Produit>produits=new ArrayList<>();
            produits=ps.afficherAll();
            productTable.setItems(FXCollections.observableArrayList(produits));

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void deletepro(ActionEvent event) {
        // Get selected item(s)
        Produit selectedItem = productTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                // Remove selected item from the TableView and the underlying data source
                ps.supprimer(selectedItem); // Pass the selected product object
                loadTableData(); // Reload the table data after deletion
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Database Error");
                alert.setContentText("There was a problem deleting the product. Please try again.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    @FXML
    void updateprod(ActionEvent event) {
        Produit selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                selectedProduct.setNom(namepro.getText());
                selectedProduct.setPrix(Double.parseDouble(pricepro.getText()));
                selectedProduct.setDescription(descpro.getText());
                selectedProduct.setImg1(img1pro.getText());
                selectedProduct.setImg2(img2pro.getText());
                selectedProduct.setImg3(img3pro.getText());
                selectedProduct.setCategorie(categorypro.getText());
                selectedProduct.setQuantity(Integer.parseInt(quantitypro.getText()));

                ps.modifier(selectedProduct);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Product Updated");
                successAlert.setContentText("Product details updated successfully.");
                successAlert.showAndWait();
                loadTableData();
            } catch (NumberFormatException | SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Updating Product");
                alert.setContentText("Error updating product: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Product Selected");
            alert.setContentText("Please select a product to update.");
            alert.showAndWait();
        }
    }
    @FXML
    void clearspace(ActionEvent event) {
        clearFields();
    }
}
