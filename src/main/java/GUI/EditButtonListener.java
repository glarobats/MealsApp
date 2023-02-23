package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditButtonListener implements ActionListener {
    private Meal meal;
    private JButton editButton, deleteButton, saveButton, saveEdited;
    private JTextArea mealsArea, category, Area, Instructions;

    public EditButtonListener(Meal meal, JButton saveButton, JButton saveEdited, JButton editButton, JButton deleteButton, JTextArea mealsArea, JTextArea category, JTextArea Area, JTextArea Instuctions) {
        this.meal = meal;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.saveButton = saveButton;
        this.saveEdited = saveEdited;
        this.mealsArea = mealsArea;
        this.category = category;
        this.Area = Area;
        this.Instructions = Instuctions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int edit = JOptionPane.showConfirmDialog(null,
                "Είσαι σίγουρος οτι θέλεις να τροποποιήσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
        if (edit == JOptionPane.YES_NO_OPTION) {
            //απενεργοποίηση κουμπιών και ενεργοποίηση των πεδίων προς τροποποίηση
            saveEdited.setEnabled(true);
            saveButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            mealsArea.setEditable(true);
            category.setEditable(true);
            Area.setEditable(true);
            Instructions.setEditable(true);
        }
    }
}