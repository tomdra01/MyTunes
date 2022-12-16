package be;

public class SongsInPlaylist {
    private int songID;
    private int playlistID;
    private String title;
    private String artist;
    private String source;

    public SongsInPlaylist( int playlistID,int songID, String title, String artist, String source) {
        this.playlistID = playlistID;
        this.songID = songID;
        this.title = title;
        this.artist = artist;
        this.source = source;
    }

    public int getSongID() {
        return songID;
    }

    public int getPlaylistID() {
        return playlistID;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getArtist(){
        return artist;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public String getSource(){
        return  source;
    }
    public void setSource(){
        this.source = source;
    }
    public String toString() {
        return "Playlist{" +
                " playlistID ='" + playlistID + '\'' +
        " songID ='" + songID + '\'' +
        " title ='" + title+ '\'' +
        " artist ='" + artist + '\'' +
        " source ='" + source + '\'' +
    '}';

}
}

