package controller;

import model.Actor;
import model.Video; // Dosyanın başına ekleyin
import db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActorController {

    private Connection connection;

    public ActorController() {
        connection = DatabaseConnection.getConnection();
    }

    public Actor getActorById(String actorId) {
        Actor actor = null;
        String query = "SELECT * FROM Actor WHERE ActorID = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
        
        try (Statement statement = connection.createStatement()) {
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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
}