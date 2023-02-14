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

        Database db = Database.getInstance();
        db.orderBy();

        try {
            // Step 2: Establish a connection
            Connection connection = connect();

            // Step 3: Create a statement
            Statement statement = connection.createStatement();

            // Step 4: Execute the query
            ResultSet resultSet = statement.executeQuery("SELECT Όνομα, Εμφανίσεις FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID");

            // Step 5: Create the PDF document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Statistika_Geymaton.pdf"));
            document.open();
            // Step 6: Create the table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Step 7: Add the header row
            PdfPCell headerCell1 = new PdfPCell(new Phrase("Meal"));
            headerCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell1);

            PdfPCell headerCell2 = new PdfPCell(new Phrase("Views"));
            headerCell2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell2);

            // Step 8: Add the data rows
            while (resultSet.next()) {
                table.addCell(resultSet.getString("Όνομα"));
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(Integer.toString(resultSet.getInt("Εμφανίσεις")));
                table.setHorizontalAlignment(Element.ALIGN_CENTER);
            }

            // Step 9: Add the table to the document
            document.add(table);

            // Step 10: Close the document
            document.close();

            // Step 11: Close the connection
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}