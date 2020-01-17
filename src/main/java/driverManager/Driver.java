package driverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {
  private static WebDriver driver;
  
  public static void setDriver() {
    driver = new FirefoxDriver();
  }
  
  public static WebDriver getDriver() {
    return driver;
  }
  
  public static void clearDriver() {
    driver.quit();
  }
}
