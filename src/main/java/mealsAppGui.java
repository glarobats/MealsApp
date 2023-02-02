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
    private JButton SEARCHButton;
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
                mealAppApi meal = new mealAppApi();
                String searchTerm = JOptionPane.showInputDialog("Αναζητήστε το Γεύμα που θέλετε: ");
                meal.searchByName(searchTerm, centerTextfield);
            }
        });


        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popUpListTree obj = popUpListTree.getInstance();
                obj.popUpWindow();
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
       
        SEARCHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "Enter name to search:");
                Database.selectByName(name);
            }
        });

        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                    if (result == JOptionPane.YES_NO_OPTION)
                        System.exit(0);
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
