package GUI;

import Pdf.Chart;
import Pdf.ViewsPDF;
import org.database.Database;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import static org.database.Database.connect;

public class StatisticsPrintButton extends JPanel{
        public void addStatisticsPrintButtonListener(JLabel statsTitle) {
                statsTitle.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Chart statistika = new Chart();
                        mealsAppGui gui = mealsAppGui.getInstance();
                        gui.getJPanelForCharts().removeAll();
                        gui.getRightSidePanel().removeAll();
                        gui.getRightSidePanel().add(gui.getStatsPanel());
                        gui.getStatsPanel().setLayout(new BorderLayout());
                        gui.getStatsPanel().add(gui.getJPanelForButChar(), BorderLayout.SOUTH);
                        gui.getStatsPanel().add(gui.getJPanelForCharts(), BorderLayout.NORTH);
                        gui.getJPanelForButChar().setBackground(new Color(176,166,145));
                        gui.getJPanelForCharts().setVisible(true);
                        gui.getJPanelForButChar().setVisible(true);
                        gui.getPie().setVisible(true);
                        gui.getPie().setCursor(new Cursor(Cursor.HAND_CURSOR));
                        gui.getPie().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        gui.getPie().setFont(new Font("Calibri",Font.BOLD,16));
                        gui.getBar().setVisible(true);
                        gui.getBar().setCursor(new Cursor(Cursor.HAND_CURSOR));
                        gui.getBar().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        gui.getBar().setFont(new Font("Calibri",Font.BOLD,16));
                        gui.getPrint().setVisible(true);
                        gui.getPrint().setCursor(new Cursor(Cursor.HAND_CURSOR));
                        gui.getPrint().setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                        gui.getPrint().setFont(new Font("Calibri",Font.BOLD,16));
                        gui.getJPanelForCharts().add(statistika.makePieChart());
                        gui.getMainPanel().revalidate();
                        gui.getMainPanel().repaint();
            }
        });
    }
}
