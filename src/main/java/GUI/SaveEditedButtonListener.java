package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class SaveEditedButtonListener implements ActionListener {
    private Meal meal;
    private JButton editButton, deleteButton, saveButton, saveEdited;
    private JTextArea mealsArea, category, Area, Instructions;
    private Database db;

    public SaveEditedButtonListener(Meal meal, JButton saveButton, JButton saveEdited, JButton editButton,
                                    JButton deleteButton, Database db, JTextArea mealsArea, JTextArea category,JTextArea Instructions) {
        this.meal = meal;
        this.editButton = editButton;
        this.deleteButton = deleteButton;
        this.saveButton = saveButton;
        this.saveEdited = saveEdited;
        this.db = db;
        this.mealsArea = mealsArea;
        this.category = category;
        this.Instructions = Instructions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int ID = Integer.valueOf(meal.getId());
        try {
            //αποθήκευση στη ΒΔ και συγκεκριμένα στον πίνακα SAVED την τροποποίηση
            saveEdited.setEnabled(false);
            Connection connection = db.connect();
            Statement stmt = (Statement) connection.createStatement();
            String modifiedFields = "UPDATE SAVED SET "
                    + "Όνομα = '" + mealsArea.getText() + "', "
                    + "Κατηγορία = '" + category.getText() + "', "
                    + "Περιοχή = '" + mealsArea.getText() + "', "
                    + "Οδηγίες = '" + Instructions.getText() + "' "
                    + "WHERE ID = " + ID;
            stmt.executeUpdate(modifiedFields);
            connection.commit();
            mealsArea.setEditable(false);
            saveButton.setEnabled(false);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } catch (SQLException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}
