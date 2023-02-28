package GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EditButtonListener extends MouseAdapter {
    private final JLabel EditButton, SaveButton, DeleteButton, SaveEdited;
    private final JTextArea mealsName, categories, Area, Instructions;


    public EditButtonListener(JLabel EditButton, JLabel SaveButton, JLabel DeleteButton, JLabel SaveEdited,
                              JTextArea mealsName, JTextArea categories, JTextArea Area, JTextArea Instructions) {
        this.EditButton = EditButton;
        this.SaveButton = SaveButton;
        this.DeleteButton = DeleteButton;
        this.SaveEdited = SaveEdited;
        this.mealsName = mealsName;
        this.categories = categories;
        this.Area = Area;
        this.Instructions = Instructions;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int edit = JOptionPane.showConfirmDialog(null,
                "Είσαι σίγουρος οτι θέλεις να τροποποιήσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
        if (edit == JOptionPane.YES_NO_OPTION) {
            //απενεργοποίηση κουμπιών και ενεργοποίηση των πεδίων προς τροποποίηση
            SaveEdited.setEnabled(true);
            SaveButton.setEnabled(false);
            EditButton.setEnabled(false);
            DeleteButton.setEnabled(false);
            mealsName.setEditable(true);
            categories.setEditable(true);
            Area.setEditable(true);
            Instructions.setEditable(true);
        }
    }
}
