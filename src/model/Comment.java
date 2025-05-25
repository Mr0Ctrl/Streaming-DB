package model;

import java.time.LocalDateTime;

public class Comment {
    private String commentID;
    private String userID;
    private String videoID;
    private LocalDateTime commentedAt;
    private String content;
    private String parentCommentID;

    public Comment() {}
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

    public void setCommentID(String commentID) {
        this.commentID = commentID;
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

    public void setCommentedAt(LocalDateTime commentedAt) {
        this.commentedAt = commentedAt;
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