package bll;

import be.Playlist;
import be.Song;
import dal.PlaylistDAO;
import dal.SongDAO_db;
import javafx.stage.FileChooser;

import javax.swing.text.html.HTML;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogicManager {
    private SongDAO_db songDAO_db = new SongDAO_db();
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    public Song createSong (String title, String artist, String source, int genreID, String time) throws SQLException {
        return songDAO_db.createSong(title, artist, source, genreID, time);
    }

    public List<Song> getAllSongs(){
        return songDAO_db.getAllSongs();
    }

    public void deleteSong(String title) {
        songDAO_db.deleteSong(title);
    }

    public Playlist createPlaylist(String name) throws  SQLException{
        return playlistDAO.createPlaylist(name);
    }
    public  List<Playlist> getAllPlaylist(){
        return  playlistDAO.getAllPlaylist();
    }
}