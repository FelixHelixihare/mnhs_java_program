package com.mfnhs.frontend;

import com.mfnhs.backend.manager.GeneralDataManager;
import com.mfnhs.backend.manager.StudentManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;
public class DashboardPanel extends JPanel {

    private static final Color BG = new Color(255, 249, 196); 
    private static final Color ACCENT = new Color(39, 115, 41); 
    private final int menuWidth = 400;

    private StatBox totalStudentPanel = new StatBox("TOTAL ENROLLED STUDENTS", "--");
    private StatBox totalSectionPanel = new StatBox("TOTAL SECTIONS", "--");
    public static int totalStudents;
    public static int totalSections;

    public DashboardPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout());
        setBackground(new Color(45, 45, 45)); 

        JLayeredPane layered = new JLayeredPane();
        layered.setLayout(null);

        // 1. Transparent Overlay for click-to-close
        JPanel glassPane = new JPanel();
        glassPane.setOpaque(false);
        glassPane.setVisible(false);

        // 2. com.mfnhs.Main Content Area
        JPanel mainContent = new JPanel(new BorderLayout());
            mainContent.setBackground(Theme.PANEL_WHITE);
        mainContent.setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- FIXED CENTERED HEADER ---
        JPanel headerPanel = new JPanel(null); // Absolute layout for layering
        headerPanel.setOpaque(false);
        headerPanel.setPreferredSize(new Dimension(1160, 100));

        JLabel brand = new JLabel("MFNHS", SwingConstants.CENTER);
        brand.setFont(new Font("Serif", Font.BOLD, 64)); 
        brand.setForeground(ACCENT);
        brand.setBounds(0, 0, 1160, 100); // Fills width to ensure center

        JButton menuBtn = new JButton("\u2261"); // Bold recognizable menu icon
        menuBtn.setBorderPainted(false);
        menuBtn.setContentAreaFilled(false);
        menuBtn.setFocusPainted(false);
        menuBtn.setFont(new Font("SansSerif", Font.PLAIN, 45));
        menuBtn.setForeground(ACCENT.darker());
        menuBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        menuBtn.setBounds(1080, 20, 70, 60);

        headerPanel.add(menuBtn); 
        headerPanel.add(brand); 
        mainContent.add(headerPanel, BorderLayout.NORTH);

        // --- DASHBOARD BODY ---
        JPanel centerBody = new JPanel(new GridBagLayout());
        centerBody.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(25, 25, 25, 25);
        gbc.fill = GridBagConstraints.BOTH;

        // Left: Stats
        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setOpaque(false);
        leftCol.add(totalStudentPanel);
        leftCol.add(Box.createRigidArea(new Dimension(0, 40)));
        leftCol.add(totalSectionPanel);

        gbc.gridx = 0; gbc.weightx = 0.6; gbc.weighty = 1.0;
        centerBody.add(leftCol, gbc);

        // Right: Actions
        JPanel rightCol = new JPanel(new GridLayout(3, 1, 0, 25));
        rightCol.setOpaque(false);
        rightCol.add(createActionButton("ENROLL A STUDENT", e -> navigator.accept(App.CARD_ENROLL)));
        rightCol.add(createActionButton("GO TO MASTERLIST", e -> navigator.accept(App.CARD_MASTER)));
        rightCol.add(createActionButton("PRINT REPORTS", e -> {}));

        gbc.gridx = 1; gbc.weightx = 0.4;
        centerBody.add(rightCol, gbc);
        mainContent.add(centerBody, BorderLayout.CENTER);

        // 3. Sliding Menu Panel
        MenuPanel menuPanel = new MenuPanel(navigator);
        menuPanel.setVisible(false);

        layered.add(mainContent, JLayeredPane.DEFAULT_LAYER);
        layered.add(glassPane, JLayeredPane.MODAL_LAYER); 
        layered.add(menuPanel, JLayeredPane.DRAG_LAYER);  

        add(layered, BorderLayout.CENTER);

        // Resize Listener
        layered.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int w = layered.getWidth();
                int h = layered.getHeight();
                mainContent.setBounds(0, 0, w, h);
                glassPane.setBounds(0, 0, w, h);
                brand.setBounds(0, 0, w, 100); 
                menuBtn.setBounds(w - 90, 20, 70, 60);
                menuPanel.setBounds(w, 0, menuWidth, h);
            }
        });

        // Toggle Logic
        final boolean[] menuOpen = {false};
        final Timer[] slideTimer = new Timer[1];
        updateCounts();

        Runnable toggleMenu = () -> {
            if (slideTimer[0] != null && slideTimer[0].isRunning()) return;
            int w = layered.getWidth();
            int h = layered.getHeight();
            final int endX = menuOpen[0] ? w : w - menuWidth;
            if (!menuOpen[0]) { menuPanel.setVisible(true); glassPane.setVisible(true); }
            
            slideTimer[0] = new Timer(10, ev -> {
                int curX = menuPanel.getX();
                if (menuOpen[0]) {
                    if (curX >= endX) {
                        menuPanel.setBounds(endX, 0, menuWidth, h);
                        menuPanel.setVisible(false); glassPane.setVisible(false);
                        slideTimer[0].stop(); menuOpen[0] = false;
                    } else { menuPanel.setLocation(curX + 30, 0); }
                } else {
                    if (curX <= endX) {
                        menuPanel.setBounds(endX, 0, menuWidth, h);
                        slideTimer[0].stop(); menuOpen[0] = true;
                    } else { menuPanel.setLocation(curX - 30, 0); }
                }
            });
            slideTimer[0].start();
        };

        menuBtn.addActionListener(e -> toggleMenu.run());
        glassPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) { if (menuOpen[0]) toggleMenu.run(); }
        });

        updateCounts();
    }

    static class StatBox extends JPanel {
        private final JLabel titleHolder = new JLabel("" ,SwingConstants.CENTER);
        private final JLabel valueHolder = new JLabel("", SwingConstants.CENTER);

        StatBox(String title, String value) {
            super(new BorderLayout());
            this.setBackground(Color.WHITE);
            this.setBorder(new LineBorder(ACCENT, 3, true));

            this.titleHolder.setText(title);
            this.titleHolder.setBorder(new EmptyBorder(15, 0, 0, 0));
            this.titleHolder.setFont(new Font("SansSerif", Font.BOLD, 18));
            this.titleHolder.setForeground(ACCENT);

            valueHolder.setText(value);
            valueHolder.setFont(new Font("SansSerif", Font.BOLD, 80));
            valueHolder.setForeground(new Color(210, 210, 210));

            this.add(titleHolder, BorderLayout.NORTH);
            this.add(valueHolder, BorderLayout.CENTER);
        }

        public void setValue(int value) {
            valueHolder.setText(String.valueOf(value));
        }

        public void setTitle(String title) {
            titleHolder.setText(title);
        }
    }

    private JButton createActionButton(String text, java.awt.event.ActionListener action) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        b.setFocusPainted(false);
        b.setBackground(ACCENT);
        b.setForeground(Color.WHITE);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addActionListener(action);
        return b;
    }

    public void updateCounts() {
        totalStudents = StudentManager.getStudentCount();
        totalSections = GeneralDataManager.getSectionCount();
        totalStudentPanel.setValue(totalStudents);
        totalSectionPanel.setValue(totalSections);
    }
}