package model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Comment {
    private String commentID;
    private String userID;
    private String videoID;
    private LocalDateTime commentedAt;
    private String content;
    private String parentCommentID;

    public Comment(String userID, String videoID, String content) {
        this(userID, videoID, content, null);
    }

    public Comment(String userID, String videoID, String content, String parentCommentID) {
        this.commentID = UUID.randomUUID().toString();
        this.userID = userID;
        this.videoID = videoID;
        this.commentedAt = LocalDateTime.now();
        this.content = content;
        this.parentCommentID = parentCommentID;
    }

    public Comment(String commentID, String userID, String videoID, LocalDateTime commentedAt, String content, String parentCommentID) {
        this.commentID = commentID;
        this.userID = userID;
        this.videoID = videoID;
        this.commentedAt = commentedAt;
        this.content = content;
        this.parentCommentID = parentCommentID;
    }

    public String getCommentID() {
        return commentID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParentCommentID() {
        return parentCommentID;
    }

    public void setParentCommentID(String parentCommentID) {
        this.parentCommentID = parentCommentID;
    }
}