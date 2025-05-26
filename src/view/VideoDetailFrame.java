package view;

import model.Video;
import model.Comment;
import controller.VideoController;
import controller.CommentController;
import controller.LikeController;
import model.User;
import model.Actor;
import controller.ActorController;
import controller.UserController; // Add this import
import controller.PlaybackHistoryController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VideoDetailFrame extends JFrame {
    private User user;

    public VideoDetailFrame(Video video, User user) {
        this.user = user;

        // İzleme geçmişine ekle
        new PlaybackHistoryController().addHistory(user.getUserID(), video.getVideoID());

        setTitle("Video Details");
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Main panel with BorderLayout
        JPanel panel = new JPanel(new BorderLayout());

        // Panel for video details (top section)
        JPanel detailsPanel = new JPanel(new GridLayout(6, 1, 3, 3));
        detailsPanel.add(new JLabel("Title: " + video.getTitle()));
        detailsPanel.add(new JLabel("Genre: " + video.getGenre()));
        detailsPanel.add(new JLabel("Description: " + video.getDescription()));
        detailsPanel.add(new JLabel("Duration: " + video.getDuration() + " min"));

        // Controllers
        VideoController videoController = new VideoController();
        CommentController commentController = new CommentController();
        LikeController likeController = new LikeController();
        ActorController actorController = new ActorController();
        UserController userController = new UserController(); // Add this

        // Like count label
        int likeCount = likeController.getLikeCount(video.getVideoID());
        JLabel likeCountLabel = new JLabel("Likes: " + likeCount);
        detailsPanel.add(likeCountLabel);

        // Panel for actors (as buttons)
        List<Actor> actors = actorController.getActorsByVideoID(video.getVideoID());
        JPanel actorsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actorsPanel.add(new JLabel("Actors:"));
        for (Actor actor : actors) {
            JButton actorButton = new JButton(actor.getFirstName() + " " + actor.getLastName());
            actorButton.addActionListener(e -> {
                // Open actor detail frame
                JFrame actorFrame = new ActorFrame(actor, user);
                actorFrame.setVisible(true);
            });
            actorsPanel.add(actorButton);
        }
        detailsPanel.add(actorsPanel);

        panel.add(detailsPanel, BorderLayout.NORTH);

        // Comments area (center section)
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));

        // Fetch and display comments as blocks
        List<Comment> comments = commentController.getCommentsByVideoID(video.getVideoID());
        for (Comment c : comments) {
            JPanel commentBlock = new JPanel();
            commentBlock.setLayout(new BorderLayout());
            commentBlock.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
            ));

            // Fetch username from userID (use only userID)
            String commenterUsername = c.getUserID();
            model.User commentUser = userController.getUserById(commenterUsername);
            if (commentUser != null) {
                commenterUsername = commentUser.getUsername();
            }

            String date = c.getCommentedAt().toString();

            JLabel userAndDateLabel = new JLabel(commenterUsername + " - " + date);
            userAndDateLabel.setFont(new Font("Arial", Font.BOLD, 12));
            commentBlock.add(userAndDateLabel, BorderLayout.NORTH);

            JTextArea contentArea = new JTextArea(c.getContent());
            contentArea.setLineWrap(true);
            contentArea.setWrapStyleWord(true);
            contentArea.setEditable(false);
            contentArea.setBackground(new Color(245, 245, 245));
            commentBlock.add(contentArea, BorderLayout.CENTER);

            commentsPanel.add(commentBlock);
            commentsPanel.add(Box.createVerticalStrut(5)); // Space between comments
        }

        JScrollPane commentsScrollPane = new JScrollPane(commentsPanel);
        commentsScrollPane.setBorder(BorderFactory.createTitledBorder("Comments"));
        panel.add(commentsScrollPane, BorderLayout.CENTER);

        // Bottom panel: comment input and like button
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Comment input area
        JPanel commentPanel = new JPanel(new BorderLayout());
        JTextField commentField = new JTextField();
        JButton commentButton = new JButton("Add Comment");
        commentPanel.add(commentField, BorderLayout.CENTER);
        commentPanel.add(commentButton, BorderLayout.EAST);

        // Like/unlike button
        boolean[] liked = {likeController.hasUserLikedVideo(user.getUserID(), video.getVideoID())};
        JButton likeButton = new JButton(liked[0] ? "Unlike" : "Like");

        bottomPanel.add(commentPanel, BorderLayout.CENTER);
        bottomPanel.add(likeButton, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Action for adding a comment
        commentButton.addActionListener(e -> {
            String commentText = commentField.getText().trim();
            if (!commentText.isEmpty()) {
                // Create and add new comment
                Comment newComment = new Comment(user.getUserID(), video.getVideoID(), commentText);
                commentController.addComment(newComment);

                // Refresh comments area as blocks
                List<Comment> updatedComments = commentController.getCommentsByVideoID(video.getVideoID());
                commentsPanel.removeAll();
                for (Comment c : updatedComments) {
                    JPanel commentBlock = new JPanel();
                    commentBlock.setLayout(new BorderLayout());
                    commentBlock.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(5, 5, 5, 5),
                        BorderFactory.createLineBorder(Color.LIGHT_GRAY)
                    ));

                    // Fetch username from userID (use only userID)
                    String commenterUsername = c.getUserID();
                    model.User commentUser = userController.getUserById(commenterUsername);
                    if (commentUser != null) {
                        commenterUsername = commentUser.getUsername();
                    }

                    String date = c.getCommentedAt().toString();

                    JLabel userAndDateLabel = new JLabel(commenterUsername + " - " + date);
                    userAndDateLabel.setFont(new Font("Arial", Font.BOLD, 12));
                    commentBlock.add(userAndDateLabel, BorderLayout.NORTH);

                    JTextArea contentArea = new JTextArea(c.getContent());
                    contentArea.setLineWrap(true);
                    contentArea.setWrapStyleWord(true);
                    contentArea.setEditable(false);
                    contentArea.setBackground(new Color(245, 245, 245));
                    commentBlock.add(contentArea, BorderLayout.CENTER);

                    commentsPanel.add(commentBlock);
                    commentsPanel.add(Box.createVerticalStrut(5));
                }
                commentsPanel.revalidate();
                commentsPanel.repaint();
                commentField.setText("");
            }
        });

        // Action for like/unlike
        likeButton.addActionListener(e -> {
            if (liked[0]) {
                // Unlike the video
                likeController.unlikeVideo(user.getUserID(), video.getVideoID());
                liked[0] = false;
                likeButton.setText("Like");
                JOptionPane.showMessageDialog(this, "You unliked this video.");
            } else {
                // Like the video
                likeController.likeVideo(user.getUserID(), video.getVideoID());
                liked[0] = true;
                likeButton.setText("Unlike");
                JOptionPane.showMessageDialog(this, "You liked this video!");
            }
            // Update like count label
            int updatedLikeCount = likeController.getLikeCount(video.getVideoID());
            likeCountLabel.setText("Likes: " + updatedLikeCount);
        });

        add(panel);
    }
}