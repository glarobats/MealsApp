package GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoriesButton extends JPanel{
    public void addCategoriesButtonListener(JLabel categoriesTitle) {
        categoriesTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CategoriesTreeCall call = null;
                try {
                    call = CategoriesTreeCall.getInstance();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}

