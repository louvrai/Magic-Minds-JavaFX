package controllers.Ressource;

import Entity.Categorie;
import Entity.Cours;
import Entity.Ressource;
import Service.CoursService;
import Service.RessourceService;
import controllers.AlertClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddChapter implements Initializable {

    @FXML
    private Label ECtitre;

    @FXML
    private Button InsertChap;

    @FXML
    private TextField addChapTiltle;

    @FXML
    private ChoiceBox<String> addChapType;
    @FXML
    private ChoiceBox<String> addChapCour;

    @FXML
    private AnchorPane addchapteranchor;

    @FXML
    private Button back;

    @FXML
    private Label displayUrl;

    @FXML
    private Button preview;

    @FXML
    private Button uploadFile;
    File selectedFile = new File("C:\\");
    File UploadDirectory = new File("C:/Users/HBY/IdeaProjects/test2/src/main/resources/UploadedPDF");
    File destinationFile = new File("C:\\");
    String url ="Please choose a file";
    Ressource ressource = new Ressource();
    Cours cours=new Cours();
    RessourceService ressourceService=new RessourceService();
    AlertClass alertClass= new AlertClass();
    CoursService coursService=new CoursService();



    @FXML
    void addChap(MouseEvent event) throws SQLException {
    if (isValid()){
        try {
            Files.copy(selectedFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            e.printStackTrace();
        }
        ressource.setTitre(addChapTiltle.getText());
        ressource.setType(addChapType.getValue());
        ressource.setUrl(url);
        String courseName=addChapCour.getValue();
        cours=coursService.getbyId(coursService.getCourstId(courseName));
        ressource.setId_cours_id(cours.getId());
        ressourceService.insert(ressource);
        alertClass.showSAlert("Addition Success", "The category "+ressource.getTitre()+" was successfully added");
        Stage stage = (Stage) uploadFile.getScene().getWindow();
        stage.close();
    }

    }

    @FXML
    void goBack(MouseEvent event) {
        Stage closestage = (Stage) uploadFile.getScene().getWindow();
        closestage.close();
    }

    @FXML
    void previewFile(MouseEvent event) {

    }

    @FXML
    void uploadFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\"));
        if (addChapType.getValue().equals("PDF")) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        }else if (addChapType.getValue().equals("Image")) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPEG Image","*.jpg"),
                    new FileChooser.ExtensionFilter("PNG Image", "*.png"),
                    new FileChooser.ExtensionFilter("All image files","*.jpg","*.png")
            );
        }else {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.avi", "*.mkv"));
        }
        selectedFile = fileChooser.showOpenDialog(addChapType.getScene().getWindow());
        if (selectedFile != null) {
            String pathName = selectedFile.getAbsolutePath().replace("\\", "/");
            destinationFile = new File(UploadDirectory, selectedFile.getName());
            url = UploadDirectory.getAbsolutePath().replace("\\", "/");
            url += "/" + selectedFile.getName();
            displayUrl.setText(url);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> types = FXCollections.observableArrayList("PDF", "Image", "Video");
        addChapType.setItems(types);
        addChapType.setValue(types.get(0));
        addChapCour.setItems(coursService.getCoursNames());
        addChapCour.setValue(coursService.getCoursNames().get(0));

    }
    public boolean isValid(){
        try {
            ObservableList<Ressource> ressources = ressourceService.getAll();
            String title = addChapTiltle.getText();
            ECtitre.setText("");

            if (title == null || title.isEmpty()) {
                ECtitre.setText("The title can't be empty");
                return false;
            }
            if (title.length() < 3 || title.length() > 20) {
                ECtitre.setText("The title is either too long or too short");
                return false;
            }
            for (Ressource cat : ressources) {
                if (title.equals(cat.getTitre())) {
                    ECtitre.setText("This title already exist");
                    return false;
                }
            }
            if (url.equals("Please choose a file")) {
                displayUrl.setText(url);
                return false;
            }
            return true ;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
