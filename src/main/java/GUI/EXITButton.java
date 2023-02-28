package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EXITButton {
    public void addEXITButtonMouseListener(JLabel exitTitle) {
        exitTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Εάν πατηθεί το κουμπί ερώτηση για έξοδο και διαγραφή δεδομένων των πινάκων και διαγραφή των πινάκων
                //αφού κάθε φορά που ανοίγει το πρόγραμμα δημιουργείται μια νέα ΒΔ
                int result = JOptionPane.showConfirmDialog(null,
                        "Είσαι σίγουρος οτι θέλεις να κάνεις έξοδο?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_NO_OPTION) {
                    Database db = Database.getInstance();
                    db.deleteDB();
                    System.out.println("Επιτυχείς διαγραφή δεδομένων");
                    System.exit(0);
                }
            }
        });
    }
}
