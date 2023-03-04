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
        //Εάν πατηθεί το κουμπί τότε διαγράφεται απο τον πίνακα SAVED
        mealsAppGui gui = mealsAppGui.getInstance();
        if(gui.getDeleteButton().isEnabled()){
        super.mouseClicked(e);
        if (Database.idSearchInSAVED(dataButton.getMealId())) {
            Database.deleteSavedTable(dataButton.getMealId());
            DeleteButton.setEnabled(false);
            EditButton.setEnabled(false);
            SaveButton.setEnabled(true);
        }
    }
    }
}
