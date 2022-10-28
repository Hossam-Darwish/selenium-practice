package file_utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelFileManager {

    private static String filePath;
    private static String sheetName;
    private static File excelFile;
    private static FileInputStream fs;
    private static XSSFWorkbook workbook;  //HSSF for excel version 2010 and lower
    private static XSSFSheet mySheet;

    public ExcelFileManager(String filePath, String sheetName) {
        this.filePath = filePath;
        this.sheetName = sheetName;
        excelFile = new File(filePath);
        try {
            fs = new FileInputStream(excelFile);
            workbook = new XSSFWorkbook(fs);
            mySheet = workbook.getSheet(sheetName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object[][] getExcelData() throws IOException, InvalidFormatException {

        int rowNum = mySheet.getPhysicalNumberOfRows();
        int colsNum = mySheet.getRow(0).getLastCellNum();

        String[][] arrData = new String[rowNum - 1][colsNum];
        for (int i = 1; i < rowNum; i++) {
            var row = mySheet.getRow(i);
            for (int j = 0; j < colsNum; j++) {
                arrData[i - 1][j] = row.getCell(j).toString();
            }
        }

        workbook.close();
        return arrData;

    }

    public String getCellData(int row, int col) {

        String cellValue = mySheet.getRow(row).getCell(col).toString();
        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cellValue;

    }
}
