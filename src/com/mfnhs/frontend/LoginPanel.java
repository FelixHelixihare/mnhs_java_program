package com.mfnhs.frontend;

import com.mfnhs.Main;
import com.mfnhs.backend.data.AuthResult;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.function.Consumer;

public class LoginPanel extends JPanel {

    // Use theme colors
    private static final Color BG = Theme.OUTER_BG;
    private static final Color CARD = Theme.CARD_BG;
    private static final Color ACCENT = Theme.ACCENT;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private Window parent = SwingUtilities.getWindowAncestor(this);

    public LoginPanel(Consumer<String> navigator) {
        setLayout(new GridBagLayout());
        setBackground(BG);

        RoundedCard card = new RoundedCard();
        card.setLayout(new GridBagLayout());
        card.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 24, 12, 24);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Header label (inside rounded card)
        JLabel header = new JLabel("STUDENT PORTAL LOGIN", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(ACCENT.darker());
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 26f));
        header.setBorder(new EmptyBorder(12, 12, 12, 12));
        // larger header size for accessibility
        header.setPreferredSize(new Dimension(760, 72));
        gbc.weightx = 1.0;
        card.add(header, gbc);

        gbc.gridy++;
        // inner spacing panel
        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        inner.setBorder(new EmptyBorder(18, 36, 18, 36));

        JLabel welcome = new JLabel("WELCOME!", SwingConstants.CENTER);
        welcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 24f));
        welcome.setForeground(new Color(39, 115, 41));
        inner.add(welcome);
        inner.add(Box.createRigidArea(new Dimension(0, 18)));

        // User ID label and input (larger for accessibility)
        JLabel userLabel = new JLabel("User ID");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));
        inner.add(userLabel);
        inner.add(Box.createRigidArea(new Dimension(0, 6)));

        usernameField = new PlaceholderTextField("Enter your User ID");
        usernameField.setMaximumSize(new Dimension(760, 56));
        usernameField.setPreferredSize(new Dimension(760, 56));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameField.setFont(usernameField.getFont().deriveFont(18f));
        usernameField.setBorder(new CompoundBorder(new LineBorder(ACCENT, 2, true), new EmptyBorder(8, 12, 8, 12)));
        usernameField.setToolTipText("User ID");
        usernameField.setBackground(new Color(252,252,250));
        inner.add(usernameField);
        inner.add(Box.createRigidArea(new Dimension(0, 12)));

        JLabel passLabel = new JLabel("Password");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLabel.setFont(passLabel.getFont().deriveFont(Font.BOLD, 14f));
        inner.add(passLabel);
        inner.add(Box.createRigidArea(new Dimension(0, 6)));

        passwordField = new PlaceholderPasswordField("Enter your Password");
        passwordField.setMaximumSize(new Dimension(760, 56));
        passwordField.setPreferredSize(new Dimension(760, 56));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordField.setFont(passwordField.getFont().deriveFont(18f));
        passwordField.setBorder(new CompoundBorder(new LineBorder(ACCENT, 2, true), new EmptyBorder(8, 12, 8, 12)));
        passwordField.setBackground(new Color(252,252,250));
        inner.add(passwordField);
        inner.add(Box.createRigidArea(new Dimension(0, 18)));

        // Prominent login button
        JButton login = new JButton("LOG IN");
        login.setAlignmentX(Component.CENTER_ALIGNMENT);
        login.setBackground(ACCENT);
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        login.setFont(login.getFont().deriveFont(Font.BOLD, 20f));
        login.setMaximumSize(new Dimension(340, 60));
        login.setPreferredSize(new Dimension(340, 60));
        login.addActionListener(e -> {
            AuthResult result = Main.authenticate(usernameField.getText(), new String(passwordField.getPassword()));
            switch (result) {
                case DATABASE_ERROR -> JOptionPane.showMessageDialog(parent, "Failed to communicate with database.", "Database Failure", JOptionPane.INFORMATION_MESSAGE);
                case NO_USER -> JOptionPane.showMessageDialog(parent, String.format("User %s cannot be found.", usernameField.getText()), "Authentication Failure", JOptionPane.INFORMATION_MESSAGE);
                case WRONG_PASSWORD -> JOptionPane.showMessageDialog(parent, "Password does not match.", "Authentication Failure", JOptionPane.INFORMATION_MESSAGE);
                case SUCCESS -> navigator.accept(App.CARD_DASHBOARD);
            }
        });
        inner.add(login);

        gbc.gridy++;
        card.add(inner, gbc);

        GridBagConstraints outerGbc = new GridBagConstraints();
        outerGbc.gridx = 0;
        outerGbc.gridy = 0;
        outerGbc.fill = GridBagConstraints.BOTH;
        outerGbc.weightx = 1.0;
        outerGbc.weighty = 1.0;
        outerGbc.anchor = GridBagConstraints.CENTER;
        add(card, outerGbc);
    }

    // A simple rounded card panel which paints its own rounded background.
    private static class RoundedCard extends JPanel {
        public RoundedCard() {
            setOpaque(false);
            // fixed preferred size for static layout
            setMinimumSize(new Dimension(680, 420));
            setPreferredSize(new Dimension(680, 420));
            setMaximumSize(new Dimension(680, 420));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int arc = 28;
            // shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(8, 12, getWidth() - 16, getHeight() - 12, arc, arc);
            // card background
            g2.setColor(CARD);
            g2.fillRoundRect(0, 0, getWidth() - 16, getHeight() - 16, arc, arc);
            // border
            g2.setColor(new Color(46, 125, 50));
            g2.setStroke(new BasicStroke(3f));
            g2.drawRoundRect(0, 0, getWidth() - 16, getHeight() - 16, arc, arc);
            g2.dispose();
        }
    }

    // Placeholder-enabled text field
    private static class PlaceholderTextField extends JTextField {
        private final String placeholder;

        public PlaceholderTextField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(true);
            setCaretColor(Color.BLACK);
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    setBorder(new CompoundBorder(new LineBorder(ACCENT.darker(), 2, true), new EmptyBorder(6, 10, 6, 10)));
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    setBorder(new CompoundBorder(new LineBorder(ACCENT, 2, true), new EmptyBorder(6, 10, 6, 10)));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(140, 140, 140));
                Insets in = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, in.left + 4, y);
                g2.dispose();
            }
        }
    }

    private static class PlaceholderPasswordField extends JPasswordField {
        private final String placeholder;

        public PlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;
            setOpaque(true);
            setCaretColor(Color.BLACK);
            addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    setBorder(new CompoundBorder(new LineBorder(ACCENT.darker(), 2, true), new EmptyBorder(6, 10, 6, 10)));
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    setBorder(new CompoundBorder(new LineBorder(ACCENT, 2, true), new EmptyBorder(6, 10, 6, 10)));
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0 && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2.setColor(new Color(140, 140, 140));
                Insets in = getInsets();
                FontMetrics fm = g2.getFontMetrics();
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(placeholder, in.left + 4, y);
                g2.dispose();
            }
        }
    }
}
