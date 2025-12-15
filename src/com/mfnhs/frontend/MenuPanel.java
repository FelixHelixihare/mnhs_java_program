package com.mfnhs.frontend;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Consumer;

public class MenuPanel extends JPanel {

    private static final Color ACCENT = Theme.ACCENT;

    public MenuPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout());
        setBackground(Theme.PANEL_WHITE);
        setBorder(new LineBorder(ACCENT, 2, true));
        // fixed size for static layout
        setPreferredSize(new Dimension(320, 560));

        JLabel title = new JLabel("MENU");
        title.setBorder(new EmptyBorder(8, 12, 8, 12));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        title.setForeground(ACCENT.darker());
        add(title, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(Color.WHITE);
        body.setBorder(new EmptyBorder(12,12,12,12));

        body.add(createBtn("DASHBOARD", e -> navigator.accept(App.CARD_DASHBOARD)));
        body.add(Box.createRigidArea(new Dimension(0,10)));
        body.add(createBtn("MASTERLIST", e -> navigator.accept(App.CARD_MASTER)));
        body.add(Box.createRigidArea(new Dimension(0,12)));

        // Strand/Track management button (Track is a subsection of Strand)
        body.add(createBtn("Strand / Tracks", e -> navigator.accept(App.CARD_SECTIONS)));
        body.add(Box.createRigidArea(new Dimension(0,12)));

        body.add(createBtn("PRINT", e -> {/* TODO: print action */}));
        body.add(Box.createRigidArea(new Dimension(0,8)));
        body.add(createBtn("HELP", e -> {/* TODO: help */}));
        body.add(Box.createRigidArea(new Dimension(0,8)));
        body.add(createBtn("LOGOUT", e -> System.exit(0)));

        add(body, BorderLayout.CENTER);
    }

    private JButton createBtn(String text, java.awt.event.ActionListener action) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        b.setPreferredSize(new Dimension(220, 36));
        b.setBackground(new Color(238,238,238));
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(new Color(200,230,200), 2, true));
        b.addActionListener(action);
        return b;
    }
}
