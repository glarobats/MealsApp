package GUI;

import Pdf.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class BarListener extends Listener {

    public BarListener(JPanel rightSidePanel, JPanel statsPanel, JPanel jPanelForButChar, JPanel jPanelForCharts,
                       JPanel mainPanel, JLabel pie, JLabel bar) {
        super(rightSidePanel, statsPanel, jPanelForButChar, jPanelForCharts, mainPanel, pie, bar);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Εφόσον πατηθεί το κουμπί "κλείνουν" τα πάνελ και ανοίγουν μόνο αυτά που χρειάζονται
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
