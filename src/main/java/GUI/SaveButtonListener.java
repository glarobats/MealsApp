package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonListener implements ActionListener {
    private Meal meal;
    private JButton editButton, deleteButton, saveButton;
    private Database db;


    public SaveButtonListener(Meal meal,JButton saveButton, JButton editButton, JButton deleteButton, Database db) {
        this.meal = meal;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.saveButton = saveButton;
        this.db = db;
    }
    mealsAppGui mealsappgui = mealsAppGui.getInstance();
    @Override
    public void actionPerformed(ActionEvent e) {
            if (!db.idSearchInSAVED(Integer.parseInt(meal.getId()))) {
                int save = JOptionPane.showConfirmDialog(null,
                        "Είσαι σίγουρος οτι θέλεις να αποθηκεύσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                if (save == JOptionPane.YES_NO_OPTION) {
                    db.saveToNewTable(Integer.parseInt(meal.getId()));
                    editButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    saveButton.setEnabled(false);

                }
            } else {
                JOptionPane.showMessageDialog(null, "Το γεύμα είναι ήδη αποθηκευμένο", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                editButton.setEnabled(true);
            }
    }
}
