package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Playlist;
import controller.PlaylistController;

public class PlaylistPanel extends JPanel {
    private JList<Playlist> playlistList;
    private DefaultListModel<Playlist> playlistListModel;
    private JButton addButton;
    private JButton removeButton;
    private PlaylistController playlistController;

    public PlaylistPanel() {
        playlistController = new PlaylistController();
        playlistListModel = new DefaultListModel<>();
        playlistList = new JList<>(playlistListModel);
        addButton = new JButton("Add Video to Playlist");
        removeButton = new JButton("Remove Playlist");

        setLayout(new BorderLayout());
        add(new JScrollPane(playlistList), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loadPlaylists();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVideoToPlaylist();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePlaylist();
            }
        });
    }

    private void loadPlaylists() {
        List<Playlist> playlists = playlistController.getAllPlaylists();
        playlistListModel.clear();
        for (Playlist playlist : playlists) {
            playlistListModel.addElement(playlist);
        }
    }

    private void addVideoToPlaylist() {
        Playlist selectedPlaylist = playlistList.getSelectedValue();
        if (selectedPlaylist != null) {
            // Logic to add video to the selected playlist
            // This could open a dialog to select a video
        } else {
            JOptionPane.showMessageDialog(this, "Please select a playlist.");
        }
    }

    private void removePlaylist() {
        Playlist selectedPlaylist = playlistList.getSelectedValue();
        if (selectedPlaylist != null) {
            playlistController.removePlaylist(selectedPlaylist);
            loadPlaylists();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a playlist to remove.");
        }
    }
}