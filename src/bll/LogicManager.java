package bll;

import be.Song;
import dal.SongDAO_db;

import java.sql.SQLException;
import java.util.List;

public class LogicManager {
    private SongDAO_db songDAO_db = new SongDAO_db();

    public Song createSong (String title, String artist, String source, int genreID, String time) throws SQLException {
        return songDAO_db.createSong(title, artist, source, genreID, time);
    }

    public List<Song> getAllSongs(){
        return songDAO_db.getAllSongs();
    }
}
