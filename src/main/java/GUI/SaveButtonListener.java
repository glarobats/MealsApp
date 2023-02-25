package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SaveButtonListener extends MouseAdapter {
    private final JLabel SaveButton;
    private final JLabel EditButton;
    private final JLabel DeleteButton;
    private final DataButton dataButton;

    public SaveButtonListener(JLabel SaveButton, JLabel EditButton, JLabel DeleteButton, DataButton dataButton) {
        this.SaveButton = SaveButton;
        this.EditButton = EditButton;
        this.DeleteButton = DeleteButton;
        this.dataButton = dataButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (!Database.idSearchInSAVED(dataButton.getMealId())) {
            int save = JOptionPane.showConfirmDialog(null,
                    "Είσαι σίγουρος οτι θέλεις να αποθηκεύσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
            if (save == JOptionPane.YES_NO_OPTION) {
                Database.saveToNewTable(dataButton.getMealId());
                EditButton.setEnabled(true);
                DeleteButton.setEnabled(true);
                SaveButton.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Το γεύμα είναι ήδη αποθηκευμένο", "SAVED", JOptionPane.INFORMATION_MESSAGE);
            EditButton.setEnabled(true);
        }
    }
}
