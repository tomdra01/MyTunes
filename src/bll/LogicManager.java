package bll;

import be.Playlist;
import be.Song;
import dal.PlaylistDAO;
import dal.SongDAO_db;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogicManager {
    private SongDAO_db songDAO_db = new SongDAO_db();
    private PlaylistDAO playlistDAO = new PlaylistDAO();

    public Song createSong (String title, String artist, String source, String genreID, String time) throws SQLException {
        return songDAO_db.createSong(title, artist, source, genreID, time);
    }

    public List<Song> getAllSongs(){
        return songDAO_db.getAllSongs();
    }

    public List<Song> searchSongs(String query) {
        List<Song> songs = songDAO_db.getAllSongs();
        List<Song> filtered = new ArrayList<>();

        for(Song s : songs) {
            if((""+s.getArtist().toLowerCase()).contains(query.toLowerCase()) || s.getTitle().toLowerCase().contains(query.toLowerCase())){
                filtered.add(s);
            }
        }
        return filtered;
    }

    public void deleteSong(int id) {
        songDAO_db.deleteSong(id);
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
        songDAO_db.editSong(id, newTitle, newArtist);
    }

    public void editPlaylist(int id, String newName) {
        playlistDAO.editPlaylist(id, newName);
    }
}