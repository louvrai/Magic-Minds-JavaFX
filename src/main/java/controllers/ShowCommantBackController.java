package controllers;
import Entities.Comment;
import Entities.Produit;
import Services.CommentCRUD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShowCommantBackController {

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
    private TableView<Comment> commanttable;

    @FXML
    private TableColumn<Comment, LocalDate> datecommant;

    @FXML
    private Pane inner_pane;

    @FXML
    private TableColumn<Comment,String> nameuser;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_bar;

    @FXML
    private TextField txt_search;

    @FXML
    private TableColumn<Comment,String> usercommant;
    public void initData(Produit product) {
        loadComments(product.getId());
    }

    private void loadComments(int productId) {
        CommentCRUD commentCRUD = new CommentCRUD();
        try {
            ArrayList<Comment> comments = commentCRUD.afficherAllByProductId(productId);
            commanttable.setItems(FXCollections.observableArrayList(comments));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        datecommant.setCellValueFactory(new PropertyValueFactory<>("date"));
       nameuser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        usercommant.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    @FXML
    void clearspace(ActionEvent event) {

    }

    @FXML
    void deletepro(ActionEvent event) {

    }

}
