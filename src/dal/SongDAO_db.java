package dal;

import be.Song;

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
                    int id = resultSet.getInt("SongID");
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");

                    Song song = new Song(id, title, artist);
                    allSongs.add(song);
                }
            }
        }
        return allSongs;
    }

    public static void main(String[] args) throws SQLException {
        SongDAO_db songDAO_db = new SongDAO_db();

        List<Song> allSongs = songDAO_db.getAllSongs();

        System.out.println(allSongs);
    }
}
