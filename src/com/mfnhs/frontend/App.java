package com.mfnhs.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class App {
    public static final String CARD_LOGIN = "LOGIN";
    public static final String CARD_DASHBOARD = "DASHBOARD";
    public static final String CARD_ENROLL = "ENROLL";
    public static final String CARD_MASTER = "MASTERLIST";
    public static final String CARD_SECTIONS = "SECTIONS";
    public static final String CARD_TRACKS = "TRACKS";

    private JFrame frame;
    private JPanel container;
    private CardLayout cards;

    public App() {
        frame = new JFrame("Front The Stage - Desktop (Swing)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Make window static: do not allow user to resize; content uses fixed preferred sizes
        frame.setResizable(false);

        // Apply global theme defaults
        Theme.applyUIDefaults();

        cards = new CardLayout();
        container = new JPanel(cards);
            // Keep the card container a fixed viewport so cards do not stretch when the frame is resized
            // Increased size for accessibility (older users)
            container.setPreferredSize(new Dimension(1200, 780));

        // Root panel uses GridBagLayout to center the fixed-size container
        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(Theme.OUTER_BG);
        GridBagConstraints gbcRoot = new GridBagConstraints();
        gbcRoot.gridx = 0;
        gbcRoot.gridy = 0;
        gbcRoot.anchor = GridBagConstraints.CENTER;
        gbcRoot.fill = GridBagConstraints.NONE; // do not stretch
        root.add(container, gbcRoot);

        Consumer<String> nav = this::showCard;

        // create panels
        LoginPanel login = new LoginPanel(nav);
        DashboardPanel dashboard = new DashboardPanel(nav);
        EnrollPanel enroll = new EnrollPanel(nav);
        MasterlistPanel master = new MasterlistPanel(nav);
        SectionsPanel sections = new SectionsPanel(nav);
        TracksPanel tracks = new TracksPanel(nav);

        container.add(login, CARD_LOGIN);
        container.add(dashboard, CARD_DASHBOARD);
        container.add(enroll, CARD_ENROLL);
        container.add(master, CARD_MASTER);
        container.add(sections, CARD_SECTIONS);
        container.add(tracks, CARD_TRACKS);

        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        showCard(CARD_LOGIN);
    }

    private void showCard(String name) {
        SwingUtilities.invokeLater(() -> cards.show(container, name));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}

