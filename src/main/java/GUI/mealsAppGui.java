package GUI;

import Pdf.Chart;
import Pdf.ViewsPDF;
import org.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class mealsAppGui extends JFrame {
    private static mealsAppGui instance;
    private JPanel mainPanel,leftSidePanel,rightSidePanel,BackGdPanel,startPanel,searchingPanel,categoriesPanel,statsPanel,
            firstPanel,jPanelForText,jPanelForButtons,JPanelForCharts,JPanelForButChar;
    private JLabel appIcon,appTitle,dataIcon,dataTitle,categoryIcon,categoryTitle,exitTitle,exitIcon,StatsTitle,statsIcon,
            categoriesLabel,firstLabel,categoryJLabel,areaJLabel,instructionsJLabel,mealJLabel,Pie,Bar;
    private JTextArea mealsName,categories,Area,Instructions;
    private JButton SaveButton,EditButton,DeleteButton,SaveEdited;
    private JScrollPane jScrollInsrt;
    private JLabel Print;
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
        //setUndecorated(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        Database.startDB();
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



        Print.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                ViewsPDF statistika = new ViewsPDF();
                statistika.viewPdf();
            }
        });
        Pie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Chart statistika = new Chart();
                rightSidePanel.removeAll();
                JPanelForCharts.removeAll();
                rightSidePanel.add(statsPanel);
                statsPanel.setLayout(new BorderLayout());
                statsPanel.add(JPanelForButChar, BorderLayout.SOUTH);
                statsPanel.add(JPanelForCharts, BorderLayout.NORTH);
                JPanelForButChar.setBackground(new Color(176,166,145));
                JPanelForCharts.add(statistika.makePieChart());
                JPanelForCharts.setVisible(true);
                JPanelForButChar.setVisible(true);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        Bar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Chart statistika = new Chart();
                rightSidePanel.removeAll();
                JPanelForCharts.removeAll();
                rightSidePanel.add(statsPanel);
                statsPanel.setLayout(new BorderLayout());
                statsPanel.add(JPanelForButChar, BorderLayout.SOUTH);
                statsPanel.add(JPanelForCharts, BorderLayout.NORTH);
                JPanelForButChar.setBackground(new Color(176,166,145));
                JPanelForCharts.add(statistika.makeBarChart());
                JPanelForCharts.setVisible(true);
                JPanelForButChar.setVisible(true);
                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
        SaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Database.idSearchInSAVED(dataButton.getMealId())) {
                    int save = JOptionPane.showConfirmDialog(null,
                            "Είσαι σίγουρος οτι θέλεις να αποθηκεύσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                    if (save == JOptionPane.YES_NO_OPTION) {
                        Database.saveToNewTable(dataButton.getMealId());
                        EditButton.setEnabled(true);
                        DeleteButton.setEnabled(true);
                        SaveButton.setEnabled(false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Το γεύμα είναι ήδη αποθηκευμένο", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                    EditButton.setEnabled(true);
                }
            }
        });
        EditButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int edit = JOptionPane.showConfirmDialog(null,
                        "Είσαι σίγουρος οτι θέλεις να τροποποιήσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                if (edit == JOptionPane.YES_NO_OPTION) {
                    //απενεργοποίηση κουμπιών και ενεργοποίηση των πεδίων προς τροποποίηση
                    SaveEdited.setEnabled(true);
                    SaveButton.setEnabled(false);
                    EditButton.setEnabled(false);
                    DeleteButton.setEnabled(false);
                    mealsName.setEditable(true);
                    categories.setEditable(true);
                    Area.setEditable(true);
                    Instructions.setEditable(true);
                }
            }
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Database.idSearchInSAVED(Integer.valueOf(dataButton.getMealId()))) {
                    Database.deleteSavedTable(Integer.valueOf(dataButton.getMealId()));
                    DeleteButton.setEnabled(false);
                    EditButton.setEnabled(false);
                    SaveEdited.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Το γεύμα δεν είναι αποθηκευμένο!!!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                    DeleteButton.setEnabled(false);
                }
            }
        });
        SaveEdited.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int ID = Integer.valueOf(dataButton.getMealId());
                try {
                    //αποθήκευση στη ΒΔ και συγκεκριμένα στον πίνακα SAVED την τροποποίηση

                    SaveEdited.setEnabled(false);
                    Instructions.setEditable(false);
                    mealsName.setEditable(false);
                    categories.setEditable(false);
                    Area.setEditable(false);
                    Connection connection = Database.connect();
                    Statement stmt = (Statement) connection.createStatement();
                    String modifiedFields = "UPDATE SAVED SET "
                            + "Όνομα = '" + mealsName.getText() + "', "
                            + "Κατηγορία = '" + categories.getText() + "', "
                            + "Περιοχή = '" + Area.getText() + "', "
                            + "Οδηγίες = '" + Instructions.getText() + "' "
                            + "WHERE ID = " + ID;
                    stmt.executeUpdate(modifiedFields);
                    connection.commit();
                    Area.setEditable(false);
                    SaveButton.setEnabled(false);
                    EditButton.setEnabled(true);
                    DeleteButton.setEnabled(true);
                } catch (SQLException exception) {
                    System.out.println(exception.getLocalizedMessage());
                }
            }
        });
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
    public JButton getEditButton() {
        return EditButton;
    }
    public JButton getDeleteButton() {
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
}