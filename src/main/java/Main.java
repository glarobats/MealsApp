import GUI.mealsAppGui;
import org.database.Database;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        mealsAppGui frame = new mealsAppGui("Meals DB");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(new Color(0,0,0,0));

        Database db = Database.getInstance();
        db.startDB();
    }
}