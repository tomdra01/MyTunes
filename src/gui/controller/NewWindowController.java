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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio types", "*.mp3", "*.wav"));
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
     * This method adds song directly to the database and when you press "add" button it will close the window
     * it will also pop up Error alert when you haven't filled in all text fields
     */
    public void addSongAction(ActionEvent actionEvent) throws SQLException {
        Stage stage = (Stage) addButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE); // New alert

        if(titleField.getText().trim().isEmpty() || artistField.getText().trim().isEmpty() || path.isEmpty() || chooseCategory.getText().trim().isEmpty() || timeField.getText().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else {
            model.createSong(titleField.getText(), artistField.getText(), path, chooseCategory.getText() , timeField.getText());
            stage.close();
        }
    }

    /**
     * Calling a selected item from the MyTunesController's songsTable
     * ... puts selected item in the edit fieldText
     */
    public void setSelectedSong(Song selectedItem) {
        this.selectedSong = selectedItem;
        editTitleField.setText(selectedSong.getTitle());
        editArtistField.setText(selectedSong.getArtist());
        editCategoryButton.setText(selectedSong.getGenreID());
    }

    /**
     * This method edits song from it's tableView and also from the database
     * ... Error alert pops up when the edit fieldText is empty
     */
    public void editSongAction(ActionEvent actionEvent) {
        Stage stage = (Stage) editSongButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE);

        if (editTitleField.getText().trim().isEmpty()||editArtistField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else{
            selectedSong.setArtist(editArtistField.getText());
            selectedSong.setTitle(editTitleField.getText());
            model.editSong(selectedSong);
            stage.close();
        }
    }

    /**
     * Create Playlist action
     */
    public void createPlaylistAction(ActionEvent actionEvent) throws SQLException{
        Stage stage = (Stage) createButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE); //New alert

        if (playlistNameField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else {
            model.createPlaylist(playlistNameField.getText());
            stage.close();
        }
    }

    /**
     * Calling a selected item from the MyTunesController's playlistTable
     * ... puts selected item in the edit fieldText
     */
    public void setSelectedPlaylist(Playlist selectedItem) {
        this.selectedPlaylist = selectedItem;
        editPlaylistNameField.setText(selectedPlaylist.getName());
    }

    /**
     * Edit Playlist action
     */
    public void editPlaylistAction(ActionEvent actionEvent) {
        Stage stage = (Stage) editPlaylistButton.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.NONE); //New alert

        if (editPlaylistNameField.getText().trim().isEmpty()){
            a.setAlertType(Alert.AlertType.ERROR);
            a.setContentText("Please fill in all fields");
            a.show();
        }else {
            selectedPlaylist.setName(editPlaylistNameField.getText());
            model.editPlaylist(selectedPlaylist);
            //model.editPlaylist(selectedPlaylist.getId(), editPlaylistNameField.getText());
            stage.close();
        }
    }

    /**
     * This method close the window everytime you press the "cancel" button
     */
    public void cancelActionButton(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Getting selected category
     */
    public void popCategory(ActionEvent actionEvent) {
        chooseCategory.setText(popButton.getText());
    }

    /**
     * Getting selected category
     */
    public void trapCategory(ActionEvent actionEvent) {
        chooseCategory.setText(trapButton.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}