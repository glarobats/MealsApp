import org.database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EXITButton {
    public void addEXITButtonActionListener(JButton ExitButton) {
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ExitButton) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Είσαι σίγουρος οτι θέλεις να κάνεις έξοδο?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_NO_OPTION) {
                        Database db = Database.getInstance();
                        db.deleteDB();
                        System.out.println("Επιτυχείς διαγραφή δεδομένων");
                        System.exit(0);
                    }
                }
            }
        });
    }
}