package GUI;

import Pdf.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BarListener extends MouseAdapter {
    private final JPanel rightSidePanel, statsPanel, JPanelForButChar,JPanelForCharts,mainPanel;
    private final JLabel Pie, Bar;


    public BarListener(JPanel rightSidePanel, JPanel statsPanel, JPanel JPanelForButChar,
                       JPanel JPanelForCharts, JLabel Pie, JLabel Bar, JPanel mainPanel) {
        this.rightSidePanel = rightSidePanel;
        this.statsPanel = statsPanel;
        this.JPanelForButChar = JPanelForButChar;
        this.JPanelForCharts = JPanelForCharts;
        this.Pie = Pie;
        this.Bar = Bar;
        this.mainPanel = mainPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Εφόσον πατηθεί το κουμπί "κλείνουν" τα πάνελ και ανοίγουν μόνο αυτά που χρειάζονται
        super.mouseClicked(e);
        Chart statistika = new Chart();
        rightSidePanel.removeAll();
        JPanelForCharts.removeAll();
        rightSidePanel.add(statsPanel);
        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(JPanelForButChar, BorderLayout.SOUTH);
        statsPanel.add(JPanelForCharts, BorderLayout.NORTH);
        JPanelForCharts.add(statistika.makeBarChart());
        JPanelForCharts.setVisible(true);
        JPanelForButChar.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
        Pie.setEnabled(true);
        Bar.setEnabled(false);
    }
}
