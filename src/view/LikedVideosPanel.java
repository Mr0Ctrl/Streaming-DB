package view;

import model.User;
import model.Video;
import controller.LikeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LikedVideosPanel extends JPanel {
    private User user;
    private LikeController likeController;
    private JPanel videosPanel;

    public LikedVideosPanel(User user) {
        this.user = user;
        this.likeController = new LikeController();
        setLayout(new BorderLayout());

        videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(videosPanel), BorderLayout.CENTER);

        loadLikedVideos();
    }

    private void loadLikedVideos() {
        videosPanel.removeAll();
        List<Video> likedVideos = likeController.getLikedVideosByUser(user.getUserID());
        for (Video video : likedVideos) {
            JButton videoButton = new JButton(video.getTitle() + " (" + video.getGenre() + ")");
            videoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            videoButton.addActionListener(e -> {
                new VideoDetailFrame(video, user).setVisible(true);
            });
            videosPanel.add(videoButton);
            videosPanel.add(Box.createVerticalStrut(5));
        }
        videosPanel.revalidate();
        videosPanel.repaint();
    }

    public void refresh() {
        loadLikedVideos();
    }
}