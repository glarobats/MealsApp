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
    private JLabel categoryIcon;
    private JLabel categoryTitle;
    private JLabel exitIcon;
    private JLabel exitTitle;
    private JPanel startPanel;
    private JLabel StatsTitle;
    private JLabel statsIcon;
    private JPanel searchingPanel;
    private JLabel searchLabel;
    private JPanel categoriesPanel;
    private JLabel categoriesLabel;
    private JPanel statsPanel;
    private JLabel statsLabel;
    private JPanel firstPanel;
    private JLabel firstLabel;


    public mealsAppGui() {
        super();
        setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        leftSidePanel.setOpaque(false);
        BackGdPanel.setOpaque(false);
        BackGdPanel = new BackGroundPanel();
        mainPanel.add(BackGdPanel);

        appIcon.setIcon(new ImageIcon("images/logo.png"));
        firstLabel.setIcon(new ImageIcon("images/background.png"));
        firstPanel.setVisible(true);
        dataIcon.setIcon(new ImageIcon("images/search.png"));
        categoryIcon.setIcon(new ImageIcon("images/categorize.png"));
        statsIcon.setIcon(new ImageIcon("images/stats.png"));
        exitIcon.setIcon(new ImageIcon("images/shutdown.png"));

        Buttons();
     /* Εάν βγάλεις τα σχόλια θα δεις οτι καλεί τη μέθοδο showSearchPanel() στον constructor. Η μέθοδος είναι στη γραμμή 91.
     Ουσιαστικά αφαιρεί τα JPanel που καλύπτουν το JPanel που θέλουμε να δούμε και εμφανίζει αυτό που θέλουμε να δούμε.
     Προσπάθησα να το βάλω στην κλάση DataButton φτιάχνοντας ένα αντικείμενο mealsAppGui αλλά παπάρια. Δε λειτουργεί.

       showSearchPanel();
      */
    }

    public void Buttons() {

        //κουμπί προβολής δεδομένων γεύματος
        DataButton dataButton = new DataButton();
        dataTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dataIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dataButton.addDataButtonListener(dataTitle);
        dataButton.addDataButtonListener(dataIcon);

        //κουμπί προβολής λίστας γευμάτων ανα κατηγορία γεύματος
        CategoriesButton categoriesButton = new CategoriesButton();
        categoryTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        categoryIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        categoriesButton.addCategoriesButtonListener(categoryTitle);
        categoriesButton.addCategoriesButtonListener(categoryIcon);

        //κουμπί προβολής στατιστικών δεδομένων γευμάτων και εκτύπωση σε αρχείο pdf
        StatisticsPrintButton statisticsPrintButton = new StatisticsPrintButton();
        StatsTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statsIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statisticsPrintButton.addStatisticsPrintButtonListener(StatsTitle);
        statisticsPrintButton.addStatisticsPrintButtonListener(statsIcon);

        //κουμπί έξοδος
        EXITButton exitButton = new EXITButton();
        exitTitle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addEXITButtonMouseListener(exitTitle);
        exitButton.addEXITButtonMouseListener(exitIcon);
    }

    public void showSearchPanel() {
        rightSidePanel.remove(firstPanel);
        rightSidePanel.remove(statsPanel);
        rightSidePanel.remove(categoriesPanel);
        rightSidePanel.add(searchingPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


}