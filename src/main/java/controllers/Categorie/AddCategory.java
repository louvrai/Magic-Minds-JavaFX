package controllers.Categorie;

import Entity.Categorie;
import Entity.Cours;
import Service.CategorieService;
import controllers.AlertClass;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

public class AddCategory {

    @FXML
    private TextArea addCatDescrip;

    @FXML
    private ImageView addCatImg;

    @FXML
    private TextField addCatTitle;
    @FXML
    private Label ECDesc;

    @FXML
    private Label ECattitre;

    @FXML
    private Button addcat;
    @FXML

    private AnchorPane addWind;

    @FXML
    private Button upload;
    CategorieService categorieService = new CategorieService();
    AlertClass alertClass=new AlertClass();
    String url ;
    File selectedFile = new File("C:\\");
    File UploadDirectory = new File("C:/Users/HBY/IdeaProjects/test2/src/main/resources/UploadImage");
    File destinationFile = new File("C:\\");
    @FXML
    public void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files","*.jpg","*.png")
        );
        selectedFile = fileChooser.showOpenDialog(upload.getScene().getWindow());
        if (selectedFile != null){
                String pathName =selectedFile.getAbsolutePath().replace("\\","/");
                System.out.println(pathName);
                Image image = new Image("file:/" +pathName);
                addCatImg.setImage(image);
                destinationFile=new File(UploadDirectory,selectedFile.getName());
                url = UploadDirectory.getAbsolutePath().replace("\\", "/");
                url += "/" + selectedFile.getName();
                System.out.println(url);
        }
    }
    @FXML
    void addCat(MouseEvent event) {
        Categorie cat = new Categorie();
        try {
            Files.copy(selectedFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
        if (isValid()==true){
            cat.setTitre(addCatTitle.getText());
            cat.setDescription(addCatDescrip.getText());
            cat.setImage(url);
            categorieService.insert(cat);
            alertClass.showSAlert("Addition Success", "The category "+cat.getTitre()+" was successfully added");
            ECattitre.setText("");
        }else {
        alertClass.showSAlert("Error","An error occurred");
         }
        Stage closestage = (Stage) addcat.getScene().getWindow();
        closestage.close();
    }
    public boolean isValid(){
        try {
            ObservableList<Categorie> categories=categorieService.getAll();
            for (Categorie cat : categories) {
                if (addCatTitle.getText().equals(cat.getTitre())){
                    ECattitre.setText("This title already exist");
                    return false ;
                }
                else {
                    ECattitre.setText("");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true ;
    }



}
