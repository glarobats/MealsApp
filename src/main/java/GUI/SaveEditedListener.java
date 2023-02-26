package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SaveEditedListener extends MouseAdapter {
    private final DataButton dataButton;
    private final JTextArea instructions;
    private final JTextArea mealsName;
    private final JTextArea categories;
    private final JTextArea area;
    private final JLabel saveButton;
    private final JLabel editButton;
    private final JLabel deleteButton;
    private final JLabel SaveEdited;

    public SaveEditedListener(JLabel SaveEdited, DataButton dataButton, JTextArea instructions, JTextArea mealsName, JTextArea categories,
                              JTextArea area, JLabel saveButton, JLabel editButton, JLabel deleteButton) {
        this.SaveEdited = SaveEdited;
        this.dataButton = dataButton;
        this.instructions = instructions;
        this.mealsName = mealsName;
        this.categories = categories;
        this.area = area;
        this.saveButton = saveButton;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        int ID = dataButton.getMealId();
        try {
            //αποθήκευση στη ΒΔ και συγκεκριμένα στον πίνακα SAVED την τροποποίηση

            saveButton.setEnabled(false);
            instructions.setEditable(false);
            mealsName.setEditable(false);
            categories.setEditable(false);
            area.setEditable(false);
            Connection connection = Database.connect();
            Statement stmt = (Statement) connection.createStatement();
            String modifiedFields = "UPDATE SAVED SET "
                    + "Όνομα = '" + mealsName.getText() + "', "
                    + "Κατηγορία = '" + categories.getText() + "', "
                    + "Περιοχή = '" + area.getText() + "', "
                    + "Οδηγίες = '" + instructions.getText() + "' "
                    + "WHERE ID = " + ID;
            stmt.executeUpdate(modifiedFields);
            connection.commit();
            area.setEditable(false);
            saveButton.setEnabled(false);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            SaveEdited.setEnabled(false);
        } catch (SQLException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
