package gui.model;

import be.Playlist;
import be.Song;
import be.SongsInPlaylist;
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

    public Song createSong(String title, String artist, String source, String genreID, String time) throws SQLException {
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

    public void deleteSong(int id) {
        bll.deleteSong(id);
    }

    public void deletePlaylist(int id){
        bll.deletePlaylist(id);
    }

    public void search(String query) {
        songs.clear();
        songs.addAll(bll.searchSongs(query));
    }

    public void addSongsInPlaylist(int playlistID, int songID) throws SQLException {
        bll.addSongsInPlaylist(playlistID, songID);
    }

    public ObservableList<SongsInPlaylist> getSongInPlayList(int id) {
        ObservableList<SongsInPlaylist> listObservable = FXCollections.observableArrayList();
        listObservable.addAll(bll.getSongInPlayList(id));
        return listObservable;
    }

    public void editSong(Song selectedSong) {
        bll.editSong(selectedSong);

    }

    public void editPlaylist(Playlist selectedPlaylist) {
        bll.editPlaylist(selectedPlaylist);
    }
}



