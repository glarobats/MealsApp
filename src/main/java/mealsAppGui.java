import Pdf.Chart;
import Pdf.ViewsPDF;
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
                    if (!db.idSearch(Integer.valueOf(meal.getId()))){
                        db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                    }else {
                        //διαφορετικά ενημέρωση του πίνακα VIEWS με αύξηση κατά 1 του κελιού εμφανίσεις
                        db.incrementViews(Integer.valueOf(meal.getId()));
                    }

                    //Δημιουργία JFrame με 4 διαφορετικές JTextArea ώστε να αποθηκευτούν τα δεδομένα κατά την τροποποίηση
                    //πιο εύκολα
                    JFrame frame = new JFrame("Meal Details");

                    JTextArea mealsArea = createTextArea(meal.getName());
                    JTextArea category = createTextArea(meal.getCategory());
                    JTextArea Area = createTextArea(meal.getArea());
                    JTextArea Instructions = createTextArea(meal.getInstructions());

                    JScrollPane scrollPane1 = createScrollPane(mealsArea);
                    JScrollPane scrollPane2 = createScrollPane(category);
                    JScrollPane scrollPane3 = createScrollPane(Area);
                    JScrollPane scrollPane4 = createScrollPane(Instructions);
                    scrollPane4.setPreferredSize(new Dimension(500, 430));


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

                    buttonPanel.add(DeleteButton);
                    panel.add(buttonPanel,BorderLayout.SOUTH);

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
                                    SaveButton.setEnabled(false);
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
                                SaveButton.setEnabled(true);
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
                                            mealsArea.setEditable(false);
                                            SaveButton.setEnabled(false);
                                            EditButton.setEnabled(true);
                                            DeleteButton.setEnabled(true);
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
                Database db = Database.getInstance();
                db.orderBy();

                ViewsPDF view = new ViewsPDF();
                view.viewPdf();

                Chart chart = new Chart();
                chart.makeChart();
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
                        db.deleteDB();
                        System.out.println("Επιτυχείς διαγραφή δεδομένων");
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
