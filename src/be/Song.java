package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String source;
    private int genreID;
    private String time;

    public Song(String title, String artist, String source, int genreID, String time) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.genreID = genreID;
        this.time = time;
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

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", source='" + source + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}