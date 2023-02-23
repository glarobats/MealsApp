package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteButtonListener implements ActionListener {
    private Meal meal;
    private JButton editButton, deleteButton, saveButton;

    public DeleteButtonListener(Meal meal, JButton editButton, JButton deleteButton, JButton saveButton) {
        this.meal = meal;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.saveButton = saveButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Database.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
            Database.deleteSavedTable(Integer.valueOf(meal.getId()));
            deleteButton.setEnabled(false);
            editButton.setEnabled(false);
            saveButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(null, "Το γεύμα δεν είναι αποθηκευμένο!!!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
            deleteButton.setEnabled(false);
        }
    }
}