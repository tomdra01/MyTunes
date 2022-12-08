package gui.controller;

import be.Playlist;
import be.Song;
import dal.DatabaseConnector;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MyTunesController implements Initializable {
    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableColumn<Playlist,String> nameColumn;
    @FXML
    private TableView queueTable;
    @FXML
    private TableColumn<Song,String> queueColumn;
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
    private Label songLabel;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    private Media media;
    private MediaPlayer mediaPlayer;
    private File directory;
    private File[] files;
    private String path;
    private ArrayList<File> songs;
    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;
    private MyTunesModel model;

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

        songLabel.setText(songs.get(songNumber).getName());

        changeVolume();
    }



    public void changeVolume(){
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    /**
     * Play the song...
     * and we also begin our timeline
     */
    public void playMedia() {
        beginTimer();
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

    public void cancelTimer(){
        running = false;
        timer.cancel();
    }

    public void addSong(ActionEvent actionEvent) throws IOException{
        FXMLLoader addSongLoader = new FXMLLoader(Main.class.getResource("view/AddSongWindow.fxml"));
        Scene addSongScene = new Scene(addSongLoader.load());

        NewWindowController newWindowController = addSongLoader.getController();
        newWindowController.setParentController();
        newWindowController.setModel(model);

        Stage addSongStage = new Stage();
        addSongStage.setTitle("Add Song");
        addSongStage.setScene(addSongScene);
        addSongStage.setResizable(false);
        addSongStage.show();
    }

    public void editSong(ActionEvent actionEvent) throws IOException{
        FXMLLoader editSongLoader = new FXMLLoader(Main.class.getResource("view/EditSongWindow.fxml"));
        Scene editSongScene = new Scene(editSongLoader.load());

        NewWindowController newWindowController = editSongLoader.getController();
        newWindowController.setParentController();

        Stage editSongStage = new Stage();
        editSongStage.setTitle("Edit Song");
        editSongStage.setScene(editSongScene);
        editSongStage.setResizable(false);
        editSongStage.show();
    }

    public void createPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader createPlaylistLoader = new FXMLLoader(Main.class.getResource("view/CreatePlaylistWindow.fxml"));
        Scene createPlaylistScene = new Scene(createPlaylistLoader.load());

        NewWindowController newWindowController = createPlaylistLoader.getController();
        newWindowController.setParentController();
        newWindowController.setModel(model);

        Stage createPlaylistStage = new Stage();
        createPlaylistStage.setTitle("Create Playlist");
        createPlaylistStage.setScene(createPlaylistScene);
        createPlaylistStage.setResizable(false);
        createPlaylistStage.show();
    }

    public void editPlaylist(ActionEvent actionEvent) throws IOException {
        FXMLLoader editPlaylistLoader = new FXMLLoader(Main.class.getResource("view/EditPlaylistWindow.fxml"));
        Scene editPlaylistScene = new Scene(editPlaylistLoader.load());

        NewWindowController newWindowController = editPlaylistLoader.getController();
        newWindowController.setParentController();

        Stage editPlaylistStage = new Stage();
        editPlaylistStage.setTitle("Edit Playlist");
        editPlaylistStage.setScene(editPlaylistScene);
        editPlaylistStage.setResizable(false);
        editPlaylistStage.show();
    }

    public void openButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        path = file.toURI().toString();

        if (path != null){
            mediaPlayer.stop(); //Stops the current playing song before opening new song
            Media media = new Media(path);
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(file.getName()); //Changing the label for the current song
            playMedia();
        }
    }

    public void deleteSongAction(ActionEvent actionEvent) {


    }
}




