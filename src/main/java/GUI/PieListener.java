package GUI;

import Pdf.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class PieListener extends Listener {

    public PieListener(JPanel rightSidePanel, JPanel statsPanel, JPanel jPanelForButChar, JPanel jPanelForCharts,
                       JPanel mainPanel, JLabel pie, JLabel bar) {
        super(rightSidePanel, statsPanel, jPanelForButChar, jPanelForCharts, mainPanel, pie, bar);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Εάν πατηθεί το κουμπί τότε κλείνουν όλα τα πάνελ και ανοίγει το διάγραμμα πίτας
        Chart statistika = new Chart();
        rightSidePanel.removeAll();
        JPanelForCharts.removeAll();
        rightSidePanel.add(statsPanel);
        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(JPanelForButChar, BorderLayout.SOUTH);
        statsPanel.add(JPanelForCharts, BorderLayout.NORTH);
        JPanelForCharts.add(statistika.makePieChart());
        JPanelForCharts.setVisible(true);
        JPanelForButChar.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
        Pie.setEnabled(false);
        Bar.setEnabled(true);
    }
}
