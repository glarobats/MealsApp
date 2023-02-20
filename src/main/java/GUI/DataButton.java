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

            Database db = Database.getInstance();
            //εάν είναι κενό το πεδίο και πατήσεις ΟΚ
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Δεν δόθηκε κανένας όρος αναζήτησης", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            //προβολή JPanel που αντιστοιχεί στην αναζήτηση και απόκρυψη των υπολοίπων

            //εάν δεν είναι κενό το πεδίο
            if (meal != null) {
                if (!db.idSearch(Integer.valueOf(meal.getId()))){
                    db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                }else {
                    //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                    db.incrementViews(Integer.valueOf(meal.getId()));
                }

                mealsAppGui gui = mealsAppGui.getInstance();
                gui.showSearchPanel();

                //JPanel for text and Labels
                String mealName = meal.getName();
                String categoryName = meal.getCategory();
                String areaName = meal.getArea();
                String instructionsName = meal.getInstructions();

                gui.setMealsName(mealName);
                gui.setCategories(categoryName);
                gui.setArea(areaName);
                gui.setInstructions(instructionsName);





     /*              frame.getRootPane().setDefaultButton(OkButton);//Ορίζει ως default button το ΟΚ και με το άνοιγμα του

              //ενεργοποίηση ή απενεργοποίηση του κουμπιού EDIT ανάλογα εάν είναι αποθηκευμένο το γεύμα
                if(!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                    EditButton.setEnabled(false);
                }else {
                    EditButton.setEnabled(true);
                }

                //ενεργοποίηση ή απενεργοποίηση του κουμπιού DELETE ανάλογα εάν είναι αποθηκευμένο το γεύμα
                if(!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                    DeleteButton.setEnabled(false);
                }else {
                    DeleteButton.setEnabled(true);
                }

                //απενεργοποίηση κουμπιού SAVE EDITED
                SaveEdited.setEnabled(false);
                //Τέλος, προσθήκης κουμπιών


                //listener κουμπιού SAVEbutton
                SaveButton.addActionListener(new SaveButtonListener(meal, SaveButton, EditButton, DeleteButton, db));
                //listener κουμπιού DeleteButton
                DeleteButton.addActionListener(new DeleteButtonListener(meal, EditButton, DeleteButton,SaveButton, db));
                //listener κουμπιού EditButton
                EditButton.addActionListener(new EditButtonListener(meal, SaveButton, SaveEdited, EditButton, DeleteButton, db, mealsArea, category, Area, Instructions));
                //listener κουμπιού SaveEdited
                SaveEdited.addActionListener(new SaveEditedButtonListener(meal, SaveButton, SaveEdited, EditButton, DeleteButton, db, mealsArea, category, Instructions));
                //listener κουμπιού OK

      */

            /*    OkButton.addActionListener(new OKButtonListener(frame));

             */
                //Τέλος listeners
            } else {
                //εάν δεν υπάρχει το γεύμα στο API τότε εμφάνιση μηνύματος
                JOptionPane.showMessageDialog(null, "Το Γεύμα που εισάγατε δεν υπάρχει", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}
}