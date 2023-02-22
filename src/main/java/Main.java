import java.awt.Color;

import GUI.mealsAppGui;
import org.database.Database;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        mealsAppGui frame = mealsAppGui.getInstance();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        //frame.setBackground(new Color(0,0,0,0));
        frame.setTitle("Meals App");
        ImageIcon image = new ImageIcon("images/loo.png");
        frame.setIconImage(image.getImage());
    }
}