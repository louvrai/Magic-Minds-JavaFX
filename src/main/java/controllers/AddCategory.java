package controllers;

import Entity.Categorie;
import Service.CategorieService;
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

public class AddCategory {

    @FXML
    private TextArea addCatDescrip;

    @FXML
    private ImageView addCatImg;

    @FXML
    private TextField addCatTitle;

    @FXML
    private Button addcat;
    @FXML

    private AnchorPane addWind;

    @FXML
    private Button upload;
    CategorieService categorieService = new CategorieService();
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
        cat.setTitre(addCatTitle.getText());
        cat.setDescription(addCatDescrip.getText());
        cat.setImage(url);
        try {
            Files.copy(selectedFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
        categorieService.insert(cat);
        Stage closestage = (Stage) addcat.getScene().getWindow();
        closestage.close();
    }




}
