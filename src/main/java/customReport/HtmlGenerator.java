package customReport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

public class HtmlGenerator {

  private StringBuilder htmlBuilder;
  private StringBuilder htmlHeader;
  private String filePath;

  public HtmlGenerator(String filePath, String fileName) {
    super();
    this.filePath = filePath + File.separator + fileName;
  }

  public void initiateHtml(boolean isSummaryReport) {
    htmlBuilder = new StringBuilder();
    htmlHeader = new StringBuilder();
    htmlBuilder.append("<body>");
    if (isSummaryReport) {
      /*
       * htmlBuilder.append("<div class=\"split left\">"); htmlBuilder.append("<div></div>");
       * htmlBuilder.append("</div>"); htmlBuilder.append("<div class=\"split right\">");
       */
      htmlBuilder.append("<div id=\"piechart\"></div>");
//      htmlBuilder.append("</div>");
    }
    htmlBuilder.append("<div><table class=\"table\">");
  }

  private void createGraph(int passCount, int failCount, int skipCount) {
    String header =
        "<html><head><script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>"
            + "    <script type=\"text/javascript\">"
            + "      google.charts.load('current', {'packages':['corechart']});"
            + "      google.charts.setOnLoadCallback(drawChart);" + "      function drawChart() {"
            + "        var data = google.visualization.arrayToDataTable(["
            + "          ['Category', 'Count']," + "          ['Pass',     " + passCount + "],"
            + "          ['Fail',      " + failCount + "]," + "          ['Skip',  " + skipCount
            + "]" + "        ]);" + "        var options = {" + "          title: 'Test Result'"
            + "        };"
            + "        var chart = new google.visualization.PieChart(document.getElementById('piechart'));"
            + "        chart.draw(data, options);" + "      }"
            + "    </script><link rel=\"stylesheet\" href=\"./../../src/main/resources/result-summary.css\"><title>Report Summary</title></head>";
    htmlHeader.append(header);
  }

  public void createHeaders(String[] headerColumns) {
    htmlBuilder.append("<tr>");
    for (String columnName : headerColumns) {
      htmlBuilder.append("<td><b>");
      htmlBuilder.append(columnName);
      htmlBuilder.append("</b></td>");
    }
    htmlBuilder.append("</tr><tbody>");
  }

  public void createOneColumnRow(String columnString, String columnValue, String span) {

    String cssClass = "suite";

    htmlBuilder.append("<tr ");
    htmlBuilder.append("class=\"");
    htmlBuilder.append(cssClass);
    htmlBuilder.append("\">");
    htmlBuilder.append("<th colspan=\"" + span + "\">");
    htmlBuilder.append(columnString + " - " + columnValue);
    htmlBuilder.append("</th>");
    htmlBuilder.append("</tr>");
  }

  public void createTableData(String suiteName, String testCaseClass,String classFullName, String testcaseName,
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
    htmlBuilder.append("<a href=\"" + classFullName + ".html\">" + testCaseClass + "</a>");
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
    if(exceptionMessage == "NONE") {
      htmlBuilder.append("<td>");
      htmlBuilder.append(exceptionMessage);
      htmlBuilder.append("</td>");
    }
    else {
      htmlBuilder.append("<td>");
      htmlBuilder.append(exceptionMessage);
      htmlBuilder.append("<p><a href=\"\">Details ...</a></p>");
      htmlBuilder.append("</td>");
    }
    htmlBuilder.append("</tr>");
  }

  public void createDetailReportData(String methodName, String log, String Screenshot) {
    htmlBuilder.append("<tr>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(methodName);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append(log);
    htmlBuilder.append("</td>");
    htmlBuilder.append("<td>");
    htmlBuilder.append("<a href=\"" + Screenshot + "\">");
    htmlBuilder.append("<img src =\"" + Screenshot + "\" height=\"200\" width=\"200\">");
    htmlBuilder.append("</a>");
    htmlBuilder.append("</td>");
    htmlBuilder.append("</tr>");
  }

  public void closeHtml(int passCount, int failCount, int skipCount) {
    htmlBuilder.append("</table></div></body></html>");
    createGraph(passCount, failCount, skipCount);
    htmlHeader.append(htmlBuilder);
  }

  public void closeHtml() {
    String header =
        "<html><head><link rel=\"stylesheet\" href=\"./../../src/main/resources/result-summary.css\"><title>Report Summary</title></head>";
    htmlHeader.append(header);
    htmlHeader.append(htmlBuilder);
  }

  public void writeToFile() {
    try {
      FileOutputStream outputStream = new FileOutputStream(filePath);
      Writer writer = new OutputStreamWriter(outputStream);
      writer.write(htmlHeader.toString());
      writer.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
