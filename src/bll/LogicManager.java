package bll;

import be.Playlist;
import be.Song;
import be.SongsInPlaylist;
import dal.PlaylistDAO;
import dal.SongDAO;
import dal.SongsInPlaylistDAO;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private SongDAO songDAO = new SongDAO();
    private PlaylistDAO playlistDAO = new PlaylistDAO();
    private SongsInPlaylistDAO songsInPlaylistDAO = new SongsInPlaylistDAO();

    public Song createSong (String title, String artist, String source, String genreID, String time) throws SQLException {
        return songDAO.createSong(title, artist, source, genreID, time);
    }

    public List<Song> getAllSongs(){
        return songDAO.getAllSongs();
    }

    public List<Song> searchSongs(String query) {
        List<Song> songs = songDAO.getAllSongs();
        List<Song> filtered = new ArrayList<>();

        for(Song s : songs) {
            if((""+s.getArtist().toLowerCase()).contains(query.toLowerCase()) || s.getTitle().toLowerCase().contains(query.toLowerCase())){
                filtered.add(s);
            }
        }
        return filtered;
    }

    public void deleteSong(int id) {
        songDAO.deleteSong(id);
    }

    public Playlist createPlaylist(String name) throws  SQLException{
        return playlistDAO.createPlaylist(name);
    }
    public  List<Playlist> getAllPlaylist(){
        return  playlistDAO.getAllPlaylist();
    }

    public void deletePlaylist(int id) {
        playlistDAO.deletePlaylist(id);
    }

    public void editSong(int id, String newTitle, String newArtist) {
        songDAO.editSong(id, newTitle, newArtist);
    }

    public void editPlaylist(int id, String newName) {
        playlistDAO.editPlaylist(id, newName);
    }

    public void addSongsInPlaylist(int playlistID, int songID) throws SQLException {
        songsInPlaylistDAO.addSongsInPlaylist(playlistID, songID);
    }

    public List<SongsInPlaylist> getSongInPlayList(int id) {
        return songsInPlaylistDAO.getAllSongsInPlaylist(id);
    }
}