package com.mfnhs.frontend;

import com.mfnhs.backend.manager.StudentManager;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class MasterlistPanel extends JPanel {

    private static Object[][] masterList;

    public MasterlistPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JLabel heading = new JLabel("Masterlist");
        heading.setFont(Theme.TITLE_FONT);
        heading.setForeground(Theme.ACCENT.darker());
        add(heading, BorderLayout.NORTH);

        // Top filters (Strand, Track) aligned to the right
        JPanel topFilters = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        topFilters.setOpaque(false);
        // Single button for Strand/Track management (Track is a subsection of Strand)
        JButton strandTrackBtn = new JButton("Strand / Tracks");
        strandTrackBtn.addActionListener(e -> navigator.accept(App.CARD_SECTIONS));
        topFilters.add(strandTrackBtn);
        add(topFilters, BorderLayout.NORTH);

        // Masterlist table
        String[] cols = new String[]{"LRN","Last Name","First Name","Middle Name","Track","Strand"};
        Object[][] sample = new Object[][]{
            {"123456789101","Gimolatan","Brandon","Antonio","Track A","STEM"},
            {"11111111111111111111","Doe","John","M","Track B","ABM"}
        };
        updateMasterlist();
        JTable table = new JTable(masterList, cols);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 1, true));
        add(sp, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JButton back = new JButton("Back");
        back.addActionListener(e -> navigator.accept(App.CARD_DASHBOARD));
        footer.add(back, BorderLayout.WEST);
        add(footer, BorderLayout.SOUTH);
    }

    public static void updateMasterlist() {
        masterList = StudentManager.objectifyStudentList();
    }
}
