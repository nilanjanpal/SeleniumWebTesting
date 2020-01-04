package customReport;

import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {
  
  String sheetname = "Result Summary";
  
  public void generateReport(String[] headerColumns, List<String[]> passedTestCases, List<String[]> failedTestCases,
      List<String[]> skippedTestCases) {
    ExcelGenerator excel = new ExcelGenerator();
    XSSFWorkbook workbook = excel.createWorkBook();
    XSSFSheet worksheet = excel.createSheet(workbook, sheetname);
    excel.createRowsColumns(workbook, worksheet, headerColumns, passedTestCases, failedTestCases, skippedTestCases);
    excel.writeToFile(workbook);
  }
}
