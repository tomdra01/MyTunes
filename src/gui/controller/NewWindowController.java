package gui.controller;

import gui.model.MyTunesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewWindowController implements Initializable {
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private MenuItem trapButton;
    @FXML
    private MenuItem popButton;
    @FXML
    private SplitMenuButton chooseCategory;
    @FXML
    private Button closeButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField fileField;
    @FXML
    private TextField timeField;
    private String path;
    private Media media;
    private File file;
    private MediaPlayer mediaPlayer;

    MyTunesModel model;

    public void setModel(MyTunesModel model){
        this.model = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setParentController() {
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Your Directory");
        file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);

            //Shows the time of the file
            mediaPlayer.setOnReady(() ->{
                int minutes = (int) mediaPlayer.getTotalDuration().toMinutes(); // Getting the minutes of the song
                int seconds = (int) mediaPlayer.getTotalDuration().toSeconds() % 60; // Getting seconds of the song
                timeField.setText(minutes + ":" + seconds);
                System.out.println(minutes + ":" + seconds);
            });
            //Shows the path to the file
            fileField.setText(file.getPath());
        }
    }

    /**
     * This method close the window everytime you press the "Cancel" button
     */
    public void cancelActionButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method adds song directly to the database and when you press "Add" button it will close the window
     */
    public void addSongAction(ActionEvent actionEvent) throws SQLException {
        model.createSong(titleField.getText(), path, artistField.getText(), 1 , timeField.getText());

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method creates new playlist
     */
    public void createPlaylistAction(ActionEvent actionEvent) {
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    public void popCategory(ActionEvent actionEvent) {
        chooseCategory.setText(popButton.getText());
    }

    public void trapCategory(ActionEvent actionEvent) {
        chooseCategory.setText(trapButton.getText());
    }
}
