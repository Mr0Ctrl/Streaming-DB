package view;

import model.Video;
import model.Comment;
import controller.VideoController;
import controller.CommentController;
import controller.LikeController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VideoDetailFrame extends JFrame {
    private User user;

    public VideoDetailFrame(Video video, User user) {
        this.user = user;

        setTitle("Video Details");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());

        // Video detayları için panel
        JPanel detailsPanel = new JPanel(new GridLayout(5, 1, 5, 5)); // 4 -> 5 satır
        detailsPanel.add(new JLabel("Başlık: " + video.getTitle()));
        detailsPanel.add(new JLabel("Tür: " + video.getGenre()));
        detailsPanel.add(new JLabel("Açıklama: " + video.getDescription()));
        detailsPanel.add(new JLabel("Süre: " + video.getDuration() + " dk"));

        VideoController videoController = new VideoController();
        CommentController commentController = new CommentController();
        LikeController likeController = new LikeController();

        // Beğeni sayısı etiketi
        int likeCount = likeController.getLikeCount(video.getVideoID());
        JLabel likeCountLabel = new JLabel("Beğeni: " + likeCount);
        detailsPanel.add(likeCountLabel);

        panel.add(detailsPanel, BorderLayout.NORTH);

        // Yorumlar
        JTextArea commentsArea = new JTextArea();
        commentsArea.setEditable(false);

        List<Comment> comments = commentController.getCommentsByVideoID(video.getVideoID());

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

        // Beğenme/bırakma butonu
        boolean[] liked = {likeController.hasUserLikedVideo(user.getUserID(), video.getVideoID())};
        JButton likeButton = new JButton(liked[0] ? "Beğenmekten Vazgeç" : "Beğen");

        bottomPanel.add(commentPanel, BorderLayout.CENTER);
        bottomPanel.add(likeButton, BorderLayout.EAST);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        // Yorum yapma işlemi
        commentButton.addActionListener(e -> {
            String commentText = commentField.getText().trim();
            if (!commentText.isEmpty()) {
                Comment newComment = new Comment(user.getUserID(), video.getVideoID(), commentText);
                commentController.addComment(newComment);
                // Yorumları yeniden çek ve göster
                List<Comment> updatedComments = commentController.getCommentsByVideoID(video.getVideoID());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Yorumlar:\n");
                for (Comment c : updatedComments) {
                    sb2.append("- ").append(c.getContent()).append("\n");
                }
                commentsArea.setText(sb2.toString());
                commentField.setText("");
            }
        });

        // Beğen/bırak işlemi
        likeButton.addActionListener(e -> {
            if (liked[0]) {
                // Beğeniyi geri al
                likeController.unlikeVideo(user.getUserID(), video.getVideoID());
                liked[0] = false;
                likeButton.setText("Beğen");
                JOptionPane.showMessageDialog(this, "Beğenmekten vazgeçtiniz.");
            } else {
                likeController.likeVideo(user.getUserID(), video.getVideoID());
                liked[0] = true;
                likeButton.setText("Beğenmekten Vazgeç"); 
                JOptionPane.showMessageDialog(this, "Beğendiniz!");
            }
            // Beğeni sayısını güncelle
            int updatedLikeCount = likeController.getLikeCount(video.getVideoID());
            likeCountLabel.setText("Beğeni: " + updatedLikeCount);
        });

        add(panel);
    }
}