package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String source;
    private int genreID;
    private String time;

    //Constructor
    public Song(String title, String artist, String source, int genreID, String time) {
        this.title = title;
        this.artist = artist;
        this.source = source;
        this.genreID = genreID;
        this.time = time;
    }

    //Gets title of the song
    public String getTitle() {
        return title;
    }

    //Sets title of the song
    public void setTitle(String title) {
        this.title = title;
    }

    //Gets artist of the song
    public String getArtist() {
        return artist;
    }

    //Sets artist of the song
    public void setArtist(String artist) {
        this.artist = artist;
    }

    //Gets time of the song
    public String getTime() {
        return time;
    }

    //Sets time of the song
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
