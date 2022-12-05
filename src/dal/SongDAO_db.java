package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_db {

    private DatabaseConnector databaseConnector;

    public SongDAO_db() {
        databaseConnector = new DatabaseConnector();
    }

    public List<Song> getAllSongs() throws SQLException {
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Songs;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String source = resultSet.getString("Source");
                    String artist = resultSet.getString("Artist");

                    Song song = new Song(title, source, artist);
                    allSongs.add(song);
                }
            }
        }
        return allSongs;
    }

    public Song createSong(String title, String artist, String source, int genereID) throws SQLException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + title + "'" + "," + "'" + artist + "'" + "," + "'" + source + "'" + "," + genereID;
            String sql = "INSERT INTO Songs (Title, Source, Artist, GenereID) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();

            statement.execute(sql);
        }
        return new Song(title, source, artist);
    }

    public static void main(String[] args) throws SQLException {
        SongDAO_db songDAO_db = new SongDAO_db();

        //songDAO_db.createSong("Just Dance", "songPathXXX", "Lady Gaga", 1);

        List<Song> allSongs = songDAO_db.getAllSongs();

        System.out.println(allSongs);
    }
}
