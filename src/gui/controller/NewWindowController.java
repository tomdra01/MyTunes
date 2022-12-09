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
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewWindowController implements Initializable {
    @FXML
    private TextField playlistNameField;
    @FXML
    private TextField titleField;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField artistField;
    @FXML
    private SplitMenuButton chooseCategory;
    @FXML
    private TextField timeField;
    @FXML
    private TextField fileField;


    @FXML
    private Button addButton;
    @FXML
    private MenuItem trapButton;
    @FXML
    private MenuItem popButton;

    private MyTunesModel model;
    private MediaPlayer mediaPlayer;
    private File file;
    private String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setModel(MyTunesModel model){
        this.model = model;
    }

    /**
     * Choosing a file we want to add to our MyTunes
     */
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null){
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() ->{
                int minutes = (int) mediaPlayer.getTotalDuration().toMinutes(); //Getting the minutes of the song
                int seconds = (int) mediaPlayer.getTotalDuration().toSeconds() % 60; //Getting seconds of the song
                timeField.setText(minutes + ":" + seconds); //Shows the total time of the song
            });
            fileField.setText(file.getPath()); //Shows the path to the file
        }
    }

    /**
     * This method adds song directly to the database and when you press "Add" button it will close the window
     */
    public void addSongAction(ActionEvent actionEvent) throws SQLException {
        model.createSong(titleField.getText(), path, artistField.getText(), chooseCategory.getText() , timeField.getText());

        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method creates new playlist
     */
    public void createPlaylistAction(ActionEvent actionEvent) throws SQLException{
        model.createPlaylist(playlistNameField.getText());
        Stage stage = (Stage) createButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method close the window everytime you press the "Cancel" button
     */
    public void cancelActionButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void popCategory(ActionEvent actionEvent) {
        chooseCategory.setText(popButton.getText());
    }

    public void trapCategory(ActionEvent actionEvent) {
        chooseCategory.setText(trapButton.getText());
    }
}