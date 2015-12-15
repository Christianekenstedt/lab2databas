package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

/**
 * FXML Controller class
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class FXMLMainViewController implements Initializable {        
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu fileMenu;
    @FXML
    private Menu helpMenu;
    @FXML
    private MenuItem aboutMenuItem;
    private String user, pwd;
    @FXML
    private TableView<Object> table;
    @FXML
    private Label connectedLabel;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButn;
    @FXML
    private Label tempLabel;
    @FXML
    private ComboBox<String> searchComboBox;
    @FXML
    private MenuItem addAlbumMenuItem;
    
    //private MySQLConnection connection;
    private NoSQLConnection connection;
    @FXML
    private ComboBox<Genre> genreComboBox;
    @FXML
    private ComboBox<Grade> gradeComboBox;
    @FXML
    private Button searchButtonCombo;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem fillDbMenuItem;
    @FXML
    private MenuItem dropDbMenuItem;




    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String choiceOne = "Get Album by Title", choiceTwo = "Get Album by Artist";
        ObservableList<String> choices = FXCollections.observableArrayList(choiceOne, choiceTwo);
        searchComboBox.getItems().addAll(choices);
        searchComboBox.getSelectionModel().select("Get Album by Title");
        searchField.setPromptText("Type here");
        
        
    }    
    
    private void disconnectButtonHandle(ActionEvent event) throws IOException {
        connection.closeConnection();
    }
    
    private void showAlbumButtonHandle(ActionEvent event) {
        table.getColumns().clear();
    }

    private void showArtistButtonHandle(ActionEvent event) {
        table.getColumns().clear();
    }

    @FXML
    private void handleSearchButn(ActionEvent event) throws IOException {
        if(!searchField.getText().isEmpty()){
            int selection = 0;
            if(searchComboBox.getValue().equals("Get Album by Title")) selection = 1;
            else if(searchComboBox.getValue().equals("Get Album by Artist")) selection = 2;
            final int finalSelect = selection;
            new Thread(){
                    @Override
                    public void run(){
                        ArrayList<Object> list;
                        if(finalSelect == 1){
                            list = connection.getAlbumByTitle(searchField.getText());
                        }else{
                            list = connection.getAlbumsByArtist(searchField.getText());
                        }
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                updateUI(list, finalSelect);
                            }
                        });
                    }
                }.start();
            
        }
    }
    
    @FXML
    public void addAlbumHandle(ActionEvent event) throws IOException, IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLView/FXMLAddView.fxml"));
        Parent root = loader.load();
        
        AddViewController c = loader.getController();
        c.initData(connection);
        c.updateComboBoxes();
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("resources/playIcon.png"));
        stage.setResizable(false);
        stage.setTitle("Add Album");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        }
    
    public void initConnection(NoSQLConnection connection){
        this.connection = connection;
        connectedLabel.setTextFill(Color.GREEN);
        connectedLabel.setText("Connected as " + connection.getConnectedUser() + " to " + connection.getHost() + " --> " + connection.getDatabase());
    }
    
    public void updateUI(ArrayList<Object> inputList, int select){
            if(select == 1){ // 1 = AlbumByTitle

                ObservableList<Object> list =  FXCollections.observableArrayList(inputList);
                table.getColumns().clear();
                TableColumn<Object, Integer> cID = new TableColumn<>("AlbumID");
                cID.setCellValueFactory(new PropertyValueFactory("albumID"));
                TableColumn<Object, String> cName = new TableColumn<>("Title");
                cName.setCellValueFactory(new PropertyValueFactory("name"));
                TableColumn<Object, LocalDate> cDate = new TableColumn<>("Release Date");
                cDate.setCellValueFactory(new PropertyValueFactory("releaseDate"));
                table.getColumns().addAll(cID,cName,cDate);
                table.setItems(list);
                
            }else if (select == 2){ // AlbumsByArtist
                ObservableList<Object> list =  FXCollections.observableArrayList(inputList);
                table.getColumns().clear();
                TableColumn<Object, Integer> cID = new TableColumn<>("AlbumID");
                cID.setCellValueFactory(new PropertyValueFactory("albumID"));
                TableColumn<Object, String> cName = new TableColumn<>("Title");
                cName.setCellValueFactory(new PropertyValueFactory("name"));
                TableColumn<Object, LocalDate> cDate = new TableColumn<>("Release Date");
                cDate.setCellValueFactory(new PropertyValueFactory("releaseDate"));
                table.getColumns().addAll(cID,cName,cDate);
                table.setItems(list);
            }
    }

    @FXML
    private void handleButtonCombo(ActionEvent event) throws IOException {
        if(gradeComboBox.getValue() != null){
            new Thread(){
                    @Override
                    public void run(){
                        ArrayList<Object> list;
                        list = connection.getAlbumByGrade(gradeComboBox.getValue().getGradeID());
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                updateUI(list, 1);
                            }
                        });
                    }
                }.start();
        }else{
            new Thread(){
                    @Override
                    public void run(){
                        ArrayList<Object> list;
                        list = connection.getAlbumByGenre(genreComboBox.getValue().getGenreID());
                        Platform.runLater(new Runnable(){
                            @Override
                            public void run(){
                                updateUI(list, 1);
                            }
                        });
                    }
            }.start();
        }
    }

    @FXML
    private void genreBoxClicked(MouseEvent event) throws IOException {
        updateComboBoxes();
        gradeComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void gradeBoxClicked(MouseEvent event) throws IOException {
        updateComboBoxes();
        genreComboBox.getSelectionModel().clearSelection();
    }
    
    private void updateComboBoxes() throws IOException{
        new Thread(){
            @Override
            public void run(){
                ObservableList<Genre> genres = FXCollections.observableArrayList(connection.getGenre());
                ObservableList<Grade> grades = FXCollections.observableArrayList(connection.getGrades());
                Platform.runLater(new Runnable(){
                    @Override
                    public void run(){
                        updateUIComboBoxes(genres, grades);
                    }
                });
            }
        }.start();
    }
    
    private void updateUIComboBoxes(ObservableList<Genre> genres, ObservableList<Grade> grades){
        genreComboBox.setItems(genres);
        gradeComboBox.setItems(grades);
    }
    
    @FXML
    private void handleCloseMenuItem(ActionEvent event) throws IOException {
        connection.closeConnection();
        Platform.exit();
    }

    @FXML
    private void handleAboutMenuItem(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLView/FXMLAboutView.fxml"));
        Parent root = loader.load();
        
        FXMLAboutViewController c = loader.getController();
        c.initData(connection.getHost(), connection.getDatabase(), connection.getUsername());
        
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add(new Image("resources/playIcon.png"));
        stage.setTitle("About");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    public void fillDbMenuItemHandle(ActionEvent event){
        CreateDB d = new CreateDB(connection.getDb());
        d.createCollections();
    }

    @FXML
    public void dropDbMenuItemHandle(ActionEvent event){
        CreateDB d = new CreateDB(connection.getDb());
        d.dropDatabase();
    }
    
}
