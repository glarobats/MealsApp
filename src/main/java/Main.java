import GUI.mealsAppGui;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        mealsAppGui frame = mealsAppGui.getInstance();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Meals App");
        ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/loo.png")));
        frame.setIconImage(image.getImage());
    }
}