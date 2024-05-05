package Service;
import Entities.Evaluation;
import Service.EvaluationCrud;
import javafx.scene.control.ListView;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.stage.FileChooser;
import java.util.List;
public class ExcelExporter {
    public void generateExcel(List<Evaluation> evaluations) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Id User");
            headerRow.createCell(1).setCellValue("Id Quiz");
            headerRow.createCell(2).setCellValue("Result");
            headerRow.createCell(3).setCellValue("Date");

            // Ajoutez les en-têtes des autres colonnes ici

            for (int i = 0; i < evaluations.size(); i++) {
                Row dataRow = sheet.createRow(i + 1); // +1 pour éviter d'écraser l'en-tête
                Evaluation campaign = evaluations.get(i);

                dataRow.createCell(0).setCellValue(campaign.getId_user_id());
                dataRow.createCell(1).setCellValue(campaign.getId_quiz_id());
                dataRow.createCell(2).setCellValue( campaign.getResultat());
                LocalDate localDate = campaign.getDate();
                // Convertir LocalDate en java.util.Date
                java.util.Date utilDate = java.sql.Date.valueOf(localDate);

// Convertir java.util.Date en java.sql.Date
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

// Formatter de date pour formater la date en format LocalDate
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

// Formatter de date pour convertir java.sql.Date en String
                String formattedDate = dateFormat.format(sqlDate);

// Utiliser la date formatée dans la méthode setCellValue
                dataRow.createCell(3).setCellValue(formattedDate);




            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                workbook.close();
                System.out.println("Excel generated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}