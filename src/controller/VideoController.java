package controller;

import model.Video;
import model.Comment;
import model.User;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoController {

    private Connection connection;

    public VideoController() {
        connection = DatabaseConnection.getConnection();
    }

    public Video getVideoDetails(String videoID) {
        Video video = null;
        String query = "SELECT * FROM Video WHERE VideoID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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

    public void likeVideo(String userID, String videoID) {
        String query = "INSERT INTO Liked (LikeID, UserID, VideoID, LikedAt) VALUES (?, ?, ?, NOW())";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, generateLikeID());
            stmt.setString(2, userID);
            stmt.setString(3, videoID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentsForVideo(String videoID) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Commented WHERE VideoID = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, videoID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCommentID(rs.getString("CommentID"));
                comment.setUserID(rs.getString("UserID"));
                comment.setVideoID(rs.getString("VideoID"));
                Timestamp ts = rs.getTimestamp("CommentedAt");
                if (ts != null) {
                    comment.setCommentedAt(ts.toLocalDateTime());
                }
                comment.setContent(rs.getString("Content"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public void addComment(String userID, String videoID, String content) {
        String query = "INSERT INTO Commented (CommentID, UserID, VideoID, CommentedAt, Content) VALUES (?, ?, ?, NOW(), ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, generateCommentID());
            stmt.setString(2, userID);
            stmt.setString(3, videoID);
            stmt.setString(4, content);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Video> getAllVideos() {
        List<Video> videos = new ArrayList<>();
        String query = "SELECT * FROM Video";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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

    private String generateLikeID() {
        // Logic to generate a unique LikeID
        return "like_" + System.currentTimeMillis();
    }

    private String generateCommentID() {
        // Logic to generate a unique CommentID
        return "comment_" + System.currentTimeMillis();
    }
}