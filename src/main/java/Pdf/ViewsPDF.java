package Pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.database.Database.connect;

public class ViewsPDF {

    public void viewPdf () {
        try {
            Connection connection = connect();
            Statement statement = connection.createStatement();
            String qr = "SELECT CENTRAL.Όνομα, VIEWS.Εμφανίσεις FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID ORDER BY VIEWS.Εμφανίσεις DESC";
            ResultSet resultSet = statement.executeQuery(qr);

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("views.pdf"));
            document.open();
            PdfPTable table = new PdfPTable(2);
            PdfPCell onoma = new PdfPCell(new Phrase("Onoma", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            onoma.setHorizontalAlignment(Element.ALIGN_CENTER);
            onoma.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(onoma);
            PdfPCell emfaniseis = new PdfPCell(new Phrase("Emfaniseis", new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD)));
            emfaniseis.setHorizontalAlignment(Element.ALIGN_CENTER);
            emfaniseis.setBackgroundColor(BaseColor.YELLOW);
            table.addCell(emfaniseis);
            while (resultSet.next()) {
                table.addCell(resultSet.getString("Όνομα"));
                table.addCell(String.valueOf(resultSet.getInt("Εμφανίσεις")));
            }
            document.add(table);

            /*DefaultPieDataset dataset = new DefaultPieDataset();
            try {
                Connection connection1 = connect();
                Statement statement1 = connection.createStatement();
                String qur = ("SELECT Εμφανίσεις, Όνομα FROM CENTRAL INNER JOIN VIEWS ON CENTRAL.ID = VIEWS.ID");
                ResultSet resultSet1 = statement.executeQuery(qur);
                while (resultSet1.next()) {
                    dataset.setValue(resultSet1.getString("Όνομα"), resultSet.getInt("Εμφανίσεις"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            JFreeChart chart = ChartFactory.createPieChart("Εμφανίσεις Γευμάτων",dataset,true,true, false);
            int w = 500;
            int h = 400;
            BufferedImage image = chart.createBufferedImage(w,h);
            ImageIO.write(image,"PNG",new File("chart.png"));
            Image chartIm = Image.getInstance("chart.png");
            chartIm.scaleToFit(w,h);
            document.add(chartIm);*/
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
