package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideoPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel genreLabel;
    private JTextArea descriptionArea;
    private JButton likeButton;
    private JButton commentButton;
    private JButton addToPlaylistButton;
    private JTextField commentField;

    public VideoPanel() {
        setLayout(new BorderLayout());

        // Video details section
        JPanel videoDetailsPanel = new JPanel();
        videoDetailsPanel.setLayout(new GridLayout(3, 1));

        titleLabel = new JLabel("Video Title");
        genreLabel = new JLabel("Genre: Action");
        descriptionArea = new JTextArea("Video description goes here...");
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);

        videoDetailsPanel.add(titleLabel);
        videoDetailsPanel.add(genreLabel);
        videoDetailsPanel.add(new JScrollPane(descriptionArea));

        add(videoDetailsPanel, BorderLayout.NORTH);

        // Interaction buttons
        JPanel buttonPanel = new JPanel();
        likeButton = new JButton("Like");
        commentButton = new JButton("Comment");
        addToPlaylistButton = new JButton("Add to Playlist");

        buttonPanel.add(likeButton);
        buttonPanel.add(commentButton);
        buttonPanel.add(addToPlaylistButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Comment input
        commentField = new JTextField("Enter your comment here...");
        add(commentField, BorderLayout.SOUTH);

        // Action listeners
        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle like action
                System.out.println("Video liked!");
            }
        });

        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle comment action
                String comment = commentField.getText();
                System.out.println("Comment added: " + comment);
                commentField.setText("");
            }
        });

        addToPlaylistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle add to playlist action
                System.out.println("Video added to playlist!");
            }
        });
    }

    public void setVideoDetails(String title, String genre, String description) {
        titleLabel.setText(title);
        genreLabel.setText("Genre: " + genre);
        descriptionArea.setText(description);
    }
}