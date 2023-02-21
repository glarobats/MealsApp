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

                        ViewsPDF print= new ViewsPDF();
                        print.viewPdf();
            }
        });
    }
}
