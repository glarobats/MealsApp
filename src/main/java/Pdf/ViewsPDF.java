package Pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import com.itextpdf.text.Element;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import static org.database.Database.connect;

public class ViewsPDF {

    public void viewPdf() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Αποθήκευση PDF");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF Files", "pdf"));
        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileSave = fileChooser.getSelectedFile();
            try {
                Connection connection = connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT VIEWS.Εμφανίσεις, CENTRAL.Όνομα \n" +
                        "FROM VIEWS INNER JOIN CENTRAL ON VIEWS.ID = CENTRAL.ID ORDER BY VIEWS.Εμφανίσεις DESC  ");

                // Φτιάξε το PDF
                Document document = new Document();
                if (!fileSave.getName().endsWith(".pdf")) {
                    fileSave = new File(fileSave.getAbsolutePath() + ".pdf");
                }
                PdfWriter.getInstance(document, new FileOutputStream(fileSave));
                document.open();

                //Επικεφαλίδα στο PDF
                PdfPTable heading = new PdfPTable(1);
                PdfPCell headingCell = new PdfPCell(new Phrase ("Meal Appearances",new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK)));
                headingCell.setBorder(Rectangle.NO_BORDER);
                headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                heading.addCell(headingCell);

                // Φτιάξε τον πίνακα
                PdfPTable table = new PdfPTable(2);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);

                // Φτιάξε επικεφαλίδα
                PdfPCell headerCell1 = new PdfPCell(new Phrase("Meal",new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
                headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell1.setBackgroundColor(new BaseColor(159, 100, 69));
                table.addCell(headerCell1);

                PdfPCell headerCell2 = new PdfPCell(new Phrase("Views",new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE)));
                headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                headerCell2.setBackgroundColor(new BaseColor(159, 100, 69));
                table.addCell(headerCell2);

                while (resultSet.next()) {
                    PdfPCell cell1 = new PdfPCell(new Phrase(resultSet.getString("Όνομα"), new Font(Font.FontFamily.HELVETICA, 10)));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell1);

                    PdfPCell cell2 = new PdfPCell(new Phrase(Integer.toString(resultSet.getInt("Εμφανίσεις")), new Font(Font.FontFamily.HELVETICA, 10)));
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell2);
                }

                //Βάλε τον πίνακα στο PDF
                document.add(heading);
                document.add(table);
                document.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}