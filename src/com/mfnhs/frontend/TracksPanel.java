package com.mfnhs.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class TracksPanel extends JPanel {

    public TracksPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JLabel heading = new JLabel("Tracks");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 16f));
        add(heading, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.add(new JLabel("(Tracks management UI goes here)"));
        add(body, BorderLayout.CENTER);

        JButton back = new JButton("Back");
        back.addActionListener(e -> navigator.accept(App.CARD_DASHBOARD));
        add(back, BorderLayout.SOUTH);
    }
}
