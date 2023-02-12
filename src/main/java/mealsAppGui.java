import org.database.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


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
                    if (db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                        JOptionPane.showMessageDialog(null, "ΠΡΟΣΟΧΗ!!!!\nΥπάρχει περίπτωση το γεύμα να έχει ΗΔΗ τροποποιηθεί \nοπότε και η τροποποιημένη του έκδοση θα βρίσκεται ήδη\n" +
                                "στην Βάση Δεδομένων σου.\nΓια να έχεις πρόσβαση στο σε αυτήν την λειτουργία θα \nπρέπει να περιμένεις την " +
                                "επόμενη έκδοση του MealsDB.", "EDITED", JOptionPane.INFORMATION_MESSAGE);
                    }else if (!db.idSearch(Integer.valueOf(meal.getId()))){
                        db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                    }else {
                        //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                        db.incrementViews(Integer.valueOf(meal.getId()));
                    }

                    //Δημιουργία JFrame με 4 διαφορετικές JTextArea ώστε να αποθηκευτούν τα δεδομένα κατά την τροποποίηση
                    //πιο εύκολα
                    JFrame frame = new JFrame("Meal Details");

                    JTextArea mealsArea = new JTextArea();
                    mealsArea.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0,4,0,4,Color.WHITE),
                            BorderFactory.createEmptyBorder(0,0,0,0)));
                    mealsArea.setText(meal.getName());
                    mealsArea.setLineWrap(true);
                    mealsArea.setWrapStyleWord(true);
                    mealsArea.setEditable(false);

                    JTextArea category = new JTextArea();
                    category.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0,4,0,4,Color.WHITE),
                            BorderFactory.createEmptyBorder(0,0,0,0)));
                    category.setText(meal.getCategory());
                    category.setLineWrap(true);
                    category.setWrapStyleWord(true);
                    category.setEditable(false);

                    JTextArea Area = new JTextArea();
                    Area.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0,4,0,4,Color.WHITE),
                            BorderFactory.createEmptyBorder(0,0,0,0)));
                    Area.setText(meal.getArea());
                    Area.setLineWrap(true);
                    Area.setWrapStyleWord(true);
                    Area.setEditable(false);

                    JTextArea Instructions = new JTextArea();
                    Instructions.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createMatteBorder(0,4,0,4,Color.WHITE),
                            BorderFactory.createEmptyBorder(0,0,0,0)));
                    Instructions.setText(meal.getInstructions());
                    Instructions.setLineWrap(true);
                    Instructions.setWrapStyleWord(true);
                    Instructions.setEditable(false);

                    JScrollPane scrollPane1 = new JScrollPane(mealsArea);
                    JScrollPane scrollPane2 = new JScrollPane(category);
                    JScrollPane scrollPane3 = new JScrollPane(Area);
                    JScrollPane scrollPane4 = new JScrollPane(Instructions);


                    //Προσθήκη κουμπιών SAVE-EXIT-DELETE-CLOSE στο εξτρά panel στο κάτω μέρος του παραθύρου
                    JButton SaveButton = new JButton("SAVE");
                    JButton EditButton = new JButton("EDIT");
                    JButton DeleteButton = new JButton("DELETE");
                    JButton SaveEdited = new JButton("SAVE EDITED");

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

                    scrollPane1.setPreferredSize(new Dimension(500, 25));
                    scrollPane2.setPreferredSize(new Dimension(500, 25));
                    scrollPane3.setPreferredSize(new Dimension(500, 25));
                    scrollPane4.setPreferredSize(new Dimension(500, 430));

                    ImageIcon image = new ImageIcon("logo.png");

                    Font font1 = mealsArea.getFont();
                    font1 = font1.deriveFont(16f); // change the font size to 16
                    mealsArea.setFont(font1);

                    Font font2 = category.getFont();
                    font2 = font2.deriveFont(16f); // change the font size to 16
                    category.setFont(font2);

                    Font font3 = Area.getFont();
                    font3 = font3.deriveFont(16f); // change the font size to 16
                    Area.setFont(font3);

                    panel.add(buttonPanel, BorderLayout.SOUTH);

                    frame.setIconImage(image.getImage());
                    frame.getContentPane().add(panel, BorderLayout.CENTER);
                    frame.setResizable(true);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);

                    buttonPanel.add(DeleteButton);
                    panel.add(buttonPanel,BorderLayout.SOUTH);

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

                    //Listeners για ανωτέρω κουμπιά
                    SaveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (!db.idSearchInSAVED(Integer.valueOf(meal.getId()))) {
                                int save = JOptionPane.showConfirmDialog(null,
                                        "Είσαι σίγουρος οτι θέλεις να αποθηκεύσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                                if (save == JOptionPane.YES_NO_OPTION) {
                                    db.saveToNewTable(Integer.valueOf(meal.getId()));
                                    EditButton.setEnabled(true);
                                    DeleteButton.setEnabled(true);
                                }
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
                            int edit = JOptionPane.showConfirmDialog(null,
                                    "Είσαι σίγουρος οτι θέλεις να τροποποιήσεις το γεύμα?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                            if (edit == JOptionPane.YES_NO_OPTION) {
                                //απενεργοποίηση κουμπιών και ενεργοποίηση των πεδίων προς τροποποίηση
                                SaveEdited.setEnabled(true);
                                SaveButton.setEnabled(false);
                                EditButton.setEnabled(false);
                                DeleteButton.setEnabled(false);
                                mealsArea.setEditable(true);
                                category.setEditable(true);
                                Area.setEditable(true);
                                Instructions.setEditable(true);

                                //listener κουμπιού SAVE EDITED
                                SaveEdited.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        int ID = Integer.valueOf(meal.getId());
                                        try {
                                            //αποθήκευση στη ΒΔ και συγκεκριμένα στον πίνακα SAVED την τροποποίηση
                                            SaveEdited.setEnabled(false);
                                            Connection connection = db.connect();
                                            Statement stmt = connection.createStatement();
                                            String modifiedFields = "UPDATE SAVED SET "
                                                    + "Όνομα = '" + mealsArea.getText() + "', "
                                                    + "Κατηγορία = '" + category.getText() + "', "
                                                    + "Περιοχή = '" + mealsArea.getText() + "', "
                                                    + "Οδηγίες = '" + Instructions.getText() + "' "
                                                    + "WHERE ID = " + ID;
                                            stmt.executeUpdate(modifiedFields);
                                            connection.commit();
                                            SaveButton.setEnabled(false);
                                            EditButton.setEnabled(true);
                                            DeleteButton.setEnabled(true);
                                            JOptionPane.showMessageDialog(null, "Το γεύμα τροποποιήθηκε", "EDITED", JOptionPane.INFORMATION_MESSAGE);
                                        } catch (SQLException exception) {
                                            System.out.println(exception.getLocalizedMessage());
                                        }
                                    }
                                });
                            }
                        }
                    });
                    //Τέλος listeners
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

            //    popUpListTree pop = new popUpListTree();
            //    pop.popUpWindow();

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
        /*
        frame.setUndecorated(true);
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
