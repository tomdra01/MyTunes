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
        //playlistDAO.deletePlaylist(" ");
       // playlistDAO.createPlaylist(" ");
        List<Playlist> allPlaylist = playlistDAO.getAllPlaylist();
    }

    public Playlist createPlaylist(String name) throws SQLException {
        try (Connection connection = databaseConnector.getConnection()) {
            String insert = "'" + name + "'";
            String sql = "INSERT INTO Playlist (Name) VALUES (" + insert + ")";
            Statement statement = connection.createStatement();
            statement.execute(sql,Statement.RETURN_GENERATED_KEYS);

            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            return new Playlist(id, name);
        }

    }

    public List<Playlist> getAllPlaylist() {
        ArrayList<Playlist> allPlaylist = new ArrayList<>();
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "SELECT * FROM Playlist;";

            Statement statement = connection.createStatement();
            if (statement.execute(sql)) {
                ResultSet resultSet = statement.getResultSet();
                while (resultSet.next()) {
                    int id = resultSet.getInt("PlaylistID");
                    String name = resultSet.getString("Name");
                    Playlist playlist = new Playlist(id, name);
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

    public void deletePlaylist(int id) {
        String sql = "DELETE FROM Playlist WHERE PlaylistID= ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void editPlaylist(String name, String newName) {
        PreparedStatement pstmt=null;

        try(Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Playlist " +
                    "SET Name=? " +
                    "WHERE Name=?";

            pstmt=connection.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

        } catch (SQLServerException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}