package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    private Text ageControl;

    @FXML
    private TextField ageId;

    @FXML
    private Text agreeControl;

    @FXML
    private CheckBox agreeId;

    @FXML
    private Text emailControl;

    @FXML
    private TextField emailId;

    @FXML
    private Text firstnameControl;

    @FXML
    private TextField firstnameId;

    @FXML
    private Text genderControl;

    @FXML
    private ComboBox<String> genderId;

    @FXML
    private Text lastnameControl;

    @FXML
    private TextField lastnameId;

    @FXML
    private Text passwordControl;

    @FXML
    private TextField passwordId;

    @FXML
    private Text roleControl;

    @FXML
    private ComboBox<String> roleId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void forgetPasswordPage(MouseEvent event) {

    }

    @FXML
    void loginPage(MouseEvent event) {

    }

    @FXML
    void signUpBtn(MouseEvent event) {

    }


}
