import org.database.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class mealsAppGui {
    private JPanel mainPanel;
    private JButton button1;
    private JButton button4;
    private JButton button2;
    private JButton button3;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel centerPanel;
    private JButton EXITButton;


    public mealsAppGui() {
        //κουμπί προβολής δεδομένων γεύματος
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
                    //αναζήτηση στη ΒΔ εάν έχει γίνει ξανά αναζήτηση του γεύματος
                    //εάν δεν έχει γίνει τότε εισαγωγή στη ΒΔ
                    if (!db.idSearch(Integer.valueOf(meal.getId()))){
                        db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                    }else {
                        //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                        db.incrementViews(Integer.valueOf(meal.getId()));
                    }

                    //Δημιουργία παραθύρου με τα ζητούμενα στοιχεία
                    JTextArea textArea = new JTextArea();
                    textArea.setText("Meal: " + meal.getName() + "\n\nCategory: " + meal.getCategory() + "\n\nArea: " + meal.getArea() + "\n\nInstructions: " + meal.getInstructions());
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setEditable(false);

                    JScrollPane scrollPane = new JScrollPane(textArea);

                    //Προσθήκη κουμπιών SAVE-EXIT-DELETE-CLOSE στο εξτρά panel στο κάτω μέρος του παραθύρου
                    JButton SaveButton = new JButton("SAVE");
                    JButton EditButton = new JButton("EDIT");
                    JButton DeleteButton = new JButton("DELETE");

                    JPanel panel = new JPanel();
                    panel.setLayout(new BorderLayout());
                    panel.add(scrollPane,BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel();
                    buttonPanel.add(SaveButton);
                    buttonPanel.add(EditButton);
                    //ενεργοποίηση ή απενεργοποίηση του κουμποιύ EDIT ανάλογα εάν είναι αποθηκευμένο το γεύμα
                    if(!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                        EditButton.setEnabled(false);
                    }else {
                        EditButton.setEnabled(true);
                    }
                    buttonPanel.add(DeleteButton);
                    //ενεργοποίηση ή απενεργοποίηση του κουμποιύ DELETE ανάλογα εάν είναι αποθηκευμένο το γεύμα
                    if(!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                        DeleteButton.setEnabled(false);
                    }else {
                        DeleteButton.setEnabled(true);
                    }
                    panel.add(buttonPanel,BorderLayout.SOUTH);
                    //Τέλος, προσθήκης κουμπιών

                    //Listeners για ανωτέρω κουμπιά
                    SaveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                                db.saveToNewTable(Integer.valueOf(meal.getId()));
                                EditButton.setEnabled(true);
                                DeleteButton.setEnabled(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Το γεύμα είναι ήδη αποθηκευμένο", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                                EditButton.setEnabled(true);
                            }
                        }
                    });

                    DeleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                                db.deleteSavedTable(Integer.valueOf(meal.getId()));
                                DeleteButton.setEnabled(false);
                                EditButton.setEnabled(false);
                            } else {
                                JOptionPane.showMessageDialog(null, "Το γεύμα δεν είναι αποθηκευμένο!!!", "SAVED", JOptionPane.INFORMATION_MESSAGE);
                                DeleteButton.setEnabled(false);
                            }
                        }
                    });


                    EditButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                        }
                    });
                    //Τέλος listeners

                    scrollPane.setPreferredSize(new Dimension(500, 500));

                    JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                    JDialog dialog = optionPane.createDialog(null, "Meal Details");
                    ImageIcon image = new ImageIcon("logo.png");
                    dialog.setIconImage(image.getImage());
                    dialog.setResizable(true);
                    dialog.setVisible(true);

                } else {
                    //εάν δεν υπάρχει το γεύμα στο API τότε εμφάνιση μηνύματος
                    JOptionPane.showMessageDialog(null, "Το Γεύμα που εισάγατε δεν υπάρχει", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //κουμπί προβολής λίστας γευμάτων ανα κατηγορία γεύματος
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpListTree obj = null;
                try {
                    //εμφάνιση παραθύρου
                    obj = popUpListTree.getInstance();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                obj.popUpWindow();
            }
        });
        //κουμπί προβολής στατιστικών δεδομένων γευμάτων και εκτύπωση σε αρχείο pdf
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        //κουμπί έξοδος
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == EXITButton) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Είσαι σίγουρος οτι θέλεις να κάνεις έξοδο?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_NO_OPTION) {
                        Database db = Database.getInstance();
                        db.dropDatabase();
                        System.out.println("Database droped");
                        System.exit(0);
                    }
                }
            }
        });
    }

    //ρυθμίσεις κεντρικού μενού
    public void JFrameMain (){
        JFrame frame = new JFrame("MainGui");

     //   Διαγράφει την πάνω καρτέλα με το Χ
     /*   frame.setUndecorated(true);
        frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
    */
        frame.setSize(800,400);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new mealsAppGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("MealsApp");
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.DARK_GRAY));
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());

    }
}
