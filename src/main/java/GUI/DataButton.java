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







                //Τέλος listeners
            } else {
                //εάν δεν υπάρχει το γεύμα στο API τότε εμφάνιση μηνύματος
                JOptionPane.showMessageDialog(null, "Το Γεύμα που εισάγατε δεν υπάρχει", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });
}

    private JTextArea createTextArea(String text) {
        JTextArea area = new JTextArea();
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,4,0,4,Color.WHITE),
                BorderFactory.createEmptyBorder(0,0,0,0)));
        area.setText(text);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        return area;
    }
    private JScrollPane createScrollPane(JTextArea area) {
        JScrollPane scrollPane = new JScrollPane(area);
        scrollPane.setPreferredSize(new Dimension(500, 25));
        return scrollPane;
    }
}