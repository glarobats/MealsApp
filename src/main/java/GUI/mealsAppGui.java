package GUI;

import javax.swing.*;
import java.awt.*;

public class mealsAppGui extends JFrame{
    private static mealsAppGui instance;

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
    private JPanel categoriesPanel;
    private JLabel categoriesLabel;
    private JPanel statsPanel;
    private JLabel statsLabel;
    private JPanel firstPanel;
    private JLabel firstLabel;
    private JPanel jPanelForText;
    private JPanel jPanelForButtons;
    private JTextArea mealsArea;
    private JButton SaveButton;
    private JButton EditButton;
    private JButton DeleteButton;
    private JButton SaveEdited;
    private JTextArea categories;
    private JTextArea Area;
    private JTextArea Instructions;
    private JButton OkButton;
    private JLabel categoryJLabel;
    private JLabel areaJLabel;
    private JLabel instructionsJLabel;
    private JLabel mealJLabel;


    private mealsAppGui() {
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
    }

    //Χρήση singleton για την εναλλαγή JPanels
    public static mealsAppGui getInstance() {
        if (instance == null) {
            instance = new mealsAppGui();
        }
        return instance;
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
        rightSidePanel.removeAll();
        rightSidePanel.add(searchingPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void showCategoriesPanel() {
        rightSidePanel.remove(firstPanel);
        rightSidePanel.remove(statsPanel);
        rightSidePanel.remove(searchingPanel);
        rightSidePanel.add(categoriesPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showStatsPanel() {
        rightSidePanel.remove(firstPanel);
        rightSidePanel.remove(categoriesPanel);
        rightSidePanel.remove(searchingPanel);
        rightSidePanel.add(statsPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public JPanel getSearchingPanel() {
        return searchingPanel;
    }

    public void setSearchingPanel(JPanel searchingPanel) {
        this.searchingPanel = searchingPanel;
    }

    public JPanel getCategoriesPanel() {
        return categoriesPanel;
    }

    public void setCategoriesPanel(JPanel categoriesPanel) {
        this.categoriesPanel = categoriesPanel;
    }

    public JPanel getStatsPanel() {
        return statsPanel;
    }

    public void setStatsPanel(JPanel statsPanel) {
        this.statsPanel = statsPanel;
    }

    public JPanel getFirstPanel() {
        return firstPanel;
    }

    public void setFirstPanel(JPanel firstPanel) {
        this.firstPanel = firstPanel;
    }
}