package controller;

import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Video;
import java.util.ArrayList;
import java.util.List;

public class LikeController {

    public boolean hasUserLikedVideo(String userID, String videoID) {
        String query = "SELECT 1 FROM Liked WHERE UserID = ? AND VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setString(2, videoID);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean likeVideo(String userID, String videoID) {
        if (hasUserLikedVideo(userID, videoID)) {
            return false; // Zaten beğenmiş
        }
        String query = "INSERT INTO Liked (LikeID, UserID, VideoID, LikedAt) VALUES (?, ?, ?, NOW())";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, java.util.UUID.randomUUID().toString());
            stmt.setString(2, userID);
            stmt.setString(3, videoID);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getLikeCount(String videoID) {
        String query = "SELECT COUNT(*) FROM Liked WHERE VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, videoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void unlikeVideo(String userID, String videoID) {
        String query = "DELETE FROM Liked WHERE UserID = ? AND VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            stmt.setString(2, videoID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Video> getLikedVideosByUser(String userID) {
        List<Video> likedVideos = new ArrayList<>();
        String query = "SELECT v.* FROM Liked l JOIN Video v ON l.VideoID = v.VideoID WHERE l.UserID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Video video = new Video(
                    rs.getString("VideoID"),
                    rs.getString("Title"),
                    rs.getString("Genre"),
                    rs.getString("Description"),
                    rs.getInt("Duration")
                );
                likedVideos.add(video);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likedVideos;
    }
}