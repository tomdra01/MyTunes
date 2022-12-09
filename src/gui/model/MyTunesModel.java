package gui.model;

import be.Playlist;
import be.Song;
import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public class MyTunesModel {
    LogicManager bll = new LogicManager();
    private ObservableList<Song> songsToDelete = FXCollections.observableArrayList();
    private ObservableList<Song> songs = FXCollections.observableArrayList();
    private ObservableList<Playlist> playlists = FXCollections.observableArrayList();

    public ObservableList<Song> getSongs() {
        return songs;
    }

    public ObservableList<Song> getSongsToDelete(){
        return songsToDelete;
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

    public void deleteSong(String title) {
        bll.deleteSong(title);
    }

    public void deletePlaylist(String name){
        bll.deletePlaylist(name);
    }

    public void search(String query) {
        songs.clear();
        songs.addAll(bll.searchSongs(query));
    }





}



