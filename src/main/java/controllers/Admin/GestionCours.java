package controllers.Admin;

import Entity.*;
import Service.*;
import controllers.Cours.UpdateCours;
import controllers.Ressource.UpdateChapter;
import controllers.UpdateCategory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class GestionCours implements Initializable {
    //------------------------
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
    private TextField txt_search;
    //---------------------------
//***********************************************Categorie*********************************************
    @FXML
    private TableColumn<Categorie,String> CatAction;
    @FXML
    private ImageView DImg;
    @FXML
    private Label DNbCours;
    @FXML
    private Label DNbchap;
    @FXML
    private Label Ddescrip;
    @FXML
    private Label Dtitle;
    @FXML
    private TableColumn<Categorie, String> catDescCol;
    @FXML
    private TableColumn<Categorie, String> catTitleCol;
    @FXML
    private Button refresh_btn;
    @FXML
    private TableView<Categorie> showCat;

    @FXML
    void refreshTab(ActionEvent event) {
        updateCatTable();
    }
    @FXML
    void OpenAdd(MouseEvent event) {
        try {
            Parent addPageRoot = FXMLLoader.load(getClass().getResource("/AddCategory.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(addPageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("New Category");
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void updateCatTable(){
        showCat.setFixedCellSize(40);
        Categorie defaultItem = new Categorie();
        catTitleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        catDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        try {
            ObservableList<Categorie> categories =new CategorieService().getAll();
            showCat.setItems(categories);
            defaultItem = categories.get(0) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        CatAction.setCellValueFactory(new PropertyValueFactory<>(""));
        CatAction.setCellFactory(param -> new TableCell() {
            FontAwesomeIconView deleteButton = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            FontAwesomeIconView updateButton = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            {
                deleteButton.setOnMouseClicked(event -> {
                    Categorie category = (Categorie) getTableRow().getItem();
                    deleteCategory(category);
                });
                updateButton.setOnMouseClicked(event -> {
                    Categorie category = (Categorie) getTableRow().getItem();
                    updateCategory(category);
                });
            }
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(15);
                    buttonsContainer.setPadding(new Insets(5, 8, 3, 8));
                    buttonsContainer.getChildren().addAll(deleteButton, updateButton);
                    deleteButton.setSize("25px");
                    updateButton.setSize("25px");
                    setGraphic(buttonsContainer);
                }
            }
        });
        if (defaultItem != null) {
            Dtitle.setText(defaultItem.getTitre());
            Ddescrip.setText(defaultItem.getDescription());
            Image CatImg = new Image("file:/" + defaultItem.getImage());
            DImg.setImage(CatImg);
            DNbchap.setText(String.valueOf(defaultItem.getNbr_chapitre()));
            DNbCours.setText(String.valueOf(defaultItem.getNbr_cours()));
        }
    }
    private void updateCategory(Categorie category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCategory.fxml"));
            Parent updatePageRoot = loader.load();
            UpdateCategory updateCategory= loader.getController();
            updateCategory.fetchData(category);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(updatePageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("Update Category");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteCategory(Categorie category) {
        try {
            int categoryId = category.getId();
            CategorieService categorieService = new CategorieService();
            categorieService.delete(categoryId);
            showCat.getItems().remove(category);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //*****************************************************Cours**************************************************

    @FXML
    private Button refreshC_btn;
    @FXML
    private TableView<Cours> showCours;
    @FXML
    private TableColumn<Cours, String> coursTitleCol;
    @FXML
    private TableColumn<Cours, String> coursDescCol;
    @FXML
    private TableColumn<Cours, String> coursStatusCol;
    @FXML
    private TableColumn<Cours,String> CoursAction;
    @FXML
    private Label DCtitle;
    @FXML
    private Label Dperiode;
    @FXML
    private Label DCdescrip;
    @FXML
    private Label DCNbchap;

    @FXML
    void refreshCTab(ActionEvent event){refreshCoursTab();}
    @FXML
    void OpenAddCours(MouseEvent event) {
        try {
            Parent addCPageRoot = FXMLLoader.load(getClass().getResource("/AddCours.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(addCPageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("New Course");
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    public void refreshCoursTab(){
        showCours.setFixedCellSize(40);
        Cours defaultCours = new Cours();
        coursTitleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        coursDescCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        coursStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        try {
            ObservableList<Cours> cours =new CoursService().getAll();
            showCours.setItems(cours);
            defaultCours = cours.get(0) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        CoursAction.setCellValueFactory(new PropertyValueFactory<>(""));
        CoursAction.setCellFactory(param -> new TableCell() {
            FontAwesomeIconView Cdel_btn = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            FontAwesomeIconView Cup_btn = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            {
                Cdel_btn.setOnMouseClicked(event -> {
                    Cours cours1 = (Cours) getTableRow().getItem();
                    deleteCours(cours1);
                });
                Cup_btn.setOnMouseClicked(event -> {
                    Cours cours1 = (Cours) getTableRow().getItem();
                    updateCours(cours1);
                });
            }
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(15);
                    buttonsContainer.setPadding(new Insets(5, 8, 3, 8));
                    buttonsContainer.getChildren().addAll(Cdel_btn, Cup_btn);
                    Cdel_btn.setSize("25px");
                    Cup_btn.setSize("25px");
                    setGraphic(buttonsContainer);
                }
            }
        });
        if (defaultCours != null) {
            DCtitle.setText(defaultCours.getTitre());
            DCdescrip.setText(defaultCours.getDescription());
            DCNbchap.setText(String.valueOf(defaultCours.getNbrchapitre()));
            Dperiode.setText(String.valueOf(defaultCours.getDuree()));
        }
    }
    private void updateCours(Cours cours) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateCours.fxml"));
            Parent updatePageRoot = loader.load();
            UpdateCours updateCours= loader.getController();
            updateCours.fetchCData(cours);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(updatePageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("Update Course");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteCours(Cours cours) {
        try {
            int coursId = cours.getId();
            CoursService coursService = new CoursService();
            coursService.delete(coursId);
            showCours.getItems().remove(cours);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //*************************************************CHAPTER********************************************************
    @FXML
    private Button refreshChaop_btn;
    @FXML
    private TableColumn<Ressource,String> chapFile;
    @FXML
    private TableColumn<Ressource, String> chapTitleCol;
    @FXML
    private TableColumn<Ressource, String> chapType;
    @FXML
    private TableColumn<Ressource, String> ChapAction;
    @FXML
    private Pane Chap_contenu;
    @FXML
    private Label DChaptitle;
    @FXML
    private TableView<Ressource> showChap;
    @FXML
    void OpenAddChap(MouseEvent event) {
        try {
            Parent addChapPageRoot = FXMLLoader.load(getClass().getResource("/AddChapter.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(addChapPageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("New Chapter");
            stage.show();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void refreshChapTab(ActionEvent event) {updateChapTab();}
    public void updateChapTab(){
        showChap.setFixedCellSize(40);
        Ressource defaultChap = new Ressource();
        chapTitleCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        chapFile.setCellValueFactory(new PropertyValueFactory<>("url"));
        chapType.setCellValueFactory(new PropertyValueFactory<>("type"));
        try {
            ObservableList<Ressource> chapters =new RessourceService().getAll();
            showChap.setItems(chapters);
            defaultChap = chapters.get(0) ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        ChapAction.setCellValueFactory(new PropertyValueFactory<>(""));
        ChapAction.setCellFactory(param -> new TableCell() {
            FontAwesomeIconView Chapdel_btn = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
            FontAwesomeIconView Chapup_btn = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
            {
                Chapdel_btn.setOnMouseClicked(event -> {
                    Ressource ressource = (Ressource) getTableRow().getItem();
                    deleteChap(ressource);
                });
                Chapup_btn.setOnMouseClicked(event -> {
                    Ressource ressource = (Ressource) getTableRow().getItem();
                    updateChap(ressource);
                });
            }
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsContainer = new HBox(15);
                    buttonsContainer.setPadding(new Insets(5, 8, 3, 8));
                    buttonsContainer.getChildren().addAll(Chapdel_btn, Chapup_btn);
                    Chapdel_btn.setSize("25px");
                    Chapup_btn.setSize("25px");
                    setGraphic(buttonsContainer);
                }
            }
        });
        if (defaultChap != null) {
            DChaptitle.setText(defaultChap.getTitre());
            // tansech fazet preview
        }
    }
    private void updateChap(Ressource ressource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateChapter.fxml"));
            Parent updatePageRoot = loader.load();
            UpdateChapter updateChapter= loader.getController();
            updateChapter.fetchChapData(ressource);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UTILITY);
            Scene newPageScene = new Scene(updatePageRoot,650,350);
            stage.setScene(newPageScene);
            stage.setTitle("Update Chapter");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deleteChap(Ressource ressource) {
        try {
            int chapId = ressource.getId();
            CoursService coursService = new CoursService();
            coursService.delete(chapId);
            showChap.getItems().remove(ressource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//________________________________________________________________________________________________________________________
    public void initialize (URL url, ResourceBundle resourceBundle) {

        //-----------------------------------CATEGORY-----------------------------------------------------------
        FontAwesomeIconView refreshButton = new FontAwesomeIconView(FontAwesomeIcon.REFRESH,"20");
        refresh_btn.setGraphic(refreshButton);
        updateCatTable();
        showCat.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Dtitle.setText(newSelection.getTitre());
                Ddescrip.setText(newSelection.getDescription());
                Image DCatImg = new Image("file:/"+newSelection.getImage());
                DImg.setImage(DCatImg);
                DNbchap.setText(String.valueOf(newSelection.getNbr_chapitre()));
                DNbCours.setText(String.valueOf(newSelection.getNbr_cours()));
            } else {
                Dtitle.setText(" ");
                Ddescrip.setText(" ");
                DNbchap.setText(" ");
                DNbCours.setText(" ");
            }
        });
        //---------------------------------------COURS----------------------------------------------------------
        FontAwesomeIconView refreshCBtn = new FontAwesomeIconView(FontAwesomeIcon.REFRESH,"20");
        refreshC_btn.setGraphic(refreshButton);
        refreshCoursTab();
        showCours.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                DCtitle.setText(newSelection.getTitre());
                DCdescrip.setText(newSelection.getDescription());
                DCNbchap.setText(String.valueOf(newSelection.getNbrchapitre()));
                Dperiode.setText(String.valueOf(newSelection.getDuree()));
            } else {
                DCtitle.setText(" ");
                DCdescrip.setText(" ");
                DCNbchap.setText(" ");
                Dperiode.setText(" ");
            }
        });
        //-------------------------------------------CHAPTERS------------------------------------------------------
        FontAwesomeIconView refreshchap = new FontAwesomeIconView(FontAwesomeIcon.REFRESH,"20");
        refreshChaop_btn.setGraphic(refreshchap);
        updateChapTab();
        showChap.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                DChaptitle.setText(newSelection.getTitre());
               //tansech 5idma
            } else {
                DChaptitle.setText(" ");
            }
        });
    }







}

   

