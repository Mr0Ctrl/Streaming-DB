package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Video;
import controller.VideoController;
import java.util.List;
import model.User;

public class VideoPanel extends JPanel {
    private JLabel titleLabel;
    private JLabel genreLabel;
    private JTextArea descriptionArea;
    private JButton likeButton;
    private JButton commentButton;
    private JButton addToPlaylistButton;
    private JTextField commentField;
    private JPanel videosListPanel;
    private JButton nextButton, prevButton;
    private int currentPage = 0;
    private final int PAGE_SIZE = 5;
    private List<Video> allVideos;
    private User user;

    public VideoPanel(User user) {
        this.user = user;
        setLayout(new BorderLayout());

        // Video details section
        JPanel videoDetailsPanel = new JPanel();
        videoDetailsPanel.setLayout(new GridLayout(3, 1));

        // titleLabel = new JLabel("Video Title");
        // genreLabel = new JLabel("Genre: Action");
        // descriptionArea = new JTextArea("Video description goes here...");
        // descriptionArea.setLineWrap(true);
        // descriptionArea.setWrapStyleWord(true);
        // descriptionArea.setEditable(false);

        // videoDetailsPanel.add(titleLabel);
        // videoDetailsPanel.add(genreLabel);
        // videoDetailsPanel.add(new JScrollPane(descriptionArea));

        // add(videoDetailsPanel, BorderLayout.NORTH);

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

        // Video listesi paneli
        videosListPanel = new JPanel();
        videosListPanel.setLayout(new GridLayout(PAGE_SIZE, 1));
        add(new JScrollPane(videosListPanel), BorderLayout.CENTER);

        // Sayfalama butonları
        JPanel paginationPanel = new JPanel();
        prevButton = new JButton("Önceki");
        nextButton = new JButton("Sonraki");
        paginationPanel.add(prevButton);
        paginationPanel.add(nextButton);
        add(paginationPanel, BorderLayout.SOUTH);

        // VideoController ile videoları çek
        VideoController videoController = new VideoController();
        allVideos = videoController.getAllVideos(); // Bu metodu controller'da yazmalısınız

        // İlk sayfayı göster
        showPage(0);

        prevButton.addActionListener(e -> showPage(currentPage - 1));
        nextButton.addActionListener(e -> showPage(currentPage + 1));
    }

    private void showPage(int page) {
        if (page < 0 || page > allVideos.size() / PAGE_SIZE) return;
        currentPage = page;
        videosListPanel.removeAll();
        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, allVideos.size());
        for (int i = start; i < end; i++) {
            Video v = allVideos.get(i);
            JButton videoButton = new JButton(v.getTitle() + " (" + v.getGenre() + ")");
            videoButton.setHorizontalAlignment(SwingConstants.LEFT);
            videoButton.addActionListener(e -> {
                // Detay penceresini aç
                new VideoDetailFrame(v, user).setVisible(true);
            });
            videosListPanel.add(videoButton);
        }
        videosListPanel.revalidate();
        videosListPanel.repaint();
    }

    public void setVideoDetails(String title, String genre, String description) {
        titleLabel.setText(title);
        genreLabel.setText("Genre: " + genre);
        descriptionArea.setText(description);
    }
}