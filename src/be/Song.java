package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String source;
    private String genreID;
    private String time;

    /**
     * Constructor for Song
     */

    public Song(String title, String artist, String source, String genreID, String time) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.genreID = genreID;
        this.time = time;
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

    /**
     * Setter for Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setter for Artist
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Getter for Source
     */
    public String getSource() {
        return source;
    }

    /**
     * Setter for Source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * Getter for GenreID
     */
    public String getGenreID() {
        return genreID;
    }

    /**
     * Setter for GenreID
     */
    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    /**
     * Getter for Time
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter for Time
     */
    public void setTime(String time) {
        this.time = time;
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