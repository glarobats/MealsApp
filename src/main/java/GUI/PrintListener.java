package GUI;

import Pdf.ViewsPDF;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrintListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        ViewsPDF statistika = new ViewsPDF();
        statistika.viewPdf();
    }
}