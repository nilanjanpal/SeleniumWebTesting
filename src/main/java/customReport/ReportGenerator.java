package customReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class ReportGenerator implements IReporter {

  private final String sheetname = "Result Summary";
  private final String[] headerColumns =
      {"Suite Name", "Class Name", "Test Case Name", "Start Time", "End Time", "Result"};
  ExcelGenerator excel;
  HtmlGenerator html;
  XSSFWorkbook workbook;
  XSSFSheet worksheet;

  @Override
  public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
      String outputDirectory) {

    Boolean isCustomReportsCreated = false;
    String suiteName = "";
    Set<ITestResult> results;

    for (ISuite iSuite : suites) {
      if (!isCustomReportsCreated) {
        initializeReport(outputDirectory);
        isCustomReportsCreated = true;
      }
      suiteName = iSuite.getName();
      Map<String, ISuiteResult> suiteResults = iSuite.getResults();
      for (ISuiteResult result : suiteResults.values()) {
        ITestContext context = result.getTestContext();
        results = context.getPassedTests().getAllResults();
        for (ITestResult testResult : results) {
          processResult(suiteName, testResult);
        }
        results = context.getFailedTests().getAllResults();
        for (ITestResult testResult : results) {
          processResult(suiteName, testResult);
        }
        results = context.getSkippedTests().getAllResults();
        for (ITestResult testResult : results) {
          processResult(suiteName, testResult);
        }
      }
    }
    writeToFile(workbook);
  }

  private void writeToFile(XSSFWorkbook workbook) {
    excel.writeToFile(workbook);
    html.closeHtml();
    html.writeToFile();
  }

  private void processResult(String suiteName, ITestResult testResult) {

    String testCaseClass = "";
    String testCaseResult = "";
    String testcaseName = "";
    String startDate = "";
    String endDate = "";

    testCaseClass = testResult.getMethod().getRealClass().toString();
    testcaseName = testResult.getMethod().getMethodName();
    startDate = convertMillisecondToDate(testResult.getStartMillis());
    endDate = convertMillisecondToDate(testResult.getEndMillis());
    switch (testResult.getStatus()) {
      case 1:
        testCaseResult = "PASS";
        break;
      case 2:
        testCaseResult = "FAIL";
        break;
      case 3:
        testCaseResult = "SKIP";
        break;
      default:
        testCaseResult = "IN PROGRESS";
    }
    excel.createRowsColumns(worksheet, suiteName, testCaseClass, testcaseName, startDate, endDate,
        testCaseResult);
    html.createTableData(suiteName, testCaseClass, testcaseName, startDate, endDate, testCaseResult);
  }

  private void initializeReport(String outputDirectory) {
    initializeExcelReport(outputDirectory);
    initializeHtmlReport(outputDirectory);
  }

  private String convertMillisecondToDate(long millisecond) {
    DateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    Date result = new Date(millisecond);
    return simpleDate.format(result);
  }

  private void initializeExcelReport(String outputDirectory) {
    excel = new ExcelGenerator(outputDirectory);
    workbook = excel.createWorkBook();
    worksheet = excel.createSheet(workbook, sheetname);
    excel.setStyles(workbook);
    worksheet = excel.createHeaders(worksheet, headerColumns);
  }
  
  private void initializeHtmlReport(String outputDirectory) {
    html = new HtmlGenerator(outputDirectory);
    html.initiateHtml();
    html.createHeaders(headerColumns);
  }
}
