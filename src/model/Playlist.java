package model;

import java.time.LocalDate;

public class Playlist {
    private String playlistID;
    private String name;
    private String userID;
    private String description;
    private LocalDate createdOn;
    private boolean isPublic;

    public Playlist() {}
    public Playlist(String playlistID, String name, String userID, String description, LocalDate createdOn, boolean isPublic) {
        this.playlistID = playlistID;
        this.name = name;
        this.userID = userID;
        this.description = description;
        this.createdOn = createdOn;
        this.isPublic = isPublic;
    }

    public String getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(String playlistID) {
        this.playlistID = playlistID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}