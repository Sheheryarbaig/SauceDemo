package Setup.Initialization;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFileManager {

    protected static FileInputStream fileToRead;
    protected static XSSFWorkbook book;
    protected static XSSFSheet excelSheet;
    protected static Row excelRow;

    public static void initializeExcelFile(String filename,String Sheet) throws IOException {
        fileToRead =new FileInputStream("src/test/resources/ExcelFiles/" + filename + ".xlsx");
        book= new XSSFWorkbook(fileToRead);
        excelSheet= book.getSheet(Sheet);
    }

    public static void closeExcel(String filename) throws IOException {
        fileToRead.close();
        FileOutputStream output_file =new FileOutputStream(new File(System.getProperty("user.dir")+"/src/test/resources/ExcelFiles/" + filename + ".xlsx"));
        book.write(output_file);
        output_file.close();
    }

    public static String readFromCell(String fileToRead,String sheetName,int column,String rowToMatch) throws IOException {
        initializeExcelFile(fileToRead,sheetName);
        int lastRow =excelSheet.getLastRowNum();
        for (int i = 1; i <= lastRow; i++) {
            if (excelSheet.getRow(i).getCell(0) == null) {
            } else if (excelSheet.getRow(i).getCell(0).toString().equals(rowToMatch)) {
                excelRow = excelSheet.getRow(i);
            }
        }
        return excelRow.getCell(column).getStringCellValue();
    }

    public static void writeInCell(String fileToWrite,String sheetName,String rowToMatch,int column,String message) throws IOException {
        initializeExcelFile(fileToWrite, sheetName);
        int lastRow = excelSheet.getLastRowNum();
        for (int i = 1; i <= lastRow; i++) {
            if (excelSheet.getRow(i).getCell(0) == null) {
            } else if (excelSheet.getRow(i).getCell(0).toString().equals(rowToMatch)) {
                excelRow = excelSheet.getRow(i);
            }
        }
        excelRow.createCell(column).setCellValue(message);
        closeExcel(fileToWrite);
    }
}
