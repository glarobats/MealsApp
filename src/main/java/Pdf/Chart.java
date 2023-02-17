package Pdf;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

import static org.database.Database.connect;

public class Chart {

    public void makeChart(){
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String qur = ("SELECT Εμφανίσεις, Όνομα FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID");
            ResultSet resultSet = statement.executeQuery(qur);
            while (resultSet.next()) {
                String name = resultSet.getString("Όνομα");
                int count = resultSet.getInt("Εμφανίσεις");
                dataset.setValue(name, count);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart("Εμφανίσεις Γευμάτων", dataset, true, true, false);
        //ποσοστό και εμφανίσεις πάνω στην πίτα
        PiePlot plot = (PiePlot) chart.getPlot();
        StandardPieToolTipGenerator toolTipGenerator = new StandardPieToolTipGenerator("{0}: {1} ({2})", new DecimalFormat("#,##0"), new DecimalFormat("0.00%"));
        plot.setToolTipGenerator(toolTipGenerator);

        ChartFrame frame = new ChartFrame("Διάγραμμα Εμφανίσεων Γευμάτων", chart);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Εκτύπωση");

        JMenuItem menuItem = new JMenuItem("Εκτύπωση πίνακα δεδομένων σε PDF");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewsPDF pdf = new ViewsPDF();
                pdf.viewPdf();
            }
        });

        menu.add(menuItem);
        JMenu chartMenu = new JMenu("Στατιστικά");
        menuBar.add(chartMenu);
        JMenuItem pieChart = new JMenuItem("Γράφημα Πίτας");
        pieChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                DefaultPieDataset dataset = new DefaultPieDataset();
                try {
                    Connection connection = connect();
                    Statement statement = connection.createStatement();
                    String qur = ("SELECT Εμφανίσεις, Όνομα FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID");
                    ResultSet resultSet = statement.executeQuery(qur);
                    while (resultSet.next()) {
                        String name = resultSet.getString("Όνομα");
                        int count = resultSet.getInt("Εμφανίσεις");
                        dataset.setValue(name, count);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                ChartPanel chartPanel = new ChartPanel(chart);
                frame.setContentPane(chartPanel);
                frame.revalidate();
            }
        });
        chartMenu.add(pieChart);

        JMenuItem barChart = new JMenuItem("Γράφημα στηλών");
        barChart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                try {
                    Connection connection = connect();
                    Statement statement = connection.createStatement();
                    String qur = ("SELECT Εμφανίσεις, Όνομα FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID");
                    ResultSet resultSet = statement.executeQuery(qur);
                    while (resultSet.next()) {
                        String name = resultSet.getString("Όνομα");
                        int value = resultSet.getInt("Εμφανίσεις");
                        dataset.setValue(value, "Εμφανίσεις", name);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JFreeChart chart = ChartFactory.createBarChart("Εμφανίσεις Γευμάτων", "Γεύμα", "Εμφανίσεις", dataset, PlotOrientation.VERTICAL, true, true, false);
                //ακέραιοι στον άξονα τον εμφανίσεων
                CategoryPlot plot = (CategoryPlot) chart.getPlot();
                NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
                rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

                ChartPanel chartPanel = new ChartPanel(chart);
                frame.setContentPane(chartPanel);
                frame.revalidate();
            }
        });
        chartMenu.add(barChart);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
    }
}