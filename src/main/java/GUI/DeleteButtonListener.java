package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DeleteButtonListener extends MouseAdapter {
    private final JLabel DeleteButton, EditButton, SaveButton;
    private final DataButton dataButton;

    public DeleteButtonListener(JLabel DeleteButton, JLabel EditButton, JLabel SaveButton, DataButton dataButton) {
        this.DeleteButton = DeleteButton;
        this.EditButton = EditButton;
        this.SaveButton = SaveButton;
        this.dataButton = dataButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if (Database.idSearchInSAVED(dataButton.getMealId())) {
            Database.deleteSavedTable(dataButton.getMealId());
            DeleteButton.setEnabled(false);
            EditButton.setEnabled(false);
            SaveButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Το γεύμα δεν είναι αποθηκευμένο!!!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
            DeleteButton.setEnabled(false);
        }
    }
}
