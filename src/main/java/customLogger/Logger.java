package customLogger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import driverManager.Driver;
import ioutility.FileUtility;

public class Logger {


  private static List<String[]> m_output = new Vector<String[]>();

  public static List<String> getOutput(String className) {
    List<String> outputLogs = new ArrayList<String>();
    for (String[] output : m_output) {
      if (output[0].equals(className)) {
        outputLogs.add(output[1]);
      }
    }
    return outputLogs;
  }

  public static synchronized void log(String className, String stringLog) {
    String pattern = "yyyy_MM_dd_HH_mm_ss_SSS";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = simpleDateFormat.format(new Date());
    String screenshotPath = "";
    WebDriver driver = Driver.getDriver();

    if (!stringLog.isEmpty()) {
      String[] logValues = {className, stringLog, className + date + ".png"};
      m_output.add(logValues);
    }
    FileUtility.createDirectory(className);
    screenshotPath = FileUtility.getScreenshotPath();
    saveScreenshot(driver, screenshotPath + "/" + className + "/" + (className + date + ".png"));
  }

  private static void saveScreenshot(WebDriver driver, String fileName) {
    TakesScreenshot scrShot = ((TakesScreenshot) driver);
    File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
    File DestFile = new File(fileName);
    try {
      FileUtils.copyFile(SrcFile, DestFile);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
