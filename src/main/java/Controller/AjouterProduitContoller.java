package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AjouterProduitContoller {

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
    private TableColumn<?, ?> categoryColumn;

    @FXML
    private TextField categorypro;

    @FXML
    private TableColumn<?, ?> descColumn;

    @FXML
    private TextField descpro;

    @FXML
    private TextField img1pro;

    @FXML
    private TextField img2pro;

    @FXML
    private TextField img3pro;

    @FXML
    private TableColumn<?, ?> imgColumn;

    @FXML
    private Pane inner_pane;

    @FXML
    private Button mc;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TextField namepro;

    @FXML
    private TableColumn<?, ?> priceColumn;

    @FXML
    private TextField pricepro;

    @FXML
    private TableView<?> productTable;

    @FXML
    private TableColumn<?, ?> quantityColumn;

    @FXML
    private TextField quantitypro;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_bar;

    @FXML
    private TextField txt_search;

    @FXML
    void addPro(ActionEvent event) {

    }

    @FXML
    void clearspace(ActionEvent event) {

    }

    @FXML
    void deletepro(ActionEvent event) {

    }

    @FXML
    void updateprod(ActionEvent event) {

    }

}