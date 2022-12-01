package gui.controller;

import gui.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MyTunesController implements Initializable {
    public ListView<String> playlistsList;
    @FXML
    private ListView<String> songsList;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        songs = new ArrayList<File>();
        directory = new File("Project Resources/Music");
        files = directory.listFiles();

        if(files != null){
            for(File file:files){
                songs.add(file);
                songsList.getItems().addAll(file.getName());
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
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/EditWindow.fxml"));
        Scene scene = new Scene(loader.load());

        EditWindowController editWindowController = loader.getController();
        editWindowController.setParentController2();

        Stage stage = new Stage();
        stage.setTitle("Window");
        stage.setScene(scene);
        stage.show();
    }
}

