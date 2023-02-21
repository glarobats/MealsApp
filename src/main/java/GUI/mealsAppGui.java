package GUI;

import Pdf.Chart;
import org.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;

public class mealsAppGui extends JFrame{
    private static mealsAppGui instance;
    private JPanel mainPanel,leftSidePanel,rightSidePanel,BackGdPanel,startPanel,searchingPanel,categoriesPanel,statsPanel,
            firstPanel,jPanelForText,jPanelForButtons,JPanelForCharts,JPanelForButChar;
    private JLabel appIcon,appTitle,dataIcon,dataTitle,categoryIcon,categoryTitle,exitTitle,exitIcon,StatsTitle,statsIcon,
            categoriesLabel,firstLabel,categoryJLabel,areaJLabel,instructionsJLabel,mealJLabel,Pie,Bar;
    private JTextArea mealsName,categories,Area,Instructions;
    private JButton SaveButton,EditButton,DeleteButton,SaveEdited;
    private JScrollPane jScrollInsrt;
    private BackGroundPanel backGroundPanel;



    private mealsAppGui() {
        super();
        //setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Database db = Database.getInstance();
        db.startDB();
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



    public void showCategoriesPanel() {
        rightSidePanel.remove(firstPanel);
        rightSidePanel.remove(statsPanel);
        rightSidePanel.remove(searchingPanel);
        rightSidePanel.add(categoriesPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void showStatsPanel() {
        rightSidePanel.removeAll();
        rightSidePanel.add(statsPanel);
        SecondBackground stPanel = new SecondBackground();
        stPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        statsPanel.setLayout(new BorderLayout());
        statsPanel.add(JPanelForCharts, BorderLayout.NORTH);
        statsPanel.add(JPanelForButChar, BorderLayout.SOUTH);
        JPanelForCharts.setVisible(true);
        JPanelForButChar.setVisible(true);
        Pie.setVisible(true);
        Bar.setVisible(true);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public static void setInstance(mealsAppGui instance) {
        mealsAppGui.instance = instance;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getBackGdPanel() {
        return BackGdPanel;
    }

    public void setBackGdPanel(JPanel backGdPanel) {
        BackGdPanel = backGdPanel;
    }

    public BackGroundPanel getBackGroundPanel() {
        return backGroundPanel;
    }

    public void setBackGroundPanel(BackGroundPanel backGroundPanel) {
        this.backGroundPanel = backGroundPanel;
    }

    public JPanel getLeftSidePanel() {
        return leftSidePanel;
    }

    public void setLeftSidePanel(JPanel leftSidePanel) {
        this.leftSidePanel = leftSidePanel;
    }

    public JPanel getRightSidePanel() {
        return rightSidePanel;
    }

    public void setRightSidePanel(JPanel rightSidePanel) {
        this.rightSidePanel = rightSidePanel;
    }

    public JLabel getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(JLabel appIcon) {
        this.appIcon = appIcon;
    }

    public JLabel getAppTitle() {
        return appTitle;
    }

    public void setAppTitle(JLabel appTitle) {
        this.appTitle = appTitle;
    }

    public JLabel getDataIcon() {
        return dataIcon;
    }

    public void setDataIcon(JLabel dataIcon) {
        this.dataIcon = dataIcon;
    }

    public JLabel getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(JLabel dataTitle) {
        this.dataTitle = dataTitle;
    }

    public JLabel getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(JLabel categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public JLabel getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(JLabel categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public JLabel getExitIcon() {
        return exitIcon;
    }

    public void setExitIcon(JLabel exitIcon) {
        this.exitIcon = exitIcon;
    }

    public JLabel getExitTitle() {
        return exitTitle;
    }

    public void setExitTitle(JLabel exitTitle) {
        this.exitTitle = exitTitle;
    }

    public JPanel getStartPanel() {
        return startPanel;
    }

    public void setStartPanel(JPanel startPanel) {
        this.startPanel = startPanel;
    }

    public JLabel getStatsTitle() {
        return StatsTitle;
    }

    public void setStatsTitle(JLabel statsTitle) {
        StatsTitle = statsTitle;
    }

    public JLabel getStatsIcon() {
        return statsIcon;
    }

    public void setStatsIcon(JLabel statsIcon) {
        this.statsIcon = statsIcon;
    }

    public void setCategoriesPanel(JPanel categoriesPanel) {
        this.categoriesPanel = categoriesPanel;
    }

    public JLabel getCategoriesLabel() {
        return categoriesLabel;
    }

    public void setCategoriesLabel(JLabel categoriesLabel) {
        this.categoriesLabel = categoriesLabel;
    }

    public JLabel getFirstLabel() {
        return firstLabel;
    }

    public void setFirstLabel(JLabel firstLabel) {
        this.firstLabel = firstLabel;
    }

    public JPanel getjPanelForText() {
        return jPanelForText;
    }

    public void setjPanelForText(JPanel jPanelForText) {
        this.jPanelForText = jPanelForText;
    }

    public JPanel getjPanelForButtons() {
        return jPanelForButtons;
    }

    public void setjPanelForButtons(JPanel jPanelForButtons) {
        this.jPanelForButtons = jPanelForButtons;
    }

    public void setMealsName(JTextArea mealsName) {
        this.mealsName = mealsName;
    }

    public void setCategories(JTextArea categories) {
        this.categories = categories;
    }

    public void setArea(JTextArea area) {
        Area = area;
    }

    public void setInstructions(JTextArea instructions) {
        Instructions = instructions;
    }

    public JLabel getCategoryJLabel() {
        return categoryJLabel;
    }

    public void setCategoryJLabel(JLabel categoryJLabel) {
        this.categoryJLabel = categoryJLabel;
    }

    public JLabel getAreaJLabel() {
        return areaJLabel;
    }

    public void setAreaJLabel(JLabel areaJLabel) {
        this.areaJLabel = areaJLabel;
    }

    public JLabel getInstructionsJLabel() {
        return instructionsJLabel;
    }

    public void setInstructionsJLabel(JLabel instructionsJLabel) {
        this.instructionsJLabel = instructionsJLabel;
    }

    public JLabel getMealJLabel() {
        return mealJLabel;
    }

    public void setMealJLabel(JLabel mealJLabel) {
        this.mealJLabel = mealJLabel;
    }

    public JScrollPane getjScrollInsrt() {
        return jScrollInsrt;
    }

    public void setjScrollInsrt(JScrollPane jScrollInsrt) {
        this.jScrollInsrt = jScrollInsrt;
    }

    public JPanel getJPanelForCharts() {
        return JPanelForCharts;
    }

    public void setJPanelForCharts(JPanel JPanelForCharts) {
        this.JPanelForCharts = JPanelForCharts;
    }

    public JPanel getJPanelForButChar() {
        return JPanelForButChar;
    }

    public void setJPanelForButChar(JPanel JPanelForButChar) {
        this.JPanelForButChar = JPanelForButChar;
    }

    public JLabel getPie() {
        return Pie;
    }

    public void setPie(JLabel pie) {
        Pie = pie;
    }

    public JLabel getBar() {
        return Bar;
    }

    public void setBar(JLabel bar) {
        Bar = bar;
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

    public void setCategoriesPanel(String text) {
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

    public JPanel JPanelForCharts() {
        return firstPanel;
    }

    public void JPanelForCharts(JPanel firstPanel) {
        this.firstPanel = firstPanel;
    }

    public JTextArea getMealsName() {
        return mealsName;
    }

    public void setMealsName(String text) {
        mealsName.setText(text);
    }

    public JButton getSaveButton() {
        return SaveButton;
    }

    public void setSaveButton(JButton saveButton) {
        SaveButton = saveButton;
    }

    public JButton getEditButton() {
        return EditButton;
    }

    public void setEditButton(JButton editButton) {
        EditButton = editButton;
    }

    public JButton getDeleteButton() {
        return DeleteButton;
    }

    public void setDeleteButton(JButton deleteButton) {
        DeleteButton = deleteButton;
    }

    public JButton getSaveEdited() {
        return SaveEdited;
    }

    public void setSaveEdited(JButton saveEdited) {
        SaveEdited = saveEdited;
    }

    public JTextArea getCategories() {
        return categories;
    }

    public void setCategories(String text) {
        categories.setText(text);
    }

    public JTextArea getArea() {
        return Area;
    }

    public void setArea(String text) {
        Area.setText(text);
    }

    public JTextArea getInstructions() {
        return Instructions;
    }

    public void setInstructions(String text) {
        Instructions.setText(text);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}