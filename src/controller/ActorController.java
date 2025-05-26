package controller;

import model.Actor;
import model.Video;
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorController {

    public Actor getActorById(String actorId) {
        Actor actor = null;
        String query = "SELECT * FROM Actor WHERE ActorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, actorId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                actor = new Actor();
                actor.setActorID(resultSet.getString("ActorID"));
                actor.setFirstName(resultSet.getString("FirstName"));
                actor.setLastName(resultSet.getString("LastName"));
                actor.setDateOfBirth(resultSet.getDate("DateOfBirth").toLocalDate());
                actor.setBiography(resultSet.getString("Biography"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actor;
    }

    public List<Actor> getAllActors() {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT * FROM Actor";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Actor actor = new Actor();
                actor.setActorID(resultSet.getString("ActorID"));
                actor.setFirstName(resultSet.getString("FirstName"));
                actor.setLastName(resultSet.getString("LastName"));
                actor.setDateOfBirth(resultSet.getDate("DateOfBirth").toLocalDate());
                actor.setBiography(resultSet.getString("Biography"));
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public List<Video> getVideosByActorId(String actorId) {
        List<Video> videos = new ArrayList<>();
        String query = "SELECT v.VideoID, v.Title, v.Genre, v.Description, v.Duration " +
                       "FROM Video v " +
                       "JOIN Video_Actor va ON v.VideoID = va.VideoID " +
                       "WHERE va.ActorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, actorId);
            ResultSet rs = statement.executeQuery();
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

    public List<Actor> getActorsByVideoID(String videoId) {
        List<Actor> actors = new ArrayList<>();
        String query = "SELECT a.* FROM Actor a " +
                       "JOIN Video_Actor va ON a.ActorID = va.ActorID " +
                       "WHERE va.VideoID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, videoId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Actor actor = new Actor();
                actor.setActorID(rs.getString("ActorID"));
                actor.setFirstName(rs.getString("FirstName"));
                actor.setLastName(rs.getString("LastName"));
                actor.setDateOfBirth(rs.getDate("DateOfBirth").toLocalDate());
                actor.setBiography(rs.getString("Biography"));
                actors.add(actor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    public boolean addActor(Actor actor) {
        String query = "INSERT INTO Actor (ActorID, FirstName, LastName, DateOfBirth, Biography) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, actor.getActorID());
            statement.setString(2, actor.getFirstName());
            statement.setString(3, actor.getLastName());
            statement.setDate(4, java.sql.Date.valueOf(actor.getDateOfBirth()));
            statement.setString(5, actor.getBiography());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteActor(String actorId) {
        String query = "DELETE FROM Actor WHERE ActorID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, actorId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}