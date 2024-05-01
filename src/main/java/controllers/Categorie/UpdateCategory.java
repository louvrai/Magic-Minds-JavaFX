package controllers.Categorie;

import Entity.Categorie;
import Service.CategorieService;
import controllers.AlertClass;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;

public class UpdateCategory {
    @FXML
    private TextArea updateCatDescrip;

    @FXML
    private ImageView updateCatImg;

    @FXML
    private TextField updateCatTitle;

    @FXML
    private Label ECDescUP;

    @FXML
    private Label ECattitreUP;

    @FXML
    private Label EimageUP;

    @FXML
    private Button updatecat;

    @FXML
    private Button updateupload;

    @FXML
    private Button backCat;

    String url="Please choose an image" ;
    String same_url ;
    File selectedFile = new File("C:\\");
    File UploadDirectory = new File("C:/Users/HBY/IdeaProjects/test2/src/main/resources/UploadImage");
    File destinationFile = new File("C:\\");
    CategorieService categorieService =new CategorieService();
    Categorie categorie;
    AlertClass alertClass=new AlertClass();

    public void fetchData(Categorie cat) {
        categorie=cat ;
        updateCatDescrip.setText(cat.getDescription());
        updateCatTitle.setText(cat.getTitre());
        Image img=new Image("file:/"+cat.getImage());
        updateCatImg.setImage(img);
        same_url=cat.getImage();

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
            Image image = new Image("file:/" +pathName);
            updateCatImg.setImage(image);
            destinationFile=new File(UploadDirectory,selectedFile.getName());
            url = UploadDirectory.getAbsolutePath().replace("\\", "/");
            url += "/" + selectedFile.getName();
        }
    }
    @FXML
    void updateCat(MouseEvent event) {
        Categorie cat = new Categorie();
        if (isValid()) {
            try {
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            cat.setTitre(updateCatTitle.getText());
            cat.setDescription(updateCatDescrip.getText());
            if (!url.equals("Please choose an image")){
                cat.setImage(url);
            }
            cat.setImage(categorie.getImage());
            categorieService.update(categorie.getId(),cat);
            alertClass.showSAlert("Update Success", "The category "+cat.getTitre()+" was successfully updated");
            Stage stage = (Stage) updateupload.getScene().getWindow();
            stage.close();
        }
    }
    public boolean isValid(){
        try {
            ObservableList<Categorie> categories = categorieService.getAll();
            String title = updateCatTitle.getText();
            String descrip = updateCatDescrip.getText();
            System.out.println(same_url);
            ECattitreUP.setText("");
            ECDescUP.setText("");
            if (title == null || title.isEmpty()) {
                ECattitreUP.setText("The title can't be empty");
                return false;
            }
            if (title.length() < 3 || title.length() > 20) {
                ECattitreUP.setText("The title is either too long or too short");
                return false;
            }
            for (Categorie cat : categories) {
                if (title.equals(cat.getTitre()) && !title.equals(categorie.getTitre())) {
                    ECattitreUP.setText("This title already exist");
                    return false;
                }
            }
            if (descrip == null || descrip.isEmpty()) {
                ECDescUP.setText("The description can't be empty");
                return false;
            }
            if (descrip.length() < 10 || descrip.length() > 150) {
                ECDescUP.setText("The description is either too long or too short");
                return false;
            }
            return true ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void goBack(MouseEvent event) {
        Stage closestage = (Stage) backCat.getScene().getWindow();
        closestage.close();
    }

}
