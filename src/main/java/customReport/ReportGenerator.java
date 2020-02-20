package customReport;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import utils.FileUtility;

public class ReportGenerator implements IReporter {

  private final String summaryfileName = "result-summary.html";
  private final String sheetname = "Result Summary";
  private final String[] headerColumns =
      {"Class Name", "Test Case Name", "Start Time", "End Time", "Result", "Exception Detail"};
  private final String[] detailHeaderColumns = {"Method Name", "Log", "Screenshot"};
  private int passCount, skipCount, failCount;
  ExcelGenerator excel;
  HtmlGenerator summaryReport;
  HtmlGenerator detailReport;
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
      createHeaders(suiteName, worksheet, false);
      Map<String, ISuiteResult> suiteResults = iSuite.getResults();
      for (ISuiteResult result : suiteResults.values()) {
        ITestContext context = result.getTestContext();
        results = context.getPassedTests().getAllResults();
        passCount = results.size();
        for (ITestResult testResult : results) {
          createDetailedReport(outputDirectory, testResult.getMethod().getRealClass().getName());
          processResult(suiteName, testResult);
        }
        results = context.getFailedTests().getAllResults();
        failCount = results.size();
        for (ITestResult testResult : results) {
          createDetailedReport(outputDirectory, testResult.getMethod().getRealClass().getName());
          processResult(suiteName, testResult);
        }
        results = context.getSkippedTests().getAllResults();
        skipCount = results.size();
        for (ITestResult testResult : results) {
          createDetailedReport(outputDirectory, testResult.getMethod().getRealClass().getName());
          processResult(suiteName, testResult);
        }
      }
    }
    writeToFile(workbook);
  }

  private void createHeaders(String suiteName, XSSFSheet worksheet, Boolean isDetailedReport) {
    worksheet = excel.createSuiteRow(worksheet, suiteName);
    worksheet = excel.createHeaders(worksheet, headerColumns);
    createHtmlHeaders(suiteName, isDetailedReport);
  }

  private void createHtmlHeaders(String oneColumnValue, Boolean isDetailedReport) {
    if (isDetailedReport) {
      detailReport.createOneColumnRow("Class Name", oneColumnValue, "3");
      detailReport.createHeaders(detailHeaderColumns);
    } else {
      summaryReport.createOneColumnRow("Suite Name", oneColumnValue, "6");
      summaryReport.createHeaders(headerColumns);
    }
  }

  private void writeToFile(XSSFWorkbook workbook) {
    excel.writeToFile(workbook);
    summaryReport.closeHtml(passCount, failCount, skipCount);
    summaryReport.writeToFile();
  }

  private void processResult(String suiteName, ITestResult testResult) {

    String testCaseClass = "";
    String classFullName = "";
    String testCaseResult = "";
    String testcaseName = "";
    String startDate = "";
    String endDate = "";
    String exceptionMessage = "";
    List<String> reporterLogs;
    Throwable exception;

    testCaseClass = testResult.getMethod().getRealClass().getSimpleName();
    classFullName = testResult.getMethod().getRealClass().getName();
    reporterLogs = Logger.getOutput(testCaseClass);
    testcaseName = testResult.getMethod().getMethodName();
    startDate = convertMillisecondToDate(testResult.getStartMillis());
    endDate = convertMillisecondToDate(testResult.getEndMillis());
    exception = testResult.getThrowable();
    if (exception != null) {
      /*
       * StackTraceElement[] elements = exception.getStackTrace(); for (StackTraceElement element :
       * elements) { exceptionMessage = exceptionMessage + element.toString(); exceptionMessage =
       * exceptionMessage + System.getProperty("line.separator"); }
       */
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
    summaryReport.createTableData(suiteName, testCaseClass, classFullName, testcaseName, startDate,
        endDate, testCaseResult, reporterLogs, exceptionMessage);
  }

  private void createDetailedReport(String outputDirectory, String testCaseClass) {
    initializeHtmlDetailReport(outputDirectory, testCaseClass + ".html");
    createHtmlHeaders(testCaseClass, true);
    List<String[]> detailOutput = Logger.getDetailOutput(testCaseClass);
    for (String[] strings : detailOutput) {
      String path = "../../" + FileUtility.getScreenshotPath() + File.separator + testCaseClass
          + File.separator + strings[2];
      detailReport.createDetailReportData(strings[0], strings[1], path);
    }
    detailReport.closeHtml();
    detailReport.writeToFile();
  }

  private void initializeHtmlDetailReport(String outputDirectory, String detailFileName) {
    detailReport = new HtmlGenerator(outputDirectory, detailFileName);
    detailReport.initiateHtml(false);
  }

  private void initializeReport(String outputDirectory) {
    initializeExcelReport(outputDirectory);
    initializeHtmlSummaryReport(outputDirectory);
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

  private void initializeHtmlSummaryReport(String outputDirectory) {
    summaryReport = new HtmlGenerator(outputDirectory, summaryfileName);
    summaryReport.initiateHtml(true);
  }



}
