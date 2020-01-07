package customReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

public class ReportGenerator implements IReporter {

  private final String sheetname = "Result Summary";
  private final String[] headerColumns =
      {"Suite Name", "Class Name", "Test Case Name", "Start Time", "End Time", "Result"};
  ExcelGenerator excel;
  XSSFWorkbook workbook;
  XSSFSheet worksheet;

  public void generateReport(String[] headerColumns, List<String[]> passedTestCases,
      List<String[]> failedTestCases, List<String[]> skippedTestCases) {
    /*
     * ExcelGenerator excel = new ExcelGenerator(); XSSFWorkbook workbook = excel.createWorkBook();
     * XSSFSheet worksheet = excel.createSheet(workbook, sheetname);
     * excel.createRowsColumns(workbook, worksheet, headerColumns, passedTestCases, failedTestCases,
     * skippedTestCases); excel.writeToFile(workbook);
     */
  }

  public void generateReport(String[] headerColumns, IResultMap passedTests, IResultMap failedTests,
      IResultMap skippedTests) {

  }

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
    excel.writeToFile(workbook);
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
  }

  private void initializeReport(String outputDirectory) {
    excel = new ExcelGenerator(outputDirectory);
    workbook = excel.createWorkBook();
    worksheet = excel.createSheet(workbook, sheetname);
    excel.setStyles(workbook);
    worksheet = excel.createHeaders(worksheet, headerColumns);
  }

  private String convertMillisecondToDate(long millisecond) {
    DateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    Date result = new Date(millisecond);
    return simpleDate.format(result);
  }
}
