package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SaveButtonListener extends MouseAdapter {
    private final JLabel SaveButton, EditButton, DeleteButton;
    private final DataButton dataButton;

    public SaveButtonListener(JLabel SaveButton, JLabel EditButton, JLabel DeleteButton, DataButton dataButton) {
        this.SaveButton = SaveButton;
        this.EditButton = EditButton;
        this.DeleteButton = DeleteButton;
        this.dataButton = dataButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Εάν πατηθεί το κουμπί τότε έλεγχος εάν υπάρχει ήδη στη ΒΔ και μετά χρήση της μεθόδου για σώσιμο στον
        //πίνακα SAVED
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
        }
    }
}
