package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Actor;
import model.Video;
import controller.ActorController;

public class ActorPanel extends JPanel {
    private JLabel actorNameLabel;
    private JTextArea actorBioTextArea;
    private JList<Video> videoList;
    private DefaultListModel<Video> videoListModel;
    private ActorController actorController;

    public ActorPanel() {}
    
    public ActorPanel(Actor actor) {
        actorController = new ActorController();
        setLayout(new BorderLayout());

        // Actor Name
        actorNameLabel = new JLabel(actor.getFirstName() + " " + actor.getLastName());
        actorNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(actorNameLabel, BorderLayout.NORTH);

        // Actor Biography
        actorBioTextArea = new JTextArea(actor.getBiography());
        actorBioTextArea.setEditable(false);
        actorBioTextArea.setLineWrap(true);
        actorBioTextArea.setWrapStyleWord(true);
        add(new JScrollPane(actorBioTextArea), BorderLayout.CENTER);

        // Video List
        videoListModel = new DefaultListModel<>();
        videoList = new JList<>(videoListModel);
        videoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(videoList), BorderLayout.SOUTH);

        // Load videos featuring the actor
        loadVideos(actor.getActorID());
    }

    private void loadVideos(String actorID) {
        List<Video> videos = actorController.getVideosByActorId(actorID);
        for (Video video : videos) {
            videoListModel.addElement(video);
        }
    }
}