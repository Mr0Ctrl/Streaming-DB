package view;

import model.User;
import model.Video;
import controller.PlaybackHistoryController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WatchHistoryPanel extends JPanel {
    private User user;
    private PlaybackHistoryController historyController;
    private JPanel videosPanel;

    public WatchHistoryPanel(User user) {
        this.user = user;
        this.historyController = new PlaybackHistoryController();
        setLayout(new BorderLayout());

        videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(videosPanel), BorderLayout.CENTER);

        loadHistory();
    }

    private void loadHistory() {
        videosPanel.removeAll();
        List<Video> watchedVideos = historyController.getHistoryByUser(user.getUserID());
        for (Video video : watchedVideos) {
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
        loadHistory();
    }
}