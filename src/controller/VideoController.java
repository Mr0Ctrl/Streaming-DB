package controller;

import model.Video;
import model.Comment;
import model.User;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoController {

    public Video getVideoDetails(String videoID) {
        Video video = null;
        String query = "SELECT * FROM Video WHERE VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, videoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                video = new Video();
                video.setVideoID(rs.getString("VideoID"));
                video.setTitle(rs.getString("Title"));
                video.setGenre(rs.getString("Genre"));
                video.setDescription(rs.getString("Description"));
                video.setDuration(rs.getInt("Duration"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return video;
    }

    public List<Video> getAllVideos() {
        List<Video> videos = new ArrayList<>();
        String query = "SELECT * FROM Video";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Video video = new Video();
                video.setVideoID(rs.getString("VideoID"));
                video.setTitle(rs.getString("Title"));
                video.setGenre(rs.getString("Genre"));
                video.setDescription(rs.getString("Description"));
                video.setDuration(rs.getInt("Duration"));
                videos.add(video);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videos;
    }
}