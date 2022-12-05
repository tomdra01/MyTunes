package gui.model;

import be.Song;
import bll.LogicManager;

import java.sql.SQLException;

public class MyTunesModel {
    LogicManager bll = new LogicManager();
    public Song createSong(String title, String source, String artist, int genreID) throws SQLException {
        Song song = bll.createSong(title, artist, source, genreID);
        return song;
    }
}
