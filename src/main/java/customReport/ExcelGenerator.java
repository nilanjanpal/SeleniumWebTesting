package customReport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

  private static final String fileName = "result-summary.xlsx";
  private String filePath;
  private CellStyle mergeCellStyle;
  private CellStyle headerCellStyle;
  private CellStyle passCellStyle;
  private CellStyle failCellStyle;
  private CellStyle skipCellStyle;
  private CellStyle suiteCellStyle;
  private int rownum = 0;

  public ExcelGenerator(String filePath) {
    super();
    this.filePath = filePath + "/" + fileName;
  }

  public XSSFWorkbook createWorkBook() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    return workbook;
  }

  public XSSFSheet createSheet(XSSFWorkbook workbook, String sheetname) {
    XSSFSheet worksheet = workbook.createSheet(sheetname);
    return worksheet;
  }

  public void setStyles(XSSFWorkbook workbook) {

    Font font;

    mergeCellStyle = workbook.createCellStyle();
    headerCellStyle = workbook.createCellStyle();
    passCellStyle = workbook.createCellStyle();
    failCellStyle = workbook.createCellStyle();
    skipCellStyle = workbook.createCellStyle();
    suiteCellStyle = workbook.createCellStyle();

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
    suiteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    suiteCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    suiteCellStyle.setAlignment(HorizontalAlignment.CENTER);
    suiteCellStyle.setFont(font);
  }
  
  public XSSFSheet createSuiteRow(XSSFSheet worksheet, String suiteName) {
    
    Row row;
    Cell cell;
    
    row = worksheet.createRow(rownum);
    cell = row.createCell(0);
    cell.setCellValue("Suite Name -"+suiteName);
    cell.setCellStyle(suiteCellStyle);
    worksheet.addMergedRegion(new CellRangeAddress(rownum, rownum, 0, 4));
    rownum++;
    return worksheet;
  }

  public XSSFSheet createRowsColumns(XSSFSheet worksheet, String suiteName, String className, String testCaseName,
      String startDate, String endDate, String testCaseResult) {

    Row row;
    Cell cell;
    CellStyle style = passCellStyle ;
    
    switch(testCaseResult) {
      case "PASS":
        style = passCellStyle;
        break;
      case "FAIL":
        style = failCellStyle;
        break;
      case "SKIP":
        style = skipCellStyle;
        break;
    }

    row = worksheet.createRow(rownum++);
    cell = row.createCell(0);
    cell.setCellValue(className);
    cell.setCellStyle(style);
    cell = row.createCell(1);
    cell.setCellValue(testCaseName);
    cell.setCellStyle(style);
    cell = row.createCell(2);
    cell.setCellValue(startDate);
    cell.setCellStyle(style);
    cell = row.createCell(3);
    cell.setCellValue(endDate);
    cell.setCellStyle(style);
    cell = row.createCell(4);
    cell.setCellValue(testCaseResult);
    cell.setCellStyle(style);

    return worksheet;
  }

  public XSSFSheet createHeaders(XSSFSheet worksheet, String[] headerColumns) {

    Row row;
    Cell cell;
    int cellnum = 0;

    row = worksheet.createRow(rownum++);
    for (String columnName : headerColumns) {
      cell = row.createCell(cellnum++);
      cell.setCellValue(columnName);
      cell.setCellStyle(headerCellStyle);
    }

    return worksheet;
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
