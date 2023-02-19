package GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoriesButton {
    public void addCategoriesButtonListener(JLabel categoriesTitle) {
        categoriesTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
