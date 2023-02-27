package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DataButton extends JPanel {
    private int mealId;

    public int getMealId() {
        return mealId;
    }



    public void addDataButtonListener(JLabel dataTitle) {

        dataTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                //Παράθυρο για αναζήτηση
                mealApi mealApi = new mealApi();
                String searchTerm = JOptionPane.showInputDialog("Αναζητήστε το Γεύμα που θέλετε: ");
                Meal meal = GUI.mealApi.searchByName(searchTerm);


                //εάν είναι κενό το πεδίο και πατήσεις ΟΚ
                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Δεν δόθηκε κανένας όρος αναζήτησης", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //προβολή JPanel που αντιστοιχεί στην αναζήτηση και απόκρυψη των υπολοίπων

                //εάν δεν είναι κενό το πεδίο
                if (meal != null) {
                    if (!Database.idSearch(Integer.parseInt(meal.getId()))){
                        Database.insMeal(Integer.parseInt(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                    }else {
                        //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                        Database.incrementViews(Integer.parseInt(meal.getId()));
                    }
                    mealId = Integer.parseInt(meal.getId());

                    mealsAppGui gui = mealsAppGui.getInstance();
                    //Εφόσον πατηθεί το κουμπί "κλείνουν" τα πάνελ και ανοίγουν μόνο αυτά που χρειάζονται
                    gui.getRightSidePanel().removeAll();
                    gui.getRightSidePanel().add(gui.getSearchingPanel());
                    gui.getSearchingPanel().setLayout(new BorderLayout());
                    gui.getSearchingPanel().add(gui.getjPanelForText(), BorderLayout.NORTH);
                    gui.getSearchingPanel().add(gui.getjPanelForButtons(), BorderLayout.SOUTH);
                    JViewport viewport = gui.getjScrollInsrt().getViewport();
                    viewport.setViewPosition(new Point(0,0));
                    gui.getMealJLabel().setVisible(true);
                    gui.getCategoryJLabel().setVisible(true);
                    gui.getAreaJLabel().setVisible(true);
                    gui.getInstructionsJLabel().setVisible(true);
                    gui.getjPanelForButtons().setVisible(true);
                    gui.getjPanelForButtons().setBackground(new Color(176,166,145));
                    gui.getSaveButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    gui.getSaveButton().setFont(new Font("Calibri",Font.BOLD,16));
                    gui.getSaveButton().setForeground(Color.WHITE);
                    gui.getEditButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    gui.getEditButton().setFont(new Font("Calibri",Font.BOLD,16));
                    gui.getEditButton().setForeground(Color.WHITE);
                    gui.getDeleteButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    gui.getDeleteButton().setFont(new Font("Calibri",Font.BOLD,16));
                    gui.getDeleteButton().setForeground(Color.WHITE);
                    gui.getSaveEdited().setCursor(new Cursor(Cursor.HAND_CURSOR));
                    gui.getSaveEdited().setFont(new Font("Calibri",Font.BOLD,16));
                    gui.getSaveEdited().setForeground(Color.WHITE);
                    gui.getjPanelForText().setVisible(true);
                    gui.getjScrollInsrt().setVisible(true);
                    //ενεργοποίηση ή απενεργοποίηση κουμπιού SAVE εάν το γεύμα υπάρχει ήδη σωσμένο στη ΒΔ
                    if(Database.idSearchInSAVED(Integer.parseInt(meal.getId())))
                        gui.getSaveButton().setEnabled(false);
                    else
                        gui.getSaveButton().setEnabled(true);

                    gui.getMainPanel().revalidate();
                    gui.getMainPanel().repaint();

                    //JPanel για Labels
                    String mealName = meal.getName();
                    String categoryName = meal.getCategory();
                    String areaName = meal.getArea();
                    String instructionsName = meal.getInstructions();

                    gui.setMealsName(mealName);
                    gui.setCategories(categoryName);
                    gui.setArea(areaName);
                    gui.setInstructions(instructionsName);

                  //ενεργοποίηση ή απενεργοποίηση του κουμπιού EDIT ανάλογα εάν είναι αποθηκευμένο το γεύμα
                    if(!Database.idSearchInSAVED(Integer.parseInt(meal.getId()))) {
                        gui.getEditButton().setEnabled(false);
                    }else {
                        gui.getEditButton().setEnabled(true);
                    }

                    //ενεργοποίηση ή απενεργοποίηση του κουμπιού DELETE ανάλογα εάν είναι αποθηκευμένο το γεύμα
                    if(!Database.idSearchInSAVED(Integer.parseInt(meal.getId()))) {
                        gui.getDeleteButton().setEnabled(false);
                    }else {
                        gui.getDeleteButton().setEnabled(true);
                    }
                } else {
                    //εάν δεν υπάρχει το γεύμα στο API τότε εμφάνιση μηνύματος
                    JOptionPane.showMessageDialog(null, "Το Γεύμα που εισάγατε δεν υπάρχει", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}