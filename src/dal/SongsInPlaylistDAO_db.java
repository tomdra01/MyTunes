package dal;

import be.Song;
import be.SongsInPlaylist;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongsInPlaylistDAO_db {
    private DatabaseConnector databaseConnector;

    public SongsInPlaylistDAO_db() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLException {
        SongsInPlaylistDAO_db songsInPlaylistDAO_db = new SongsInPlaylistDAO_db();
        //songsInPlaylistDAO_db.addSongsInPlaylist(1024,1145 );
        //List<SongsInPlaylist> allSongsInPlaylist =  songsInPlaylistDAO_db.getAllSongsInPlaylist(1024);
        //System.out.println(allSongsInPlaylist);
    }

    public List<SongsInPlaylist> getAllSongsInPlaylist(int playlistID){
        ArrayList<SongsInPlaylist> allSongsInPlaylist = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String insert = "'" + playlistID + "'";
            String sql = "SELECT * FROM SongsInPlaylist JOIN Songs ON SongsInPlaylist.SongID = Songs.SongID WHERE SongsInPlaylist.PlaylistID = " + insert + "";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    int songID = resultSet.getInt("SongID");
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");
                    String source = resultSet.getString("Source");

                    SongsInPlaylist songsInPlaylist = new SongsInPlaylist(playlistID, songID, title, artist, source);
                    allSongsInPlaylist.add(songsInPlaylist);
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allSongsInPlaylist;
    }

    public void addSongsInPlaylist(int playlistID, int songID) throws SQLException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + playlistID + "'" + "," + "'" + songID + "'";
            String sql = "INSERT INTO SongsInPlaylist (playlistID, songID) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }

    }


}
