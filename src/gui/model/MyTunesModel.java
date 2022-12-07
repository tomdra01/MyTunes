package gui.model;

import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MyTunesModel {
    LogicManager bll = new LogicManager();
    private ObservableList<Song> songs = FXCollections.observableArrayList();

    public ObservableList<Song> getSongs(){
        return songs;
    }
    public Song createSong(String title, String artist, String source, int genreID, String time) throws SQLException {
        Song song = bll.createSong(title, artist, source, genreID, time);
        songs.add(song);
        return song;
    }


    public void fetchAllSongs() {
        songs.addAll(bll.getAllSongs());
    }
}
