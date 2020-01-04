package customReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ResultCollector implements ITestListener {

  String[] headerColumns = {"Test Case Name", "Start Time", "End Time", "Result"};
  List<String[]> testCasePassed = new ArrayList<String[]>();
  List<String[]> testCaseFailed = new ArrayList<String[]>();
  List<String[]> testCaseSkipped = new ArrayList<String[]>();

  public void onTestStart(ITestResult result) {
    // TODO Auto-generated method stub

  }
  
  public String convertMillisecondToDate(long millisecond) {
    DateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
    Date result = new Date(millisecond);
    return simpleDate.format(result);
  }

  public void onTestSuccess(ITestResult result) {
    String[] passedTestCaseValues = new String[5];
    passedTestCaseValues[0] = result.getName();
    passedTestCaseValues[1] = convertMillisecondToDate(result.getStartMillis());
    passedTestCaseValues[2] = convertMillisecondToDate(result.getEndMillis());
    passedTestCaseValues[3] = "Pass";
    passedTestCaseValues[4] = result.getTestClass().getRealClass().toString();
    testCasePassed.add(passedTestCaseValues);
  }

  public void onTestFailure(ITestResult result) {
    String[] failedTestCaseValues = new String[5];
    failedTestCaseValues[0] = result.getName();
    failedTestCaseValues[1] = convertMillisecondToDate(result.getStartMillis());
    failedTestCaseValues[2] = convertMillisecondToDate(result.getEndMillis());
    failedTestCaseValues[3] = "Fail";
    failedTestCaseValues[4] = result.getTestClass().getRealClass().toString();
    testCaseFailed.add(failedTestCaseValues);
  }

  public void onTestSkipped(ITestResult result) {
    String[] skippedTestCaseValues = new String[3];
    skippedTestCaseValues[0] = result.getName();
    skippedTestCaseValues[1] = "Skip";
    skippedTestCaseValues[2] = result.getTestClass().getRealClass().toString();
    testCaseSkipped.add(skippedTestCaseValues);
  }

  public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    // TODO Auto-generated method stub

  }

  public void onStart(ITestContext context) {
    // TODO Auto-generated method stub

  }

  public void onFinish(ITestContext context) {
    ReportGenerator report = new ReportGenerator();
    report.generateReport(headerColumns, testCasePassed, testCaseFailed, testCaseSkipped);
  }

}
