package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.MySQLConnection;

/**
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private Label userLabel;
    @FXML
    private Label passwdLabel;
    @FXML
    private ComboBox<String> userPicker;
    @FXML
    private TextField passwdTextField;
    @FXML
    private Label titleLabel;
    
    private String userOne, userTwo, userThree;
    
    private Parent mainParent;
    private FXMLLoader loader;
    
    @FXML
    private Button loginButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        userOne = "christian";
        userTwo = "gustaf";
        userThree = "clientapp";
        ObservableList<String> userList = FXCollections.observableArrayList(userOne, userTwo,userThree);
        userPicker.getItems().addAll(userList);
        userPicker.getSelectionModel().select(userThree);
        passwdTextField.setText("123456"); // FOR NOW ONLY!
        userPicker.setDisable(true);    
        passwdTextField.setDisable(true);
        
    }    

    @FXML
    private void handleUserPick(ActionEvent event) {
        
    }

    @FXML
    private void handlePasswdInput(ActionEvent event) throws IOException, SQLException {
        handleLogin(event);
        
    }
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException, SQLException {
        handleLogin(event);
    }
    
    
   private void handleLogin(ActionEvent event) throws IOException, SQLException{
       
        String user = userPicker.getValue();
        
        String pwd = passwdTextField.getText();
        if(userPicker.getValue() != null){
            MySQLConnection connection= new MySQLConnection("db.christianekenstedt.se", "medialibrary", userThree, pwd);
            loader = new FXMLLoader(getClass().getResource("/FXMLView/FXMLMainView.fxml"));
            mainParent = loader.load();
            
            Scene mainScene = new Scene(mainParent);
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLMainViewController c = loader.getController();
            c.initConnection(connection);
            if(connection.connectToDatabase()){
                mainStage.setScene(mainScene);
                mainStage.hide();
                mainStage.getIcons().add(new Image("resources/playIcon.png"));
                mainStage.setTitle("Media Library");
                mainStage.setOnCloseRequest((WindowEvent event1) -> {
                    try {
                        connection.closeConnection();
                    } catch (SQLException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                mainStage.show();
            }else showAlert("Invalid password!");
        }else showAlert("No user selected!");
   }
    
    private void showAlert(String message){
        alert.setHeaderText("");
        alert.setTitle("Alert!");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("resources/playIcon.png"));
        alert.setContentText(message);
        alert.show();
    }
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    
}
