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
                gui.showSearchPanel();
                Chart chart = new Chart();
                JPanel chartPanel = chart.makeChart();
                JPanel statsPanel = gui.getStatsPanel();
                statsPanel.removeAll();
                statsPanel.add(chartPanel);
                gui.showStatsPanel(statsPanel);
            }
        });
    }
}
