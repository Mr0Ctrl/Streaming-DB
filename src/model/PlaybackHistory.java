package model;

import java.time.LocalDateTime;

public class PlaybackHistory {
    private String historyID;
    private String userID;
    private String videoID;
    private LocalDateTime watchedAt;
    private int watchedDuration;
    private boolean isCompleted;

    public PlaybackHistory(String historyID, String userID, String videoID, LocalDateTime watchedAt, int watchedDuration, boolean isCompleted) {
        this.historyID = historyID;
        this.userID = userID;
        this.videoID = videoID;
        this.watchedAt = watchedAt;
        this.watchedDuration = watchedDuration;
        this.isCompleted = isCompleted;
    }

    public String getHistoryID() { return historyID; }
    public String getUserID() { return userID; }
    public String getVideoID() { return videoID; }
    public LocalDateTime getWatchedAt() { return watchedAt; }
    public int getWatchedDuration() { return watchedDuration; }
    public boolean isCompleted() { return isCompleted; }
}