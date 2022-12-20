package be;

import javafx.beans.property.SimpleStringProperty;

public class Song {
    private int id;
    private SimpleStringProperty title = new SimpleStringProperty();
    private SimpleStringProperty artist = new SimpleStringProperty();
    private SimpleStringProperty source = new SimpleStringProperty();
    private SimpleStringProperty genreID = new SimpleStringProperty();
    private SimpleStringProperty time = new SimpleStringProperty();

    /**
     * Constructor for Song
     */

    public Song(String title, String artist, String source, String genreID, String time) {
        this.title.set(title);
        this.artist.set(artist);
        this.source.set(source);
        this.genreID.set(genreID);
        this.time.set(time);
    }



    /**
     * Constructor for Song_db
     */
    public Song(int id, String title, String artist, String source, String genreID, String time) {
        this(title, artist,source,genreID,time);
        this.id = id;
    }

    /**
     * Getter for Id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getArtist() {
        return artist.get();
    }

    public SimpleStringProperty artistProperty() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist.set(artist);
    }

    public String getSource() {
        return source.get();
    }

    public SimpleStringProperty sourceProperty() {
        return source;
    }

    public void setSource(String source) {
        this.source.set(source);
    }

    public String getGenreID() {
        return genreID.get();
    }

    public SimpleStringProperty genreIDProperty() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID.set(genreID);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    /**
     * toString method
     */
    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}