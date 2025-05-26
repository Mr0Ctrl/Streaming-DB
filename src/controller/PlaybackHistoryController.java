package controller;

import db.DatabaseConnection;
import model.PlaybackHistory;
import model.Video;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlaybackHistoryController {

    public void addHistory(String userID, String videoID) {
        String sql = "INSERT INTO PlaybackHistory (HistoryID, UserID, VideoID, WatchedAt, WatchedDuration, IsCompleted) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, UUID.randomUUID().toString());
            stmt.setString(2, userID);
            stmt.setString(3, videoID);
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, 0); // İzlenme süresi (isteğe bağlı)
            stmt.setBoolean(6, false); // Tamamlandı mı (isteğe bağlı)
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Video> getHistoryByUser(String userID) {
        List<Video> videos = new ArrayList<>();
        String sql = "SELECT v.* FROM PlaybackHistory h JOIN Video v ON h.VideoID = v.VideoID WHERE h.UserID = ? ORDER BY h.WatchedAt DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                videos.add(new Video(
                    rs.getString("VideoID"),
                    rs.getString("Title"),
                    rs.getString("Genre"),
                    rs.getString("Description"),
                    rs.getInt("Duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videos;
    }
}