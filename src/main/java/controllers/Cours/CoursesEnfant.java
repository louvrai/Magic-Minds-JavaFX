package controllers.Cours;

import Entity.Categorie;
import Entity.Cours;
import Service.CategorieService;
import Service.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CoursesEnfant implements Initializable {

    @FXML
    private VBox Containe;

    @FXML
    private VBox editable;
    CategorieService categorieService = new CategorieService();
    CoursService coursService=new CoursService();
    public void ShowCat(){
        Containe.setPadding(new Insets(7,7,7,7));
        Containe.setSpacing(15);
        HBox hBox=new HBox();
        hBox.setSpacing(15);
        try {
            ObservableList<Categorie> CatList = FXCollections.observableArrayList();
            CatList=categorieService.getAll();
            int i=0;
            for (Categorie categorie : CatList){
                Pane card = new Pane();
                card.getStylesheets().add("HStyle.css");
                card.getStyleClass().add("pane");
                HBox hBox1=new HBox();
                hBox1.setPadding(new Insets(10,10,10,10));
                hBox1.setSpacing(10);
                ImageView imageView=new ImageView();
                imageView.setFitWidth(150);
                imageView.setFitHeight(150);
                javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(
                        imageView.getFitWidth(), imageView.getFitHeight()
                );
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                imageView.setClip(clip);
                Image img = new Image("file:/"+categorie.getImage());
                imageView.setImage(img);
                Label title=new Label();
                title.getStylesheets().add("HStyle.css");
                title.getStyleClass().add("label_Title");
                title.setText(categorie.getTitre());
                Label descrip=new Label();
                descrip.setWrapText(true);
                descrip.getStylesheets().add("HStyle.css");
                descrip.getStyleClass().add("label_Desc");
                descrip.setAlignment(Pos.TOP_LEFT);
                descrip.setText(categorie.getDescription());
                hBox1.getChildren().add(imageView);
                VBox vBox =new VBox();
                vBox.setSpacing(6);
                vBox.getChildren().add(title);
                vBox.getChildren().add(descrip);
                hBox1.getChildren().add(vBox);
                card.getChildren().add(hBox1);

                card.setUserData(categorie);
                card.setOnMouseClicked(this::cardClicked);

                hBox.getChildren().add(card);
                i++;
                if (i % 2==0){
                    Containe.getChildren().add(hBox);
                    hBox=new HBox();
                    hBox.setSpacing(15);
                }
            }
            if (!hBox.getChildren().isEmpty()){
                Containe.getChildren().add(hBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ShowCat();
    }

    private void cardClicked(MouseEvent mouseEvent) {
        Pane card = (Pane) mouseEvent.getSource();
        Categorie cat = (Categorie) card.getUserData();
        Containe.getChildren().clear();
        Containe.setAlignment(Pos.TOP_LEFT);
        Containe.setPadding(new Insets(7, 7, 7, 7));
        Containe.setSpacing(15);

        Button back = new Button("Back");
        back.getStylesheets().add("Style.css");
        back.getStyleClass().add("fron_btn");
        back.setPrefWidth(90);
        Containe.getChildren().add(back);
        back.setOnMouseClicked(this::goBack);

        HBox hBox = new HBox();
        Pane catCard = new Pane();
        catCard.getStylesheets().add("HStyle.css");
        catCard.getStyleClass().add("pane_det");

        HBox hBox1 = new HBox();
        hBox1.setPadding(new Insets(10, 10, 10, 10));
        hBox1.setSpacing(10);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);
        Image img = new Image("file:/" + cat.getImage());
        imageView.setImage(img);
        hBox1.getChildren().add(imageView);

        Label title = new Label();
        title.getStylesheets().add("HStyle.css");
        title.getStyleClass().add("label_Title");
        title.setText(cat.getTitre());

        Label descrip = new Label();
        descrip.setWrapText(true);
        descrip.getStylesheets().add("HStyle.css");
        descrip.getStyleClass().add("label_Desc1");
        descrip.setAlignment(Pos.TOP_LEFT);
        descrip.setText(cat.getDescription());

        VBox vBox = new VBox();
        vBox.setSpacing(6);
        vBox.getChildren().add(title);
        vBox.getChildren().add(descrip);
        hBox1.getChildren().add(vBox);
        catCard.getChildren().add(hBox1);


        VBox CourBox=new VBox();
        CourBox.setSpacing(15);

        HBox Hcourses = new HBox();
        Hcourses.setSpacing(15);
        List<Cours> coursList;
        coursList = coursService.getCoursesByCat(cat.getId());
        ObservableList<Cours> CoursList = FXCollections.observableArrayList(coursList);

        System.out.println(CoursList);

        int i = 0;
        for (Cours c : CoursList) {
            Pane Courscard = new Pane();
            Courscard.getStylesheets().add("HStyle.css");
            Courscard.getStyleClass().add("pane");
            Courscard.setPrefSize(360,130);

            Label Ctitle = new Label();
            Ctitle.getStylesheets().add("HStyle.css");
            Ctitle.getStyleClass().add("label_Title");
            Ctitle.setText(c.getTitre());

            Label Cdescrip = new Label();
            Cdescrip.setWrapText(true);
            Cdescrip.getStylesheets().add("HStyle.css");
            Cdescrip.getStyleClass().add("label_DescC");
            Cdescrip.setAlignment(Pos.CENTER);
            Cdescrip.setText(c.getDescription());

            Label period = new Label();
            period.getStyleClass().add("label_Title");
            period.setText(String.valueOf(c.getDuree()));

            Label chapters = new Label();
            chapters.getStyleClass().add("label_Title");
            chapters.setText(String.valueOf(c.getNb_chapitre()));

            VBox vBox1 = new VBox();
            vBox1.setPadding(new Insets(10, 10, 10, 10));
            vBox1.setAlignment(Pos.CENTER);

            vBox1.getChildren().add(Ctitle);
            vBox1.getChildren().add(period);
            vBox1.getChildren().add(chapters);
            vBox1.getChildren().add(Cdescrip);

            Courscard.getChildren().add(vBox1);

            Courscard.setUserData(c);


            Hcourses.getChildren().add(Courscard);
            i++;
            if (i % 2 == 0) {
                CourBox.getChildren().add(Hcourses);
                Hcourses = new HBox();
                Hcourses.setSpacing(15);
            }
        }
        if (!Hcourses.getChildren().isEmpty()) {
            CourBox.getChildren().add(Hcourses);
        }
        Containe.getChildren().add(catCard);
        ScrollPane scrollPane = new ScrollPane(CourBox);
        scrollPane.setPrefHeight(400);
        Containe.getChildren().add(scrollPane);

    }

    private void goBack(MouseEvent mouseEvent) {
        Containe.getChildren().clear();
        ShowCat();
    }
}
