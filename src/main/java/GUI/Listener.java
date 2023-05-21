package GUI;

import Pdf.Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public  class Listener extends MouseAdapter {
    protected final JPanel rightSidePanel, statsPanel, JPanelForButChar, JPanelForCharts, mainPanel;
    JLabel Pie;
    JLabel Bar;

    protected Listener(JPanel rightSidePanel, JPanel statsPanel, JPanel jPanelForButChar, JPanel jPanelForCharts,
                       JPanel mainPanel, JLabel pie, JLabel bar) {
        this.rightSidePanel = rightSidePanel;
        this.statsPanel = statsPanel;
        this.JPanelForButChar = jPanelForButChar;
        this.JPanelForCharts = jPanelForCharts;
        this.mainPanel = mainPanel;
        this.Pie = pie;
        this.Bar = bar;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        //Εάν πατηθεί το κουμπί τότε κλείνουν όλα τα πάνελ και ανοίγει το διάγραμμα πίτας
        super.mouseClicked(e);
    }
}


