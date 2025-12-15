package com.mfnhs.frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SectionsPanel extends JPanel {

    public SectionsPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(12,12,12,12));

        JLabel heading = new JLabel("Sections / Tracks");
        heading.setFont(Theme.TITLE_FONT);
        heading.setForeground(Theme.ACCENT.darker());
        add(heading, BorderLayout.NORTH);

        // Top filter row: Strand and Track
        JPanel topFilters = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 6));
        topFilters.setOpaque(false);
        topFilters.add(new JLabel("Strand:"));
        JComboBox<String> strandCombo = new JComboBox<>(new String[]{"All Strands", "STEM", "ABM", "HUMSS"});
        topFilters.add(strandCombo);
        topFilters.add(new JLabel("Track:"));
        JComboBox<String> trackCombo = new JComboBox<>(new String[]{"All Tracks", "Track A", "Track B"});
        topFilters.add(trackCombo);
        add(topFilters, BorderLayout.NORTH);

        // Table for sections
        String[] cols = new String[]{"Section", "Strand", "Track", "Capacity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 1, true));
        add(sp, BorderLayout.CENTER);

        // sample data and filter logic
        List<Object[]> allRows = new ArrayList<>();
        allRows.add(new Object[]{"Section 1", "STEM", "Track A", 40});
        allRows.add(new Object[]{"Section 2", "STEM", "Track B", 35});
        allRows.add(new Object[]{"Section 3", "ABM", "Track A", 30});
        allRows.add(new Object[]{"Section 4", "HUMSS", "Track B", 32});

        Runnable refresh = () -> {
            String sSel = (String) strandCombo.getSelectedItem();
            String tSel = (String) trackCombo.getSelectedItem();
            model.setRowCount(0);
            for (Object[] r : allRows) {
                boolean sMatch = sSel.equals("All Strands") || r[1].equals(sSel);
                boolean tMatch = tSel.equals("All Tracks") || r[2].equals(tSel);
                if (sMatch && tMatch) model.addRow(r);
            }
        };

        strandCombo.addActionListener(e -> refresh.run());
        trackCombo.addActionListener(e -> refresh.run());

        // populate initially
        refresh.run();

        // footer with back button
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        JButton back = new JButton("Back");
        back.addActionListener(e -> navigator.accept(App.CARD_DASHBOARD));
        footer.add(back, BorderLayout.WEST);
        add(footer, BorderLayout.SOUTH);
    }
}
