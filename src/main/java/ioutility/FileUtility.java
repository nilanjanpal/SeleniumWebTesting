package ioutility;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FileUtility {

  private static final String screenshotPath = "screenshots";
  
  public static String getScreenshotPath() {
    return screenshotPath;
  }
  
  public static File createDirectory(String className) {
    File file = null;
    if (!checkDirectory(className)) {
      file = new File(screenshotPath + "/" + className);
      file.mkdirs();
    }
    return file;
  }

  private static boolean checkDirectory(String className) {
    boolean isExists = false;
    File file = new File(screenshotPath + "/" + className);
    if (file.exists()) {
      isExists = true;
    }
    return isExists;
  }
  
  public static void cleanDirectory(String path) {
    File directory = new File(path);
    try {
      FileUtils.cleanDirectory(directory);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
