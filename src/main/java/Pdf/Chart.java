package Pdf;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
                dataset.setValue(resultSet.getString("Όνομα"), resultSet.getInt("Εμφανίσεις"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        JFreeChart chart = ChartFactory.createPieChart("Εμφανίσεις Γευμάτων", dataset, true, true, false);
        ChartFrame frame = new ChartFrame("Διάγραμμα Εμφανίσεων Γευμάτων", chart);
        frame.setVisible(true);
        frame.setSize(700, 600);
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
    }

}
