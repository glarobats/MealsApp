package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OKButtonListener implements ActionListener {
    private JFrame frame;

    public OKButtonListener(JFrame frame) {
        this.frame = frame;
    }
    @Override
    public void actionPerformed(ActionEvent e){
            frame.dispose();
    }
}
