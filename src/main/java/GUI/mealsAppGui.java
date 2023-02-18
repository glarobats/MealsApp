package GUI;

import javax.swing.*;
import java.awt.*;

public class mealsAppGui extends JFrame{
    private JPanel mainPanel;
    private JPanel BackGdPanel;
    private BackGroundPanel backGroundPanel;

    private JPanel leftSidePanel;
    private JPanel rightSidePanel;
    private JLabel appIcon;
    private JLabel appTitle;
    private JLabel dataIcon;
    private JLabel dataTitle;
    private JLabel statsIcon;
    private JLabel statsTitle;
    private JLabel exitIcon;
    private JLabel exitTitle;
    private JButton dataButton;
    private JButton CategoriesButton;
    private JButton statisticsPrintButton;
    private JButton ExitButton;


    public mealsAppGui(String title) {
        super(title);
        //απαλοιφή πάνω μπάρας    setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        leftSidePanel.setOpaque(false);
        BackGdPanel.setOpaque(false);
        BackGdPanel = new BackGroundPanel();
        mainPanel.add(BackGdPanel);

        ImageIcon icon = new ImageIcon("background.png");
        JLabel label = new JLabel(icon);
        rightSidePanel.add(label);


    }




    public void  Buttons() {
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
}