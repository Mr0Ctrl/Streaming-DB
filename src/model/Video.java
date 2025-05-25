package model;

public class Video {
    private String videoID;
    private String title;
    private String genre;
    private String description;
    private int duration;

    public Video() {}
    public Video(String videoID, String title, String genre, String description, int duration) {
        this.videoID = videoID;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.duration = duration;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}