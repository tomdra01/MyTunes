package bll;

import be.Song;
import dal.SongDAO_db;

import java.sql.SQLException;

public class LogicManager {
    private SongDAO_db songDAO_db = new SongDAO_db();

    public Song createSong (String title, String source, String artist, int genreID) throws SQLException {
        return songDAO_db.createSong(title, artist, source, genreID);
    }
}
