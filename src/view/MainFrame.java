package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
    private User currentUser;
    private JTabbedPane tabbedPane;
    private VideoPanel videoPanel;
    private ActorPanel actorPanel;
    private PlaylistPanel playlistPanel;

    public MainFrame(User user) {
        this.currentUser = user;
        setTitle("Streaming Platform");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sağ üstte kullanıcı adı ve logout butonu
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel userLabel = new JLabel("User: " + currentUser.getUsername());
        JButton logoutButton = new JButton("Logout");

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.add(userLabel);
        rightPanel.add(logoutButton);

        topPanel.add(rightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Logout işlemi
        logoutButton.addActionListener((ActionEvent e) -> {
            dispose();
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.resetFields();
            loginFrame.setVisible(true);
        });

        tabbedPane = new JTabbedPane();

        videoPanel = new VideoPanel();
        actorPanel = new ActorPanel();
        playlistPanel = new PlaylistPanel();

        tabbedPane.addTab("Videos", videoPanel);
        tabbedPane.addTab("Actors", actorPanel);
        tabbedPane.addTab("Playlists", playlistPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");

        logoutItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to handle logout
                System.out.println("User logged out.");
            }
        });

        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame(
                new User("1", "test@example.com", "username", "password")
            );
            mainFrame.setVisible(true);
        });
    }
}