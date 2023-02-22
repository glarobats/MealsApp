package Pdf;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.Rotation;

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

    public JPanel makePieChart() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart3D("Εμφανίσεις Γευμάτων", dataset, true, true, false);
        TextTitle title = chart.getTitle();
        Font font = new Font("Calibri", Font.BOLD, 20);
        title.setFont(font);
        //ποσοστό και εμφανίσεις πάνω στην πίτα
        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setBackgroundPaint(new Color(166, 136, 116));
        plot.setStartAngle(250);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 620));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }
    
    public JPanel makeBarChart(){
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
        JFreeChart chart = ChartFactory.createBarChart3D("Εμφανίσεις Γευμάτων", "Γεύμα", "Εμφανίσεις", dataset, PlotOrientation.VERTICAL, true, true, false);
        TextTitle title = chart.getTitle();
        Font font = new Font("Calibri", Font.BOLD, 20);
        title.setFont(font);
        //ακέραιοι στον άξονα τον εμφανίσεων
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(166, 136, 116));
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        CategoryAxis domainAxis = plot.getDomainAxis();
        ValueAxis rangeAxis3D = plot.getRangeAxis();
        BarRenderer3D renderer = new BarRenderer3D();
        renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator());
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        plot.setRenderer(renderer);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 620));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }
}


