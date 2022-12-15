package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String source;
    private String genreID;
    private String time;

    public Song(String title, String artist, String source, String genreID, String time) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.genreID = genreID;
        this.time = time;
    }

    public Song(int id, String title, String artist, String source, String genreID, String time) {
        this(title, artist,source,genreID,time);
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGenreID() {
        return genreID;
    }

    public void setGenreID(String genreID) {
        this.genreID = genreID;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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