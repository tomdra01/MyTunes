package gui.model;

import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MyTunesModel {
    private ObservableList<Song> songs = FXCollections.observableArrayList();
    LogicManager bll = new LogicManager();

    public ObservableList<Song> getSongs(){
        return songs;
    }
    public Song createSong(String title, String source, String artist, int genreID) throws SQLException {
        Song song = bll.createSong(title, artist, source, genreID);
        songs.add(song);
        return song;
    }

    public void fetchAllSongs() {
        songs.addAll(bll.getAllSongs());
    }
}
