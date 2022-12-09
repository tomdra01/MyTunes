package dal;

import be.Playlist;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO {
    private DatabaseConnector databaseConnector;
    public PlaylistDAO() {
        databaseConnector = new DatabaseConnector();
    }

    public static void main(String[] args) throws SQLException {
        PlaylistDAO playlistDAO = new PlaylistDAO();
        playlistDAO.deletePlaylist(" ");
        playlistDAO.createPlaylist(" ");
        List<Playlist> allPlaylist = playlistDAO.getAllPlaylist();
    }

    public Playlist createPlaylist(String name) throws SQLException {
        try (Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + name + "'";
            String sql = "INSERT INTO Playlist (Name) VALUES (" + insert + ")";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }
        return new Playlist(name);
    }

    public List<Playlist> getAllPlaylist() {
        ArrayList<Playlist> allPlaylist = new ArrayList<>();
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Playlist;";

            Statement statement = connection.createStatement();
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    String name = resultSet.getString("Name");
                    Playlist playlist = new Playlist(name);
                    allPlaylist.add(playlist);
                }
            }
        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPlaylist;
    }

    public void deletePlaylist(String name) {
        String sql = "DELETE FROM Playlist WHERE Name= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}