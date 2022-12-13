package gui.controller;

import be.Playlist;
import be.Song;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MyTunesController implements Initializable {
    @FXML
    private ListView songListView;
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
    private TextField txtSongSearch;

    private MyTunesModel model;
    private NewWindowController newWindowController;
    private MediaPlayer mediaPlayer;
    private Media media;
    private ArrayList<File> songs;
    private int songNumber;
    private File directory;
    private File[] files;
    private String path;
    private Timer timer;
    private TimerTask task;
    private boolean running;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MyTunesModel();
        model.fetchAllPlaylist();
        playlistTable.setItems(model.getPlaylist());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

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

        songs = new ArrayList<File>();
        directory = new File("Project Resources/Music");
        files = directory.listFiles();

        if(files != null){
            for(File file:files){
                songs.add(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        changeVolume();

        songLabel.setText(songs.get(songNumber).getName());
    }

    /**
     * Opens the file and immediately starts playing the music
     */
    public void openFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
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
    public void changeVolume(){
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
        beginTimer();
        media = new Media(songsTable.getSelectionModel().getSelectedItem().getSource());
        mediaPlayer = new MediaPlayer(media);
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
        if(songNumber > 0) {
            songNumber--;
            mediaPlayer.stop();

            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        } else {
            songNumber = songs.size() - 1;
            mediaPlayer.stop();

            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

    /**
     * Skip to the next song
     */
    public void nextMedia(){
        if(songNumber < songs.size() - 1){
            songNumber++;
            mediaPlayer.stop();

            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        } else{
            songNumber = 0;
            mediaPlayer.stop();

            if(running){
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

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
     * Opens a new window
     * add song...
     */
    public void addSong(ActionEvent actionEvent) throws IOException{
        FXMLLoader addSongLoader = new FXMLLoader(Main.class.getResource("view/AddSongWindow.fxml"));
        Scene addSongScene = new Scene(addSongLoader.load());

        newWindowController = addSongLoader.getController();
        newWindowController.setModel(model);

        Stage addSongStage = new Stage();
        addSongStage.setTitle("Add Song");
        addSongStage.setScene(addSongScene);
        addSongStage.setResizable(false);
        addSongStage.show();
    }

    /**
     * Opens a new window
     * edit song...
     */
    public void editSong(ActionEvent actionEvent) throws IOException{
        FXMLLoader editSongLoader = new FXMLLoader(Main.class.getResource("view/EditSongWindow.fxml"));
        Scene editSongScene = new Scene(editSongLoader.load());

        newWindowController = editSongLoader.getController();
        newWindowController.setSelectedSong(songsTable.getSelectionModel().getSelectedItem());

        Stage editSongStage = new Stage();
        editSongStage.setTitle("Edit Song");
        editSongStage.setScene(editSongScene);
        editSongStage.setResizable(false);
        editSongStage.show();
    }

    /**
     * Opens a new window
     * create playlist...
     */
    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader createPlaylistLoader = new FXMLLoader(Main.class.getResource("view/CreatePlaylistWindow.fxml"));
        Scene createPlaylistScene = new Scene(createPlaylistLoader.load());

        newWindowController = createPlaylistLoader.getController();
        newWindowController.setModel(model);

        Stage createPlaylistStage = new Stage();
        createPlaylistStage.setTitle("Create Playlist");
        createPlaylistStage.setScene(createPlaylistScene);
        createPlaylistStage.setResizable(false);
        createPlaylistStage.show();
    }

    /**
     * Opens a new window
     * edit playlist...
     */
    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader editPlaylistLoader = new FXMLLoader(Main.class.getResource("view/EditPlaylistWindow.fxml"));
        Scene editPlaylistScene = new Scene(editPlaylistLoader.load());

        newWindowController = editPlaylistLoader.getController();
        newWindowController.setSelectedPlaylist(playlistTable.getSelectionModel().getSelectedItem());
        //newWindowController.setModel(model);

        Stage editPlaylistStage = new Stage();
        editPlaylistStage.setTitle("Edit Playlist");
        editPlaylistStage.setScene(editPlaylistScene);
        editPlaylistStage.setResizable(false);
        editPlaylistStage.show();
    }

    public void deleteSongAction(ActionEvent actionEvent) {
        String selectedSongTitle = songsTable.getSelectionModel().getSelectedItem().getTitle();
        model.deleteSong(selectedSongTitle);

        Song selectedItem = songsTable.getSelectionModel().getSelectedItem();
        songsTable.getItems().remove(selectedItem);
    }

    public void deletePlaylistAction(ActionEvent actionEvent) {
        String selectedPlaylistName = playlistTable.getSelectionModel().getSelectedItem().getName();
        model.deletePlaylist(selectedPlaylistName);

        Playlist selectedItem = playlistTable.getSelectionModel().getSelectedItem();
        playlistTable.getItems().remove(selectedItem);
    }

    public void moveToPlaylist(ActionEvent actionEvent) {
        Song selectSong = songsTable.getSelectionModel().getSelectedItem();
        songListView.getItems().add(selectSong.getTitle());
    }
}