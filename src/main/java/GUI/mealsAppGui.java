package GUI;

import javax.swing.*;
import java.awt.*;

public class mealsAppGui {
    private JPanel mainPanel;
    private JButton dataButton;
    //private JButton button4;
    private JButton CategoriesButton;
    private JButton statisticsPrintButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JButton ExitButton;


    public mealsAppGui() {
        //κουμπί προβολής δεδομένων γεύματος
        DataButton DataButton = new DataButton();
        DataButton.addButton1ActionListener(dataButton);

        //κουμπί προβολής λίστας γευμάτων ανα κατηγορία γεύματος
        CategoriesButton CategoriesButton = new CategoriesButton();
        CategoriesButton.addButton2ActionListener(this.CategoriesButton);

        //κουμπί προβολής στατιστικών δεδομένων γευμάτων και εκτύπωση σε αρχείο pdf
        StatisticsPrintButton StatisticsPrintButton = new StatisticsPrintButton();
        StatisticsPrintButton.addButton3ActionListener(statisticsPrintButton);

        //κουμπί έξοδος
        EXITButton EXITButton = new EXITButton();
        EXITButton.addEXITButtonActionListener(this.ExitButton);
    }

    //ρυθμίσεις κεντρικού μενού
    public void JFrameMain (){
        JFrame frame = new JFrame("MainGui");

        //Διαγράφει την πάνω καρτέλα με το Χ
      /*  frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

       */

        frame.setTitle("MealsApp");
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 565);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel centerPanel = new JPanel();
        ImageIcon icon = new ImageIcon("background.png");
        JLabel iconLabel = new JLabel(icon);
        centerPanel.setBackground(Color.black);
        centerPanel.add(iconLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.black);
        bottomPanel.add(new mealsAppGui().mainPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, bottomPanel, centerPanel);
        splitPane.setResizeWeight(1.0);
        splitPane.setDividerLocation(0.8);
        splitPane.setDividerSize(0);

        frame.add(splitPane);
        frame.setVisible(true);
    }
}