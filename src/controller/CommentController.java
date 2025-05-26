package controller;

import model.Comment;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentController {

    public void addComment(Comment comment) {
        String query = "INSERT INTO Commented (CommentID, UserID, VideoID, CommentedAt, Content, ParentCommentID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, comment.getCommentID());
            pstmt.setString(2, comment.getUserID());
            pstmt.setString(3, comment.getVideoID());
            pstmt.setTimestamp(4, Timestamp.valueOf(comment.getCommentedAt()));
            pstmt.setString(5, comment.getContent());
            pstmt.setString(6, comment.getParentCommentID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Comment> getCommentsByVideoID(String videoID) {
        List<Comment> comments = new ArrayList<>();
        String query = "SELECT * FROM Commented WHERE VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, videoID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(
                    rs.getString("CommentID"),
                    rs.getString("UserID"),
                    rs.getString("VideoID"),
                    rs.getTimestamp("CommentedAt").toLocalDateTime(),
                    rs.getString("Content"),
                    rs.getString("ParentCommentID")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}