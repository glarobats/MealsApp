package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriesButton {
    public void addButton2ActionListener(JButton button2) {
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
    }
}
