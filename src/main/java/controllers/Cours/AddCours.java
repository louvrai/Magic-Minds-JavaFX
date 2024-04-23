package controllers.Cours;

import Entity.Categorie;
import Entity.Cours;
import controllers.AlertClass;
import Service.CategorieService;
import Service.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCours implements Initializable {

    @FXML
    private AnchorPane AddCourScene;
    @FXML
    private Label ECChap;

    @FXML
    private Label ECDesc;

    @FXML
    private Label ECtitre;

    @FXML
    private Button InsertCour_btn;

    @FXML
    private ChoiceBox<String> addCCat;

    @FXML
    private Spinner<Integer> addCChap;

    @FXML
    private TextArea addCDescrip;

    @FXML
    private Spinner<Integer> addCPer;

    @FXML
    private TextField addCTiltle;

    @FXML
    private Button goBack_btn;
    CategorieService categorieService=new CategorieService() ;
    CoursService coursService=new CoursService();
    ObservableList<Cours> courses = FXCollections.observableArrayList();
    AlertClass alertClass =new AlertClass();

    @FXML
    void addCours(MouseEvent event) throws SQLException {
        if (isValid()==true) {
            Cours c = new Cours();
            Categorie cat = new Categorie();
            c.setTitre(addCTiltle.getText());
            c.setDescription(addCDescrip.getText());
            c.setDuree(addCPer.getValue());
            c.setNb_chapitre(addCChap.getValue());
            String catName=addCCat.getValue();
            cat=categorieService.getbyId(categorieService.getCatId(catName));
            c.setCategorie_id(cat.getId());
            c.setStatus("Valid");
            coursService.insert(c);
            alertClass.showEAlert("Addition Success", "The course "+c.getTitre()+" was successfully added");
            ECtitre.setText("");
            ECChap.setText("");
        }else {
            alertClass.showSAlert("Error","An error occurred");
        }

    }
    public boolean isValid(){
        Cours c = new Cours();
        Categorie cat = new Categorie();
        try {
            for (Cours cours : courses = coursService.getAll()) {
                if (addCTiltle.getText().equals(cours.getTitre())){
                    ECtitre.setText("This title already exist");
                    return false ;
                }
                else {
                    ECtitre.setText("");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (addCChap.getValue()<= addCPer.getValue()){
            ECChap.setText("");
        }else {
            ECChap.setText(" the number of chapters can't be more then the period");
            return false;
        }
        return true ;
    }
    @FXML
    void goBack(MouseEvent event) {
        Stage closestage = (Stage) goBack_btn.getScene().getWindow();
        closestage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addCCat.setItems(categorieService.getCatNames());
        addCCat.setValue(categorieService.getCatNames().get(0));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20);
        SpinnerValueFactory<Integer> valueCHAPFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20);
        addCPer.setValueFactory(valueFactory);
        addCChap.setValueFactory(valueCHAPFactory);
    }

}
