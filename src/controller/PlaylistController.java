package controller;

import model.Playlist;
import model.Video;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistController {

    private Connection connection;

    public PlaylistController() {
        connection = DatabaseConnection.getConnection();
    }

    public void createPlaylist(Playlist playlist) {
        String sql = "INSERT INTO Playlist (PlaylistID, Name, UserID, Description, CreatedOn, IsPublic) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlist.getPlaylistID());
            pstmt.setString(2, playlist.getName());
            pstmt.setString(3, playlist.getUserID());
            pstmt.setString(4, playlist.getDescription());
            pstmt.setDate(5, java.sql.Date.valueOf(playlist.getCreatedOn()));
            pstmt.setBoolean(6, playlist.isPublic());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Playlist> getPlaylistsByUser(String userID) {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlist WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, userID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setPlaylistID(rs.getString("PlaylistID"));
                playlist.setName(rs.getString("Name"));
                playlist.setUserID(rs.getString("UserID"));
                playlist.setDescription(rs.getString("Description"));
                playlist.setCreatedOn(rs.getDate("CreatedOn").toLocalDate());
                playlist.setPublic(rs.getBoolean("IsPublic"));
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public void addVideoToPlaylist(String playlistID, Video video) {
        String sql = "INSERT INTO PlaylistReferenceVideo (PlaylistID, VideoID, VideoOrder) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlistID);
            pstmt.setString(2, video.getVideoID());
            pstmt.setInt(3, getNextVideoOrder(playlistID));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getNextVideoOrder(String playlistID) {
        String sql = "SELECT COUNT(*) FROM PlaylistReferenceVideo WHERE PlaylistID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, playlistID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1; // Default to 1 if no videos are present
    }

    public List<Playlist> getAllPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlist";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Playlist playlist = new Playlist();
                playlist.setPlaylistID(rs.getString("PlaylistID"));
                playlist.setName(rs.getString("Name"));
                playlist.setUserID(rs.getString("UserID"));
                playlist.setDescription(rs.getString("Description"));
                playlist.setCreatedOn(rs.getDate("CreatedOn").toLocalDate());
                playlist.setPublic(rs.getBoolean("IsPublic"));
                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    public void removePlaylist(Playlist selectedPlaylist) {
        String sql = "DELETE FROM Playlist WHERE PlaylistID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, selectedPlaylist.getPlaylistID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}