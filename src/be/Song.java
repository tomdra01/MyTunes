package be;

public class Song {
    private int id;

    private String title;

    private String artist;

    private String source;

    public Song(String title, String source, String artist) {
        this.title = title;
        this.source = source;
        this.artist = artist;
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
                ", source='" + source + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
