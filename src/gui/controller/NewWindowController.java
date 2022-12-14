package gui.controller;

import be.Playlist;
import be.Song;
import gui.model.MyTunesModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewWindowController implements Initializable {
    @FXML
    private TextField titleField;
    @FXML
    private TextField editTitleField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField editArtistField;
    @FXML
    private TextField playlistNameField;
    @FXML
    private TextField editPlaylistNameField;
    @FXML
    private SplitMenuButton chooseCategory;
    @FXML
    private SplitMenuButton editCategoryButton;
    @FXML
    private TextField fileField;
    @FXML
    private TextField timeField;


    @FXML
    private Button addButton;
    @FXML
    private Button editSongButton;
    @FXML
    private Button createButton;
    @FXML
    private Button editPlaylistButton;
    @FXML
    private Button cancelButton;
    @FXML
    private MenuItem trapButton;
    @FXML
    private MenuItem popButton;

    private MyTunesModel model;

    private MyTunesController controller;
    private Playlist selectedPlaylist;
    private Song selectedSong;
    private MediaPlayer mediaPlayer;
    private File file;
    private String path;


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
        Stage stage = (Stage) addButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE);

        if(titleField.getText().trim().isEmpty() || artistField.getText().trim().isEmpty() || path.isEmpty() || chooseCategory.getText().trim().isEmpty() || timeField.getText().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();

        }else {
            model.createSong(titleField.getText(), artistField.getText(), path, chooseCategory.getText() , timeField.getText());
        }

        stage.close();
    }

    public void editSongAction(ActionEvent actionEvent) {
        Stage stage = (Stage) editSongButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE);
        if (editTitleField.getText().trim().isEmpty()||editArtistField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else{
            model.editSong(selectedSong.getTitle(), editTitleField.getText(), editArtistField.getText());
        }


        stage.close();
    }

    /**
     * This method creates new playlist
     */
    public void createPlaylistAction(ActionEvent actionEvent) throws SQLException{
        Stage stage = (Stage) createButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE);
        if (playlistNameField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else {
            model.createPlaylist(playlistNameField.getText());
        }


        stage.close();
    }

    public void editPlaylistAction(ActionEvent actionEvent) {
        Stage stage = (Stage) editPlaylistButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE);
        if (editPlaylistNameField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else {
            model.editPlaylist(selectedPlaylist.getName(), editPlaylistNameField.getText());
        }

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

    public void setSelectedPlaylist(Playlist selectedItem) {
        this.selectedPlaylist = selectedItem;
        editPlaylistNameField.setText(selectedPlaylist.getName());
    }

    public void setSelectedSong(Song selectedItem) {
        this.selectedSong = selectedItem;
        editTitleField.setText(selectedSong.getTitle());
        editArtistField.setText(selectedSong.getArtist());
        editCategoryButton.setText(selectedSong.getGenreID());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(selectedPlaylist);

    }





}