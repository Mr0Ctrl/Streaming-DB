package view;

import model.Video;
import model.Comment;
import controller.VideoController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VideoDetailFrame extends JFrame {
    private User user;

    public VideoDetailFrame(Video video, User user) {
        this.user = user;

        setTitle("Video Details");
        setSize(400, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Video detayları
        JTextArea detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setText(
                "Başlık: " + video.getTitle() + "\n" +
                "Tür: " + video.getGenre() + "\n" +
                "Açıklama: " + video.getDescription() + "\n" +
                "Süre: " + video.getDuration() + " dk"
        );
        panel.add(detailsArea, BorderLayout.NORTH);

        // Yorumlar
        JTextArea commentsArea = new JTextArea();
        commentsArea.setEditable(false);

        VideoController controller = new VideoController();
        List<Comment> comments = controller.getCommentsForVideo(video.getVideoID());
        StringBuilder sb = new StringBuilder();
        sb.append("Yorumlar:\n");
        for (Comment c : comments) {
            sb.append("- ").append(c.getContent()).append("\n");
        }
        commentsArea.setText(sb.toString());
        panel.add(new JScrollPane(commentsArea), BorderLayout.CENTER);

        // Alt panel: Yorum yapma ve beğenme
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Yorum yapma alanı
        JPanel commentPanel = new JPanel(new BorderLayout());
        JTextField commentField = new JTextField();
        JButton commentButton = new JButton("Yorum Yap");
        commentPanel.add(commentField, BorderLayout.CENTER);
        commentPanel.add(commentButton, BorderLayout.EAST);

        // Beğenme butonu
        JButton likeButton = new JButton("Beğen");

        bottomPanel.add(commentPanel, BorderLayout.CENTER);
        bottomPanel.add(likeButton, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Yorum yapma işlemi
        commentButton.addActionListener(e -> {
            String commentText = commentField.getText().trim();
            if (!commentText.isEmpty()) {
                controller.addComment(user.getUserID(), video.getVideoID(), commentText);
                // Yorumları yeniden çek ve göster
                List<Comment> updatedComments = controller.getCommentsForVideo(video.getVideoID());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Yorumlar:\n");
                for (Comment c : updatedComments) {
                    sb2.append("- ").append(c.getContent()).append("\n");
                }
                commentsArea.setText(sb2.toString());
                commentField.setText("");
            }
        });

        // Beğenme işlemi
        likeButton.addActionListener(e -> {
            controller.likeVideo(user.getUserID(), video.getVideoID());
            JOptionPane.showMessageDialog(this, "Beğendiniz!");
        });

        add(panel);
    }
}