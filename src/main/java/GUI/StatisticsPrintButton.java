package GUI;

import Pdf.Chart;
import org.database.Database;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatisticsPrintButton {
        public void addStatisticsPrintButtonListener(JLabel statsTitle) {
                statsTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                mealsAppGui gui = mealsAppGui.getInstance();
                gui.showStatsPanel();
                gui.getStatsPanel();
            }
        });
    }
}
