package GUI;

import org.database.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class DataButton extends JPanel {



    public void addDataButtonListener(JLabel dataTitle) {

    dataTitle.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {



            //Παράθυρο για αναζήτηση
            mealApi mealApi = new mealApi();
            String searchTerm = JOptionPane.showInputDialog("Αναζητήστε το Γεύμα που θέλετε: ");
            Meal meal = mealApi.searchByName(searchTerm);

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

                mealsAppGui gui = mealsAppGui.getInstance();

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
                gui.getjPanelForText().setVisible(true);
                gui.getjScrollInsrt().setVisible(true);
                gui.getMainPanel().revalidate();
                gui.getMainPanel().repaint();

                //JPanel for text and Labels
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
                //Τέλος, προσθήκης κουμπιών

                //listener κουμπιού SAVEbutton
                gui.getSaveButton().addActionListener(new SaveButtonListener(meal,gui.getSaveButton(),
                        gui.getEditButton(),gui.getDeleteButton()));
                //listener κουμπιού DeleteButton
                gui.getDeleteButton().addActionListener(new DeleteButtonListener(meal, gui.getEditButton(),
                        gui.getDeleteButton(),gui.getSaveButton()));
                //listener κουμπιού EditButton
                gui.getEditButton().addActionListener(new EditButtonListener(meal, gui.getSaveButton(),
                        gui.getSaveEdited(), gui.getEditButton(), gui.getDeleteButton(),
                         gui.getMealsName(), gui.getCategories() , gui.getArea(), gui.getInstructions()));
                //listener κουμπιού SaveEdited
                gui.getSaveEdited().addActionListener(new SaveEditedButtonListener(meal, gui.getSaveButton(),
                        gui.getSaveEdited(), gui.getEditButton(), gui.getDeleteButton(),
                         gui.getMealsName(), gui.getCategories(), gui.getInstructions()));
                //Τέλος listeners
            } else {
                //εάν δεν υπάρχει το γεύμα στο API τότε εμφάνιση μηνύματος
                JOptionPane.showMessageDialog(null, "Το Γεύμα που εισάγατε δεν υπάρχει", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}
}