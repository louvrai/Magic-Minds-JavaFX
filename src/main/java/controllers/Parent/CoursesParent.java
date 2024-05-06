package controllers.Parent;

import Entity.Categorie;
import Entity.Cours;
import Entity.Ressource;
import Service.CategorieService;
import Service.CoursService;
import Service.RessourceService;
import controllers.Cards;
import controllers.Outil;
import controllers.PreviewFile;
import controllers.PreviewPDF;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CoursesParent implements Initializable {

    @FXML
    private VBox Containe;

    @FXML
    private Pane Content;

    @FXML
    private HBox bar;

    @FXML
    private VBox editable;

    @FXML
    private Pane pageContainer;
    Cards card=new Cards();
    Outil outil=new Outil();
    CategorieService categorieService=new CategorieService();
    CoursService coursService= new CoursService();
    RessourceService ressourceService= new RessourceService();
    Pagination pagination=new Pagination();
    Categorie catDetail=new Categorie();
    Cours coursDetail=new Cours();
    Ressource ressourceDetail=new Ressource();
    public void pagination(){
        //pagination
        ObservableList<Categorie> CatList= categorieService.getAll();
        int nbr_page=0;
        if (CatList.size()%6==0){
            nbr_page = CatList.size()/6;
        }else {
            nbr_page = (CatList.size()/ 6) + 1;
        }
        pagination.setPrefSize(764,490);
        pagination.setPageCount(nbr_page);
        pagination.setPageFactory(this::getPage);
        pageContainer.getChildren().add(pagination);
    }
    public Pane getPage(int pageIndex){
        ObservableList<ObservableList<Categorie>> allpages=sixBysix();
        ObservableList<Categorie> pages= allpages.get(pageIndex);
        Pane page=card.PageContainer(490);
        VBox table=card.table(490);
        HBox line=card.line(220,41);
        int i=0;
        for (Categorie c : pages){
            Pane carte=card.PCard(c);
            carte.setUserData(c);
            carte.setOnMouseClicked(this::cardClicked);
            line.getChildren().add(carte);
            i++;
            if (i % 3==0){
                table.getChildren().add(line);
                line=new HBox();
                line=card.line(220,41);
            }
        }
        if (!line.getChildren().isEmpty()){
            table.getChildren().add(line);
        }
        page.getChildren().add(table);
        return page ;
    }
    public ObservableList<ObservableList<Categorie>> sixBysix(){
        ObservableList<ObservableList<Categorie>> pages= FXCollections.observableArrayList();
        ObservableList<Categorie> CatList= categorieService.getAll();
        for (int j = 0; j < CatList.size() ; j += 6) {
            int endIndex = Math.min(j + 6, CatList.size());  // Ensure endIndex does not exceed CatList size
            ObservableList<Categorie> trimedList = FXCollections.observableArrayList(
                    CatList.subList(j, endIndex)
            );
            pages.add(trimedList);
        }
        return pages;
    }
    private void cardClicked(MouseEvent mouseEvent) {
        Pane carte = (Pane) mouseEvent.getSource();
        catDetail = (Categorie) carte.getUserData();
        getCourses();

    }
    public void getCourses(){
        pageContainer.getChildren().clear();
        bar.getChildren().clear();
        //creating the bar
        Button back =card.back_btn();
        Label head=card.heading("Courses List");
        bar.getChildren().addAll(back,head);
        back.setOnMouseClicked(this::goBack);
        VBox DandC=this.showCours(catDetail);
        pageContainer.getChildren().add(DandC);
    }
    private VBox showCours(Categorie catDetail){
        VBox DandC=new VBox();

        //categorie details
        Pane CatD=card.CatDP();
        HBox det=card.CatDe(catDetail);
        CatD.getChildren().add(det);


        //affichage des cours:
        List<Cours> CoursList=coursService.getCoursesByCat(catDetail.getId());
        Pane coursesList= new Pane();
        VBox Ctable=card.table(340);
        HBox Cline=card.line(120,0);
        int i=0;
        for (Cours c :CoursList){
            Pane cardC=card.courCard(c);
            Cline.getChildren().add(cardC);
            i++;
            if (i % 3==0){
                Ctable.getChildren().add(Cline);
                Cline=new HBox();
                Cline=card.line(120,0);
            }
        }
        if (!Cline.getChildren().isEmpty()){
            Ctable.getChildren().add(Cline);
        }
        coursesList.getChildren().add(Ctable);
        DandC.getChildren().addAll(CatD,coursesList);
        return DandC ;
    }
    public void backf(){
        pageContainer.getChildren().clear();
        bar.getChildren().clear();
        HBox bar1=card.HEbar();
        bar=bar1;
        Content.getChildren().add(bar);
        pagination();
    }
    private void goBack(MouseEvent mouseEvent) {
        backf();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pagination();
    }

}
