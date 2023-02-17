package GUI;

import Pdf.Chart;
import org.database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsPrintButton {
    public void addButton3ActionListener(JButton button3) {
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database db = Database.getInstance();
                db.orderBy();

                Chart chart = new Chart();
                chart.makeChart();
            }
        });
    }
}
