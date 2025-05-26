package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Actor;
import model.Video;
import controller.ActorController;

public class ActorFrame extends JFrame {
    private JLabel actorNameLabel;
    private JTextArea actorBioTextArea;
    private JList<Video> videoList;
    private DefaultListModel<Video> videoListModel;
    private ActorController actorController;

    public ActorFrame(Actor actor, model.User user) {
        actorController = new ActorController();
        setTitle(actor.getFirstName() + " " + actor.getLastName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JPanel contentPanel = new JPanel(new BorderLayout());

        // Actor Name
        actorNameLabel = new JLabel(actor.getFirstName() + " " + actor.getLastName());
        actorNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        contentPanel.add(actorNameLabel, BorderLayout.NORTH);

        // Actor Biography
        actorBioTextArea = new JTextArea(actor.getBiography());
        actorBioTextArea.setEditable(false);
        actorBioTextArea.setLineWrap(true);
        actorBioTextArea.setWrapStyleWord(true);
        contentPanel.add(new JScrollPane(actorBioTextArea), BorderLayout.CENTER);

        // Video Button Panel
        JPanel videosPanel = new JPanel();
        videosPanel.setLayout(new BoxLayout(videosPanel, BoxLayout.Y_AXIS));
        List<Video> videos = actorController.getVideosByActorId(actor.getActorID());
        for (Video video : videos) {
            JButton videoButton = new JButton(video.getTitle());
            videoButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            videoButton.addActionListener(e -> {
                JFrame videoDetailFrame = new VideoDetailFrame(video, user);
                videoDetailFrame.setVisible(true);
            });
            videosPanel.add(videoButton);
        }
        JScrollPane videosScrollPane = new JScrollPane(videosPanel);
        videosScrollPane.setBorder(BorderFactory.createTitledBorder("Oynadığı Filmler"));
        contentPanel.add(videosScrollPane, BorderLayout.SOUTH);

        setContentPane(contentPanel);
    }
}