package GUI;

import Pdf.Chart;
import org.database.Database;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StatisticsPrintButton {
    public void addStatisticsPrintButtonListener(JLabel statsTitle) {
        statsTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Database db = Database.getInstance();
                db.orderBy();

                Chart chart = new Chart();
                chart.makeChart();
            }
        });
    }
}
