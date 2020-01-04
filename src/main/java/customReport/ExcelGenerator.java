package customReport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGenerator {

  private static final String filePath = "SpreadSheetReports/ResultSummary.xlsx";

  public XSSFWorkbook createWorkBook() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    return workbook;
  }

  public XSSFSheet createSheet(XSSFWorkbook workbook, String sheetname) {
    XSSFSheet worksheet = workbook.createSheet(sheetname);
    return worksheet;
  }

  public void createRowsColumns(XSSFWorkbook workbook, XSSFSheet worksheet,String[] headerColumns, List<String[]> passedTestCases,
      List<String[]> failedTestCases, List<String[]> skippedTestCases) {

    int rownum = 0;
    String classname = "";
    Row row;
    Cell cell;
    Font font;
    CellStyle mergeCellStyle = workbook.createCellStyle();
    CellStyle headerCellStyle = workbook.createCellStyle();
    CellStyle passCellStyle = workbook.createCellStyle();
    CellStyle failCellStyle = workbook.createCellStyle();
    CellStyle skipCellStyle = workbook.createCellStyle();
    
    mergeCellStyle.setAlignment(HorizontalAlignment.CENTER);
    mergeCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
    mergeCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    font = workbook.createFont();
    font.setBold(true);
    mergeCellStyle.setFont(font);
    
    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
    headerCellStyle.setFont(font);
    
    passCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
    passCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    failCellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
    failCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    skipCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
    skipCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    
    row = worksheet.createRow(rownum++);
    cell = row.createCell(0);
    cell.setCellValue(headerColumns[0]);
    cell.setCellStyle(headerCellStyle);
    cell = row.createCell(1);
    cell.setCellValue(headerColumns[1]);
    cell.setCellStyle(headerCellStyle);
    cell = row.createCell(2);
    cell.setCellValue(headerColumns[2]);
    cell.setCellStyle(headerCellStyle);
    cell = row.createCell(3);
    cell.setCellValue(headerColumns[3]);
    cell.setCellStyle(headerCellStyle);

    for (String[] testCases : skippedTestCases) {
      row = worksheet.createRow(rownum++);
      cell = row.createCell(0);
      cell.setCellValue(testCases[0]);
      cell = row.createCell(1);
      cell.setBlank();
      cell = row.createCell(2);
      cell.setBlank();
      cell = row.createCell(3);
      cell.setCellValue(testCases[1]);
      cell.setCellStyle(skipCellStyle);
    }
    
    for (String[] testCases : passedTestCases) {
      if(!classname.equalsIgnoreCase(testCases[4])) {
        row = worksheet.createRow(rownum++);
        cell = row.createCell(0);
        cell.setCellValue(testCases[4]);
        classname=testCases[4];
        cell.setCellStyle(mergeCellStyle);
        worksheet.addMergedRegion(new CellRangeAddress(rownum-1,rownum-1,0,3));
      }
      row = worksheet.createRow(rownum++);
      cell = row.createCell(0);
      cell.setCellValue(testCases[0]);
      cell = row.createCell(1);
      cell.setCellValue(testCases[1]);
      cell = row.createCell(2);
      cell.setCellValue(testCases[2]);
      cell = row.createCell(3);
      cell.setCellValue(testCases[3]);
      cell.setCellStyle(passCellStyle);
    }
    
    for (String[] testCases : failedTestCases) {
      row = worksheet.createRow(rownum++);
      cell = row.createCell(0);
      cell.setCellValue(testCases[0]);
      cell = row.createCell(1);
      cell.setCellValue(testCases[1]);
      cell = row.createCell(2);
      cell.setCellValue(testCases[2]);
      cell = row.createCell(3);
      cell.setCellValue(testCases[3]);
      cell.setCellStyle(failCellStyle);
    }
    
  }

  public void writeToFile(XSSFWorkbook workbook) {
    try {
      FileOutputStream outputStream = new FileOutputStream(filePath);
      workbook.write(outputStream);
      workbook.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
