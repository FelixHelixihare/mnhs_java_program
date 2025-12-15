package com.mfnhs.frontend;

import javax.swing.*;
import java.awt.*;

public class Theme {
    public static final Color OUTER_BG = new Color(61, 61, 61);
    public static final Color CARD_BG = new Color(255, 249, 196);
    public static final Color ACCENT = new Color(39, 115, 41);
    public static final Color ACCENT_DARK = ACCENT.darker();
    public static final Color PANEL_WHITE = Color.WHITE;

    public static final Font HEADER_FONT = new Font("SansSerif", Font.BOLD, 20);
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 16);
    public static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 14);

    public static void applyUIDefaults() {
        UIManager.put("Button.background", ACCENT);
        UIManager.put("Button.foreground", PANEL_WHITE);
        UIManager.put("Button.font", BODY_FONT);

        UIManager.put("Label.font", BODY_FONT);
        UIManager.put("Table.font", BODY_FONT);
        UIManager.put("Table.rowHeight", 28);
        UIManager.put("TableHeader.font", TITLE_FONT);

        UIManager.put("Panel.background", CARD_BG);
    }
}
