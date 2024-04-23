package controllers.Cours;

import Entity.Categorie;
import Entity.Cours;
import Service.CategorieService;
import Service.CoursService;
import controllers.AlertClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateCours implements Initializable {
    @FXML
    private Label ECUPChap;

    @FXML
    private Label ECUPDesc;

    @FXML
    private Label ECUPtitre;

    @FXML
    private ChoiceBox<String> upCCat;

    @FXML
    private Spinner<Integer> upCChap;

    @FXML
    private TextArea upCDescrip;

    @FXML
    private Spinner<Integer> upCPer;

    @FXML
    private TextField upCTiltle;

    @FXML
    private Button upCour_btn;

    @FXML
    private Button upgoBack_btn;
    CoursService coursService =new CoursService();
    Cours course =new Cours();
    Categorie categorie = new Categorie();
    CategorieService categorieService=new CategorieService();
    ObservableList<Cours> courses = FXCollections.observableArrayList();
    AlertClass alertClass =new AlertClass();
    @FXML
    void updateCours(MouseEvent event) {
        if (isValid()==true) {
            Cours c = new Cours();
            Categorie cat = new Categorie();
            c.setTitre(upCTiltle.getText());
            c.setDescription(upCDescrip.getText());
            c.setDuree(upCPer.getValue());
            c.setNb_chapitre(upCChap.getValue());
            c.setStatus("Valid");
            String catName = upCCat.getValue();
            if (catName!=null) {
                cat = categorieService.getbyId(categorieService.getCatId(catName));
                c.setCategorie_id(cat.getId());
            }
            c.setCategorie_id(course.getCategorie_id());
            System.out.println(c);
            coursService.update(course.getId(),c);
            alertClass.showEAlert("Update Success", "The course "+c.getTitre()+" was successfully updated");
            ECUPtitre.setText("");
            ECUPChap.setText("");
        }else {
            alertClass.showSAlert("Error","An error occurred");
        }
        System.out.println(course);

    }
    public boolean isValid(){
        Cours c = new Cours();
        Categorie cat = new Categorie();
        try {
            for (Cours cours : courses = coursService.getAll()) {
                if (upCTiltle.getText().equals(cours.getTitre()) && !upCTiltle.getText().equals(course.getTitre())){
                    ECUPtitre.setText("This title already exist");
                    return false ;
                }
                else {
                    ECUPtitre.setText("");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (upCChap.getValue()<= upCPer.getValue()){
            ECUPChap.setText("");
        }else {
            ECUPChap.setText("The number of chapters can't be more then the period");
            return false;
        }
        return true ;
    }
    @FXML
    void upgoBack(MouseEvent event) {
        Stage closestage = (Stage) upgoBack_btn.getScene().getWindow();
        closestage.close();
    }
    public void fetchCData(Cours cours) {
        course=cours;
        upCTiltle.setText(cours.getTitre());
        upCDescrip.setText(cours.getDescription());
        categorie=categorieService.getbyId(cours.getCategorie_id());
        upCCat.setValue(categorie.getTitre());
        int S1=cours.getDuree();
        int S2=cours.getNb_chapitre();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,S1);
        SpinnerValueFactory<Integer> valueCHAPFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20,S2);
        upCPer.setValueFactory(valueFactory);
        upCChap.setValueFactory(valueCHAPFactory);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upCCat.setItems(categorieService.getCatNames());
    }
}


