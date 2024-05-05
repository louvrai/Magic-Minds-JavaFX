package Controller;


import Service.ExcelService;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import Entity.User;
import Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
public class UserManagementController implements Initializable {

    @FXML
    private TableColumn<User,String> LastNameCol;

    @FXML
    private TableColumn<User, String> actionsCol;

    @FXML
    private TableColumn<User, String> ageCol;

    @FXML
    private Button btn_corses;

    @FXML
    private Button btn_events;

    @FXML
    private Button btn_forum;

    @FXML
    private Button btn_quizzs;

    @FXML
    private Button btn_store;

    @FXML
    private Button btn_user;

    @FXML
    private TableColumn<User, String> firstNameCol;

    @FXML
    private TableColumn<User, String> genderCol;

    @FXML
    private Pane inner_pane;

    @FXML
    private HBox root;

    @FXML
    private AnchorPane side_bar;

    @FXML
    private TableView<User> tableUser;

    @FXML
    private TextField txt_search;

    User user = null ;

    ObservableList<User> UserList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
   loadData();
    }

    @FXML
    void getSave(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/SaveUserController.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void print(MouseEvent event) throws SQLException {
        UserService service = new UserService();
        List<User>users = service.getAll();
        ExcelService excelService = new ExcelService();
        excelService.generateExcel(users);

    }

    @FXML
    void refreshTable(MouseEvent event) {
       refresh();
    }

    void refresh(){
        UserList.clear();
        UserService service = new UserService();
        try{
            List<User>users= service.getAll();
            for(User u:users){
                UserList.add(u);
                tableUser.setItems(UserList);
            }
        }catch(SQLException ex){
            Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    void loadData(){
         refresh();

        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        LastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        //add cell of button edit and delete
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFoctory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView showIcon = new FontAwesomeIconView(FontAwesomeIcon.EYE);

//                        deleteIcon.setStyle(
//                                " -fx-cursor: hand ;"
//                                        + "-glyph-size:28px;"
//                                        + "-fx-fill:#ff1744;"
//                        );
                        showIcon.setCursor(Cursor.HAND);

                        showIcon.setSize("28px");
                        showIcon.setFill(Color.valueOf("#2e90e1"));
                        deleteIcon.setCursor(Cursor.HAND);

                       deleteIcon.setSize("28px");
                        deleteIcon.setFill(Color.valueOf("#ff1744"));
//                        editIcon.setStyle(
//                                " -fx-cursor: hand ;"
//                                        + "-glyph-size:28px;"
//                                        + "-fx-fill:#00E676;"
//                        );
                        editIcon.setCursor(Cursor.HAND);

                        editIcon.setSize("28px");
                        editIcon.setFill(Color.valueOf("#00E676"));
                        showIcon.setOnMouseClicked((MouseEvent event) -> {
                            user = tableUser.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/ShowUserDetailsController.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ShowUserDetailsController saveUserController = loader.getController();

                            saveUserController.setTextField(user.getFirstName(),user.getLastName(),
                                    user.getEmail(),user.getTel(),user.getGender(),user.getAge(),user.getRoles(),user.getPicture()
                            );

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();

                        });
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation");
                            alert.setHeaderText("Do you really want to delete?");
                            alert.setContentText("Cette action est irréversible.");


                            ButtonType buttonTypeYes = new ButtonType("Yes");
                            ButtonType buttonTypeNo = new ButtonType("No");
                            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);


                            alert.showAndWait().ifPresent(response -> {
                                if (response == buttonTypeYes) {
                                    System.out.println("Action de suppression confirmée.");
                                    user = tableUser.getSelectionModel().getSelectedItem();
                                    UserService service = new UserService();
                                    try {
                                        service.delete(user.getId());
                                        Alert alertType = new Alert(Alert.AlertType.INFORMATION);
                                        alertType.setTitle("Sucess");
                                        alertType.setHeaderText("delete with success ");
                                        alertType.show();
                                    }catch (SQLException ex) {
                                        Alert alertType = new Alert(Alert.AlertType.ERROR);
                                        alertType.setTitle("Error");
                                        alertType.setHeaderText("wrong");
                                        alertType.show();
                                        System.out.println(ex.getMessage());
                                    }



                                } else {
                                    System.out.println("Action de suppression annulée.");

                                }
                            });





                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            user = tableUser.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/SaveUserController.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(UserManagementController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                           SaveUserController saveUserController = loader.getController();
                            saveUserController.setUpdate(true);
                            saveUserController.setTextField(user.getId(),user.getFirstName(),user.getLastName(),
                                    user.getEmail(),user.getTel(),user.getGender(),user.getAge(),user.getRoles(),user.getPicture()
                            );

                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon,showIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        actionsCol.setCellFactory(cellFoctory);
        tableUser.setItems(UserList);
    }

    @FXML
    void search(MouseEvent event) {

    }

 


}
