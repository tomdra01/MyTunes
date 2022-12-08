package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_db {

    private DatabaseConnector databaseConnector;

    public SongDAO_db() {
        databaseConnector = new DatabaseConnector();
    }

    public List<Song> getAllSongs(){
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Songs;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");
                    String source = resultSet.getString("Source");
                    Integer genreID = resultSet.getInt("genereID");
                    String time = resultSet.getString("Time");

                    Song song = new Song(title, artist, source, genreID, time);
                    allSongs.add(song);
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allSongs;
    }

    public Song createSong(String title, String artist, String source, int genreID, String time) throws SQLException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + title + "'" + "," + "'" + artist + "'" + "," + "'" + source + "'" + "," + genreID + "," + "'" + time + "'";
            String sql = "INSERT INTO Songs (Title, Source, Artist, GenereID, Time) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();

            statement.execute(sql);
        }
        return new Song(title, source, artist, genreID, time);
    }

    public void delete(int id) {
        String sql = "DELETE FROM Songs WHERE SongID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        SongDAO_db songDAO_db = new SongDAO_db();

        //songDAO_db.createSong("Just Dance", "Lady Gaga", "songPathXXX", 1, "1");

        List<Song> allSongs = songDAO_db.getAllSongs();

        System.out.println(allSongs);
    }
}
