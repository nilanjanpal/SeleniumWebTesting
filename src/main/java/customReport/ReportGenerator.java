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
import customLogger.Logger;

public class ReportGenerator implements IReporter {

  private final String sheetname = "Result Summary";
  private final String[] headerColumns = {"Class Name", "Test Case Name", "Start Time", "End Time",
      "Result", "Reporter Logs", "Exception Detail"};
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
      createHeaders(suiteName, headerColumns, worksheet);
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

  private void createHeaders(String suiteName, String[] headerColumns, XSSFSheet worksheet) {
    worksheet = excel.createSuiteRow(worksheet, suiteName);
    worksheet = excel.createHeaders(worksheet, headerColumns);
    html.createSuiteRow(suiteName);
    html.createHeaders(headerColumns);
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
    String exceptionMessage = "";
    List<String> reporterLogs;
    Throwable exception;

    testCaseClass = testResult.getMethod().getRealClass().getSimpleName();
    reporterLogs = Logger.getOutput(testCaseClass);
    testcaseName = testResult.getMethod().getMethodName();
    startDate = convertMillisecondToDate(testResult.getStartMillis());
    endDate = convertMillisecondToDate(testResult.getEndMillis());

    exception = testResult.getThrowable();
    if (exception != null) {
      exceptionMessage = exception.getMessage();
    } else {
      exceptionMessage = "NONE";
    }

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
        testCaseResult, reporterLogs, exceptionMessage);
    html.createTableData(suiteName, testCaseClass, testcaseName, startDate, endDate, testCaseResult,
        reporterLogs, exceptionMessage);
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
  }

  private void initializeHtmlReport(String outputDirectory) {
    html = new HtmlGenerator(outputDirectory);
    html.initiateHtml();
  }
}
