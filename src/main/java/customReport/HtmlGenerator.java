package customReport;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class HtmlGenerator {

  StringBuilder htmlBuilder;
  private static final String fileName = "result-summary.html";
  private String filePath;

  public HtmlGenerator(String filePath) {
    super();
    this.filePath = filePath + "/" + fileName;
  }

  public void initiateHtml() {
    htmlBuilder = new StringBuilder();
    htmlBuilder.append(
        "<html><head><link rel=\"stylesheet\" href=\"./../../src/main/resources/result-summary.css\"><title>Report Summary</title></head>");
    htmlBuilder.append("<body>");
    htmlBuilder.append("<table>");
  }

  public void createHeaders(String[] headerColumns) {
    htmlBuilder.append("<tr>");
    for (String columnName : headerColumns) {
      htmlBuilder.append("<td><b>");
      htmlBuilder.append(columnName);
      htmlBuilder.append("</b></td>");
    }
    htmlBuilder.append("</tr>");
  }

  public void createSuiteRow(String suiteName) {

    String cssClass = "suite";

    htmlBuilder.append("<tr ");
    htmlBuilder.append("class=\"");
    htmlBuilder.append(cssClass);
    htmlBuilder.append("\">");
    htmlBuilder.append("<th colspan=\"7\">");
    htmlBuilder.append("Suite Name - " + suiteName);
    htmlBuilder.append("</th>");
    htmlBuilder.append("</tr>");
  }

  public void createTableData(String suiteName, String testCaseClass, String testcaseName,
      String startDate, String endDate, String testCaseResult, List<String> reporterLogs,
      String exceptionMessage) {

    String cssClass = "";

    switch (testCaseResult) {
      case "PASS":
        cssClass = "pass";
        break;
      case "FAIL":
        cssClass = "fail";
        break;
      case "SKIP":
        cssClass = "skip";
        break;
    }

    htmlBuilder.append("<tr ");
    htmlBuilder.append("class=\"");
    htmlBuilder.append(cssClass);
    htmlBuilder.append("\">");
    htmlBuilder.append("<td>");
    htmlBuilder.append(testCaseClass);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(testcaseName);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(startDate);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(endDate);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(testCaseResult);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    for (String string : reporterLogs) {
      htmlBuilder.append(string);
      htmlBuilder.append("<br>");
    }
    htmlBuilder.append("</td>");
    
    htmlBuilder.append("<td>");
    htmlBuilder.append(exceptionMessage);
    htmlBuilder.append("</td>");
    htmlBuilder.append("</tr>");
  }

  public void closeHtml() {
    htmlBuilder.append("</table></body></html>");
  }

  public void writeToFile() {
    try {
      FileOutputStream outputStream = new FileOutputStream(filePath);
      Writer writer = new OutputStreamWriter(outputStream);
      writer.write(htmlBuilder.toString());
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
