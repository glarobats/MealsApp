import org.database.Database;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mealsAppGui {
    private JPanel mainPanel;
    private JButton button1;
    private JButton button4;
    private JButton button2;
    private JButton button3;
    private JButton SAVEButton;
    private JButton EDITButton;
    private JButton DELETEButton;

    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel centerPanel;
    private JTextArea centerTextfield;
    private JButton EXITButton;


    public mealsAppGui() {
//top buttons listeners
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mealApi mealApi = new mealApi();
                String searchTerm = JOptionPane.showInputDialog("Αναζητήστε το Γεύμα που θέλετε: ");
                Meal meal = mealApi.searchByName(searchTerm);

                if (searchTerm == null || searchTerm.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Δεν δόθηκε κανένας όρος αναζήτησης", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (meal != null) {
                    JTextArea textArea = new JTextArea();
                    textArea.setText("Meal: " + meal.getName() + "\n\nCategory: " + meal.getCategory() + "\n\nArea: " + meal.getArea() + "\n\nInstructions: " + meal.getInstructions());
                    textArea.setLineWrap(true);
                    textArea.setWrapStyleWord(true);
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 500));
                    JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
                    JDialog dialog = optionPane.createDialog(null, "Meal Details");
                    ImageIcon image = new ImageIcon("logo.png");
                    dialog.setIconImage(image.getImage());
                    dialog.setResizable(true);
                    dialog.setVisible(true);
                    Database db = new Database();
                    if (!db.idSearch(Integer.valueOf(meal.getId()))){
                        db.insMeal(Integer.valueOf(meal.getId()), meal.getName(), meal.getCategory(), meal.getArea(), meal.getInstructions());
                    } else {
                        JOptionPane.showMessageDialog(null, "Το γεύμα "+meal.getName()+" υπάρχει στην βάση δεδομένων", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Λάθος εισαγωγή", "Error", JOptionPane.ERROR_MESSAGE);
                }}
        });







        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpListTree obj = popUpListTree.getInstance();
                obj.popUpWindow();



            //    popUpListTree pop = new popUpListTree();
            //    pop.popUpWindow();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });



        //bottom buttons listeners
        SAVEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputPane = new JPanel();

                inputPane.setLayout(new GridLayout(5, 2));

                JTextField idField = new JTextField();
                JTextField nameField = new JTextField();
                JTextField categoryField = new JTextField();
                JTextField areaField = new JTextField();
                JTextField instructionsField = new JTextField();

                inputPane.add(new JLabel("ID:"));
                inputPane.add(idField);
                inputPane.add(new JLabel("Name:"));
                inputPane.add(nameField);
                inputPane.add(new JLabel("Category:"));
                inputPane.add(categoryField);
                inputPane.add(new JLabel("Area:"));
                inputPane.add(areaField);
                inputPane.add(new JLabel("Instructions:"));
                inputPane.add(instructionsField);

                int result = JOptionPane.showConfirmDialog(null, inputPane, "Νέα εγγραφή", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {



                    int id = Integer.parseInt(idField.getText());
                    String name = nameField.getText();
                    String category = categoryField.getText();
                    String area = areaField.getText();
                    String instructions = instructionsField.getText();

                    Database database = new Database();
                    database.insMeal(id, name, category, area, instructions);

                }
            }
        });
       


        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel inputPane = new JPanel();
                inputPane.setLayout(new GridLayout(1, 1));
                JTextField idField = new JTextField();
                int id = Integer.parseInt(idField.getText());
                int chois = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete the meal?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
                if (chois == JOptionPane.YES_OPTION) {
                    //Database database = new Database();
                    //database.deleteRow(id);
                }
            }

        });
        EDITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == EXITButton) {
                    int result = JOptionPane.showConfirmDialog(null,
                            "Είσαι σίγουρος οτι θέλεις να κάνεις έξοδο?", "Επίλεξε", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_NO_OPTION) {
                        Database db = new Database();
                        db.deleteData();
                        System.exit(0);
                    }
                }
            }
        });
    }

    public void JFrameMain (){
        JFrame frame = new JFrame("MainGui");
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new mealsAppGui().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setTitle("MealsApp");
        ImageIcon image = new ImageIcon("logo.png");
        frame.setIconImage(image.getImage());
    }
}
