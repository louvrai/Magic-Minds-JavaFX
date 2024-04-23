package controllers;

import Entity.Categorie;
import Service.CategorieService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UpdateCategory {
    @FXML
    private TextArea updateCatDescrip;

    @FXML
    private ImageView updateCatImg;

    @FXML
    private TextField updateCatTitle;

    @FXML
    private Button updatecat;

    @FXML
    private Button updateupload;

    String url ;
    File selectedFile = new File("C:\\");
    File UploadDirectory = new File("C:/Users/HBY/IdeaProjects/test2/src/main/resources/UploadImage");
    File destinationFile = new File("C:\\");
    CategorieService categorieService =new CategorieService();
    Categorie categorie;

    public void fetchData(Categorie cat) {
        categorie=cat ;
        updateCatDescrip.setText(cat.getDescription());
        updateCatTitle.setText(cat.getTitre());
        Image img=new Image("file:/"+cat.getImage());
        updateCatImg.setImage(img);

    }

    @FXML
    void uploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Image","*.jpg"),
                new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                new FileChooser.ExtensionFilter("All image files","*.jpg","*.png")
        );
        selectedFile = fileChooser.showOpenDialog(updateupload.getScene().getWindow());
        if (selectedFile != null){
            String pathName =selectedFile.getAbsolutePath().replace("\\","/");
            System.out.println(pathName);
            Image image = new Image("file:/" +pathName);
            updateCatImg.setImage(image);
            destinationFile=new File(UploadDirectory,selectedFile.getName());
            url = UploadDirectory.getAbsolutePath().replace("\\", "/");
            url += "/" + selectedFile.getName();
            System.out.println(url);
        }
    }
    @FXML
    void updateCat(MouseEvent event) {
        Categorie cat = new Categorie();
        cat.setTitre(updateCatTitle.getText());
        cat.setDescription(updateCatDescrip.getText());
        cat.setImage(url);
        System.out.println(cat);
        System.out.println(categorie.getId());
        try {
            Files.copy(selectedFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
        categorieService.update(categorie.getId(),cat);
        Stage closestage = (Stage) updatecat.getScene().getWindow();
        closestage.close();


    }


}
