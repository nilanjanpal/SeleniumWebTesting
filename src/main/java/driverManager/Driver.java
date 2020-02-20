package driverManager;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

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

  public static void waitForLoad(WebDriver driver) {
    new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
        .executeScript("return document.readyState").equals("complete"));
  }
}
