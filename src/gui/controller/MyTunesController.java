package gui.controller;

import be.Playlist;
import be.Song;
import be.SongsInPlaylist;
import gui.Main;
import gui.model.MyTunesModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MyTunesController implements Initializable {
    @FXML
    private ListView<SongsInPlaylist> songListView;
    @FXML
    private TableColumn<Playlist,String> nameColumn;
    @FXML
    private Label songLabel;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Slider volumeSlider;

    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableView<Song> songsTable;
    @FXML
    private TableColumn<Song,String> titleColumn;
    @FXML
    private TableColumn<Song,String> artistColumn;
    @FXML
    private TableColumn<Song,String> genreColumn;
    @FXML
    private TableColumn<Song,String> timeColumn;

    @FXML
    private Button moveToPlaylistButton;
    @FXML
    private TextField txtSongSearch;

    private MyTunesModel model;
    private NewWindowController newWindowController;
    private MediaPlayer mediaPlayer;
    private Media media;
    private String path;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    /**
     * Opens the file and immediately starts playing the music
     */
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio types", "*.mp3", "*.wav"));
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null){
            mediaPlayer.stop(); //Stops the current playing song before opening new song
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(file.getName()); //Changing the label for the current song
            beginTimer();
            playMedia();
        }
    }

    /**
     * Changes the volume of the song using volume slider
     */
    public void changeVolume() {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);}
        });
    }

    /**
     * Play the song...
     * and we also begin our timeline
     */
    public void playMedia() {

        if(songListView.getSelectionModel().getSelectedItem()==null) {
            media = new Media(songsTable.getSelectionModel().getSelectedItem().getSource());
            songLabel.setText(songsTable.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songsTable.getSelectionModel().getSelectedItem().getTitle());
        } else {
            songsTable.getSelectionModel().clearSelection();
            media = new Media(songListView.getSelectionModel().getSelectedItem().getSource());
            songLabel.setText(songListView.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songListView.getSelectionModel().getSelectedItem().getTitle());
        }

        mediaPlayer =new MediaPlayer(media);
        beginTimer();
        changeVolume();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }

    /**
     * Pause the song...
     * and we also pause our timeline
     */
    public void pauseMedia() {
        cancelTimer();
        mediaPlayer.pause();
    }

    /**
     * Go back to the previous song
     */
    public void previousMedia() {
        if (songsTable.getSelectionModel().getSelectedItem()!=null) {
            mediaPlayer.stop();
            songsTable.getSelectionModel().selectPrevious();

            media = new Media(songsTable.getSelectionModel().getSelectedItem().getSource());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songsTable.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songsTable.getSelectionModel().getSelectedItem().getTitle());

            mediaPlayer.play();
        }
        else {
            mediaPlayer.stop();
            songListView.getSelectionModel().selectPrevious();

            media = new Media(songListView.getSelectionModel().getSelectedItem().getSource());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songListView.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songListView.getSelectionModel().getSelectedItem().getTitle());

            mediaPlayer.play();
        }
    }

    /**
     * Skip to the next song
     */
    public void nextMedia(){

        if (songsTable.getSelectionModel().getSelectedItem()!=null) {
            mediaPlayer.stop();
            songsTable.getSelectionModel().selectNext();

            media = new Media(songsTable.getSelectionModel().getSelectedItem().getSource());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songsTable.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songsTable.getSelectionModel().getSelectedItem().getTitle());

            mediaPlayer.play();
        }
        else {
            mediaPlayer.stop();
            songListView.getSelectionModel().selectNext();

            media = new Media(songListView.getSelectionModel().getSelectedItem().getSource());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songListView.getSelectionModel().getSelectedItem().getArtist() + "  -  " + songListView.getSelectionModel().getSelectedItem().getTitle());

            mediaPlayer.play();
        }
    }

    /**
     * Begins the timeline
     */
    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                songProgressBar.setProgress(current/end);
                if(current/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    /**
     * Resets the timer
     */
    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    /**
     * NEW WINDOW
     * for adding song...
     */
    public void addSong() throws IOException{
        FXMLLoader addSongLoader = new FXMLLoader(Main.class.getResource("view/AddSongWindow.fxml"));
        Scene addSongScene = new Scene(addSongLoader.load()); //New scene

        newWindowController = addSongLoader.getController(); //Getting the controller
        newWindowController.setModel(model); //Setting model

        Stage addSongStage = new Stage(); //New stage
        addSongStage.setTitle("Add Song");
        addSongStage.setScene(addSongScene);
        addSongStage.setResizable(false);
        addSongStage.show();
    }

    /**
     * NEW WINDOW
     * for editing song...
     */
    public void editSong(ActionEvent actionEvent) throws IOException{
        FXMLLoader editSongLoader = new FXMLLoader(Main.class.getResource("view/EditSongWindow.fxml"));
        Scene editSongScene = new Scene(editSongLoader.load()); //New Scene

        newWindowController = editSongLoader.getController(); //Getting the controller
        newWindowController.setModel(model); //Setting model
        newWindowController.setSelectedSong(songsTable.getSelectionModel().getSelectedItem());

        Stage editSongStage = new Stage(); //New stage
        editSongStage.setTitle("Edit Song");
        editSongStage.setScene(editSongScene);
        editSongStage.setResizable(false);
        editSongStage.show();
    }

    /**
     * NEW WINDOW
     * for creating playlist...
     */
    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader createPlaylistLoader = new FXMLLoader(Main.class.getResource("view/CreatePlaylistWindow.fxml"));
        Scene createPlaylistScene = new Scene(createPlaylistLoader.load()); //New scene

        newWindowController = createPlaylistLoader.getController(); //Getting the controller
        newWindowController.setModel(model); //Setting the model

        Stage createPlaylistStage = new Stage(); //New stage
        createPlaylistStage.setTitle("Create Playlist");
        createPlaylistStage.setScene(createPlaylistScene);
        createPlaylistStage.setResizable(false);
        createPlaylistStage.show();
    }

    /**
     * NEW WINDOW
     * for editing playlist...
     */
    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader editPlaylistLoader = new FXMLLoader(Main.class.getResource("view/EditPlaylistWindow.fxml"));
        Scene editPlaylistScene = new Scene(editPlaylistLoader.load()); //New scene

        newWindowController = editPlaylistLoader.getController(); //Getting the controller
        newWindowController.setSelectedPlaylist(playlistTable.getSelectionModel().getSelectedItem());
        newWindowController.setModel(model); //Setting model

        Stage editPlaylistStage = new Stage(); //New stage
        editPlaylistStage.setTitle("Edit Playlist");
        editPlaylistStage.setScene(editPlaylistScene);
        editPlaylistStage.setResizable(false);
        editPlaylistStage.show();
    }

    /**
     * This method deletes selected song from tableView and also from the database
     * you also get a confirmation window to confirm delete
     */
    public void deleteSongAction() {
        //Getting the selection item from the table
        int selectedSong = songsTable.getSelectionModel().getSelectedItem().getId();
        Song selectedItem = songsTable.getSelectionModel().getSelectedItem();

        //Creating alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to DELETE?");

        //Button statements
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){ //... user chose OK
            model.deleteSong(selectedSong);
            songsTable.getItems().remove(selectedItem);
            alert.close();
        } else {
            alert.close(); //... user chose CANCEL or closed the dialog
        }
    }

    /**
     * This method deletes selected playlist from tableView and also from the database
     * you also get a confirmation window to confirm delete
     */
    public void deletePlaylistAction() {
        //Getting the selection item from the table
        int selectedPlaylistID = playlistTable.getSelectionModel().getSelectedItem().getId();
        Playlist selectedItem = playlistTable.getSelectionModel().getSelectedItem();

        //Creating alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to DELETE?");

        //Button statements
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){ //... user chose OK
            model.deletePlaylist(selectedPlaylistID);
            playlistTable.getItems().remove(selectedItem);
            alert.close();
        } else {
            alert.close(); //... user chose CANCEL or closed the dialog
        }
    }

    /**
     * Moving song from song tableview to the listView (Songs in playlist)
     */
    public void selectedPlaylistAction() {
        if(playlistTable.getSelectionModel().getSelectedItem()==null) {
            return;
        }
        songListView.getItems().clear();
        songListView.getItems().addAll(model.getSongInPlayList(playlistTable.getSelectionModel().getSelectedItem().getId()));

         moveToPlaylistButton.setOnAction(event -> {
             int selectedSongID = songsTable.getSelectionModel().getSelectedItem().getId();
             int selectedPlaylistID = playlistTable.getSelectionModel().getSelectedItem().getId();

             try {
                 model.addSongsInPlaylist(selectedPlaylistID, selectedSongID);
                 songListView.refresh();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
         });
    }

    /**
     * Initialize method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //PLAYLISTS
        model = new MyTunesModel();
        model.fetchAllPlaylist();
        playlistTable.setItems(model.getPlaylist());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        //SONGS
        model.fetchAllSongs();
        songsTable.setItems(model.getSongs());
        titleColumn.setCellValueFactory((new PropertyValueFactory<>("title")));
        artistColumn.setCellValueFactory((new PropertyValueFactory<>("artist")));
        genreColumn.setCellValueFactory((new PropertyValueFactory<>("genreID")));
        timeColumn.setCellValueFactory((new PropertyValueFactory<>("time")));

        txtSongSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                model.search(newValue);
            }
        });
    }
}