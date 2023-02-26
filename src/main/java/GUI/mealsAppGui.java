package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.*;


public class mealsAppGui extends JFrame {
    private static mealsAppGui instance;
    private JPanel mainPanel,leftSidePanel,rightSidePanel,BackGdPanel,startPanel,searchingPanel,categoriesPanel,statsPanel,
            firstPanel,jPanelForText,jPanelForButtons,JPanelForCharts,JPanelForButChar;
    private JLabel appIcon,appTitle,dataIcon,dataTitle,categoryIcon,categoryTitle,exitTitle,exitIcon,StatsTitle,statsIcon,
            firstLabel,categoryJLabel,areaJLabel,instructionsJLabel,mealJLabel,Pie,Bar;
    private JTextArea mealsName,categories,Area,Instructions;
    private JLabel SaveButton,EditButton,DeleteButton,SaveEdited;
    private JScrollPane jScrollInsrt;
    private JLabel Print;
    private JTree categoriesTree;
    private JScrollPane jTreeScrollPane;
    private BackGroundPanel backGroundPanel;
    DataButton dataButton = new DataButton();

    //Χρήση singleton για την εναλλαγή JPanels
    public static mealsAppGui getInstance() {
        if (instance == null) {
            instance = new mealsAppGui();
        }
        return instance;
    }

    private mealsAppGui() {
        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setContentPane(mainPanel);
        this.pack();
        FrameDragListaner.FrameDragListener frameDragListener = new FrameDragListaner.FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);

        Database.startDB();
        leftSidePanel.setOpaque(false);
        BackGdPanel.setOpaque(false);
        BackGdPanel = new BackGroundPanel();
        mainPanel.add(BackGdPanel);
        appIcon.setIcon(new ImageIcon("src/resources/images/logo.png"));
        firstLabel.setIcon(new ImageIcon("src/resources/images/background.png"));
        firstPanel.setVisible(true);
        dataIcon.setIcon(new ImageIcon("src/resources/images/search.png"));
        categoryIcon.setIcon(new ImageIcon("src/resources/images/categorize.png"));
        statsIcon.setIcon(new ImageIcon("src/resources/images/stats.png"));
        exitIcon.setIcon(new ImageIcon("src/resources/images/shutdown.png"));
        DeleteButton.setBackground(Color.RED);
        mealJLabel.setVisible(false);
        categoryJLabel.setVisible(false);
        areaJLabel.setVisible(false);
        instructionsJLabel.setVisible(false);
        jScrollInsrt.setVisible(false);
        Buttons();

        //Κλήση κουμπιών (τα οποία είναι JLabel) που είναι bound με το .form
        Print.addMouseListener(new PrintListener());

        PieListener pieListener = new PieListener(rightSidePanel, statsPanel, JPanelForButChar,
                JPanelForCharts, Pie, Bar, mainPanel);
        Pie.addMouseListener(pieListener);

        BarListener barListener = new BarListener(rightSidePanel, statsPanel, JPanelForButChar,
                JPanelForCharts, Pie, Bar, mainPanel);
        Bar.addMouseListener(barListener);

        SaveButtonListener saveButtonListener = new SaveButtonListener(SaveButton, EditButton, DeleteButton, dataButton);
        SaveButton.addMouseListener(saveButtonListener);

        EditButtonListener editButtonListener = new EditButtonListener(EditButton, SaveButton, DeleteButton, SaveEdited,
                mealsName, categories, Area, Instructions);
        EditButton.addMouseListener(editButtonListener);

        DeleteButtonListener deleteListener = new DeleteButtonListener(DeleteButton, EditButton, SaveButton, dataButton);
        DeleteButton.addMouseListener(deleteListener);

        SaveEditedListener saveEditedListener = new SaveEditedListener(SaveEdited, dataButton, Instructions, mealsName, categories,
                Area, SaveButton, EditButton, DeleteButton);
        SaveEdited.addMouseListener(saveEditedListener);
    }


    public void Buttons() {
        //κουμπί προβολής δεδομένων γεύματος
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

    public JPanel getMainPanel() {
        return mainPanel;
    }
    public JPanel getRightSidePanel() {
        return rightSidePanel;
    }
    public JPanel getjPanelForText() {
        return jPanelForText;
    }
    public JPanel getjPanelForButtons() {
        return jPanelForButtons;
    }
    public JLabel getCategoryJLabel() {
        return categoryJLabel;
    }
    public JLabel getAreaJLabel() {
        return areaJLabel;
    }
    public JLabel getInstructionsJLabel() {
        return instructionsJLabel;
    }
    public JLabel getMealJLabel() {
        return mealJLabel;
    }
    public JScrollPane getjScrollInsrt() {
        return jScrollInsrt;
    }
    public JPanel getJPanelForCharts() {
        return JPanelForCharts;
    }
    public JPanel getJPanelForButChar() {
        return JPanelForButChar;
    }
    public JLabel getPie() {
        return Pie;
    }
    public JLabel getBar() {
        return Bar;
    }
    public JLabel getPrint() {
        return Print;
    }
    public JPanel getSearchingPanel() {
        return searchingPanel;
    }
    public JPanel getStatsPanel() {
        return statsPanel;
    }
    public void setMealsName(String text) {
        mealsName.setText(text);
    }
    public JLabel getEditButton() {
        return EditButton;
    }
    public JLabel getDeleteButton() {
        return DeleteButton;
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
    public JTree getCategoriesTree() {
        return categoriesTree;
    }
    public JPanel getCategoriesPanel() {
        return categoriesPanel;
    }
    public JLabel getSaveButton() {
        return SaveButton;
    }
    public JLabel getSaveEdited() {
        return SaveEdited;
    }
}