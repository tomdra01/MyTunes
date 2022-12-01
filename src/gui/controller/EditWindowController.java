package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditWindowController implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField timeField;
    @FXML
    private TextField fileField;
    @FXML
    private Button saveChanges;
    @FXML
    private Button cancelChanges;

    private String path;
    private Media media;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void chooseFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Your Directory");
        File file = directoryChooser.showDialog(null);
        path = file.toURI().toString();

        if (path != null){
            this.media = new Media(path);
            this.mediaPlayer = new MediaPlayer(media);
        }
    }

    public void setParentController2() {
    }

    public void saveChanges() {
    }

    public void cancelChanges() {
    }
}
