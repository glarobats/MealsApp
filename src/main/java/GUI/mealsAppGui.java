package GUI;

import javax.swing.*;
import java.awt.*;

public class mealsAppGui extends JFrame{
    private static mealsAppGui instance;
    private BackGroundPanel backGroundPanel;
    private JPanel mainPanel,BackGdPanel,leftSidePanel,rightSidePanel,startPanel,searchingPanel,categoriesPanel,statsPanel,
            firstPanel,jPanelForButtons,jPanelForText;
    private JLabel appIcon,appTitle,dataIcon,dataTitle,categoryIcon,categoryTitle,exitIcon,exitTitle,StatsTitle,statsIcon,
            categoriesLabel,statsLabel,firstLabel,categoryJLabel,areaJLabel,instructionsJLabel,mealJLabel;
    private JTextArea mealsName,categories,Area,Instructions;
    private JButton SaveButton,EditButton,DeleteButton,SaveEdited,OkButton;
    private JScrollPane jScrollInsrt;


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
        mealJLabel.setVisible(false);
        categoryJLabel.setVisible(false);
        areaJLabel.setVisible(false);
        instructionsJLabel.setVisible(false);
        jScrollInsrt.setVisible(false);

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
        mealJLabel.setVisible(true);
        categoryJLabel.setVisible(true);
        areaJLabel.setVisible(true);
        instructionsJLabel.setVisible(true);
        jScrollInsrt.setVisible(true);
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

    public void setMealsName(String text) {
        mealsName.setText(text);
    }

    public void setCategories(String text) {
        categories.setText(text);
    }

    public void setArea(String text) {
        Area.setText(text);
    }

    public void setInstructions(String text) {
        Instructions.setText(text);
    }
}