package be;

public class Song {
    private int id;

    private String title;

    private String artist;

    private String source;

    public Song(String title, String artist, String source) {
        this.title = title;
        this.artist = artist;
        this.source = source;
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

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
