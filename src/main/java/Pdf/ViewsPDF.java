package Pdf;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.database.Database;

import static org.database.Database.connect;

public class ViewsPDF {

    public void viewPdf() {

        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT VIEWS.Εμφανίσεις, CENTRAL.Όνομα \n" +
                    "FROM VIEWS INNER JOIN CENTRAL ON VIEWS.ID = CENTRAL.ID");

            // Φτιάξε το PDF
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Statistika_Geymaton.pdf"));
            document.open();
            // Φτιάξε τον πίνακα
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Φτιάξε επικεφαλίδα
            PdfPCell headerCell1 = new PdfPCell(new Phrase("Meal"));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell1);
            PdfPCell headerCell2 = new PdfPCell(new Phrase("Views"));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell2);

            // Φτιάξε τις σειρές με τα δεδομένα
            while (resultSet.next()) {
                table.addCell(resultSet.getString("Όνομα"));
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(Integer.toString(resultSet.getInt("Εμφανίσεις")));
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
            }
            //Βάλε τον πίνακα στο PDF
            document.add(table);
            document.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}