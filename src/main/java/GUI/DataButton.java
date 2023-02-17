package GUI;

import org.database.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DataButton {

public void addButton1ActionListener(JButton button1) {
    button1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
            //εάν δεν είναι κενό το πεδίο
            if (meal != null) {
                if (!db.idSearch(Integer.valueOf(meal.getId()))){
                    db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                }else {
                    //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                    db.incrementViews(Integer.valueOf(meal.getId()));
                }

                //Δημιουργία JFrame με 4 διαφορετικές JTextArea ώστε να αποθηκευτούν τα δεδομένα κατά την τροποποίηση
                //πιο εύκολα
                JFrame frame = new JFrame("Meal Details");

                frame.getRootPane().setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.BLACK));

                JTextArea mealsArea = createTextArea(meal.getName());
                JTextArea category = createTextArea(meal.getCategory());
                JTextArea Area = createTextArea(meal.getArea());
                JTextArea Instructions = createTextArea(meal.getInstructions());

                JScrollPane scrollPane1 = createScrollPane(mealsArea);
                JScrollPane scrollPane2 = createScrollPane(category);
                JScrollPane scrollPane3 = createScrollPane(Area);
                JScrollPane scrollPane4 = createScrollPane(Instructions);


                //Προσθήκη κουμπιών SAVE-EXIT-DELETE-CLOSE στο εξτρά panel στο κάτω μέρος του παραθύρου
                JButton SaveButton = new JButton("SAVE");
                JButton EditButton = new JButton("EDIT");
                JButton DeleteButton = new JButton("DELETE");
                DeleteButton.setBackground(Color.pink);
                JButton SaveEdited = new JButton("SAVE EDITED");
                JButton OkButton = new JButton("OK");

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

                panel.add(scrollPane1);
                panel.add(scrollPane2);
                panel.add(scrollPane3);
                panel.add(scrollPane4);

                JPanel buttonPanel = new JPanel();
                buttonPanel.add(SaveButton);
                buttonPanel.add(EditButton);
                buttonPanel.add(DeleteButton);
                buttonPanel.add(SaveEdited);
                buttonPanel.add(OkButton);

                scrollPane1.setPreferredSize(new Dimension(500, 25));
                scrollPane2.setPreferredSize(new Dimension(500, 25));
                scrollPane3.setPreferredSize(new Dimension(500, 25));
                scrollPane4.setPreferredSize(new Dimension(500, 430));

                ImageIcon image = new ImageIcon("logo.png");

                Font font = mealsArea.getFont();
                font = font.deriveFont(16f); // Αλλαγή γραμματοσειράς σε 16
                mealsArea.setFont(font);
                category.setFont(font);
                Area.setFont(font);

                //JLabel
                JLabel label = new JLabel("Meal:");
                label.setFont(font);
                JPanel labelPanel = new JPanel();
                labelPanel.add(label, BorderLayout.WEST);
                panel.add(labelPanel, BorderLayout.WEST);
                panel.add(scrollPane1, BorderLayout.WEST);

                label = new JLabel("Category:");
                label.setFont(font);
                labelPanel = new JPanel();
                labelPanel.add(label, BorderLayout.WEST);
                panel.add(labelPanel, BorderLayout.WEST);
                panel.add(scrollPane2, BorderLayout.WEST);

                label = new JLabel("Area:");
                label.setFont(font);
                labelPanel = new JPanel();
                labelPanel.add(label, BorderLayout.WEST);
                panel.add(labelPanel, BorderLayout.WEST);
                panel.add(scrollPane3, BorderLayout.WEST);

                label = new JLabel("Instructions:");
                label.setFont(font);
                labelPanel = new JPanel();
                labelPanel.add(label, BorderLayout.WEST);
                panel.add(labelPanel, BorderLayout.WEST);
                panel.add(scrollPane4, BorderLayout.WEST);

                buttonPanel.add(OkButton);
                panel.add(buttonPanel,BorderLayout.SOUTH);

                frame.setIconImage(image.getImage());
                frame.getContentPane().add(panel, BorderLayout.CENTER);
                frame.setResizable(true);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                buttonPanel.add(OkButton);
                panel.add(buttonPanel,BorderLayout.SOUTH);
                frame.getRootPane().setDefaultButton(OkButton);//Ορίζει ως default button το ΟΚ και με το άνοιγμα του
                // παραθύρου μπορεί να πατηθεί κατευθείαν το ΟΚ χωρίς να μετακινήσουμε το ποντίκι πάνω του.

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
                OkButton.addActionListener(new OKButtonListener(frame));
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