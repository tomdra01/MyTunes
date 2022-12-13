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

    public static void main(String[] args) throws SQLException {
        SongDAO_db songDAO_db = new SongDAO_db();
        //songDAO_db.deleteSong(" ");
        //songDAO_db.createSong(" ", " ", " ", " ", " ");
        //List<Song> allSongs = songDAO_db.getAllSongs();
        //songDAO_db.editSong("Cult", "NewCult", "NewSlayer", "2");
    }

    public List<Song> getAllSongs(){
        ArrayList<Song> allSongs = new ArrayList<>();
        try(Connection connection = databaseConnector.getConnection()){
            String sql = "SELECT * FROM Songs JOIN Genere ON Genere.GenereID = Songs.GenereID";

            Statement statement = connection.createStatement();

            if(statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while(resultSet.next()) {
                    String title = resultSet.getString("Title");
                    String artist = resultSet.getString("Artist");
                    String source = resultSet.getString("Source");
                    String genreID = resultSet.getString("Genere");
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

    public Song createSong(String title, String artist, String source, String genreID, String time) throws SQLException {
        try(Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + title + "'" + "," + "'" + source + "'" + "," + "'" + artist + "'" + "," + 2 + "," + "'" + time + "'";
            String sql = "INSERT INTO Songs (Title,Source, Artist, GenereID, Time) VALUES (" + insert + ")";

            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        return new Song(title, artist,source, genreID, time);
    }

    public void deleteSong(String song) {
        String sql = "DELETE FROM Songs WHERE Title= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, song);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editSong(String title, String newTitle, String newArtist, String newGenreID) {
        PreparedStatement pstmt=null;

        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Songs " +
                    "SET Title=?, Artist =?, GenereID =? " +
                    "WHERE Title=?";

            pstmt=connection.prepareStatement(sql);
            pstmt.setString(1, newTitle);
            pstmt.setString(2, newArtist);
            pstmt.setString(3, newGenreID);
            pstmt.setString(4, title);
            pstmt.executeUpdate();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}