import java.awt.Color;

import GUI.mealsAppGui;
import org.database.Database;


public class Main {
    public static void main(String[] args) {

        mealsAppGui frame = new mealsAppGui();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(new Color(0,0,0,0));

        Database db = Database.getInstance();
        db.startDB();
    }
}