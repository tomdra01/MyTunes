package gui.model;

import be.Playlist;
import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.MediaPlayer;

import java.sql.SQLException;

public class MyTunesModel {
    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    LogicManager bll = new LogicManager();


    public ObservableList<Song> getSongs() {
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


    public ObservableList<Playlist> getPlaylist() {
        return playlists;
    }

    public Playlist createPlaylist(String name) throws SQLException {
        Playlist playlist = bll.createPlaylist(name);
        playlists.add(playlist);
        return playlist;
    }

    public void fetchAllPlaylist() {
        playlists.addAll(bll.getAllPlaylist());
    }
}



