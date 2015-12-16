package Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Genre;
import model.Grade;
import model.NoSQLConnection;

/**
 * FXML Controller class
 *
 * @author Christian Ekenstedt & Gustaf Holmström
 */
public class AddViewController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField artistTextField;
    @FXML
    private ComboBox<Genre> genreComboBox;
    @FXML
    private ComboBox<Grade> gradeComboBox;
    @FXML
    private Button addButton;
    
    private NoSQLConnection connection;
    @FXML
    private TextField nationalityTextField;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void addButtonHandle(ActionEvent event) throws IOException{
        String title = titleTextField.getText();
        String artist = artistTextField.getText();
        LocalDate date = datePicker.getValue();
        Genre genre = genreComboBox.getValue();
        Grade grade = gradeComboBox.getValue();
        String nationality = nationalityTextField.getText();
        
        if(title.length()>0 && artist.length() > 0 && genre.getGenreID() != null && grade.getGradeID() != null){
            Date d = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            new Thread(){
                @Override
                public void run(){ connection.addAlbum(title, artist, nationality, d, genre, grade);}
            }.start();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }else{
            System.out.println("Fill all the fields!");
        }
    }
    
    public void initData(NoSQLConnection connection){
        this.connection = connection;
    }
    
    public void updateComboBoxes() throws IOException {
        new Thread(){
            @Override
            public void run(){
                ObservableList<Genre> genres = FXCollections.observableArrayList(connection.getGenre());
                ObservableList<Grade> grades = FXCollections.observableArrayList(connection.getGrades());
                javafx.application.Platform.runLater(new Runnable(){
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
    
    
}
