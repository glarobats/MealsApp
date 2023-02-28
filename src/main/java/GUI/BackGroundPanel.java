package GUI;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class BackGroundPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Όρισε που θα αρχίσει και που θα τελειώσει το σβήσιμο
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(159, 100, 69), // Έναρξη απο σημείο και χρώμα (rust)
                0, height, new Color(176,166,145) // Τέλος, σε σημείο και χρώμα (beige)
        );

        // Γέμισε με χρώμα
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }
}
