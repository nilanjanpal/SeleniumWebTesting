package com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import customLogger.Logger;
import driverManager.Driver;

public class MavenTest02 {

  WebDriver driver = null;

  @BeforeMethod
  public void setup() {
    String methodName = "MavenTest02.setup";
    Logger.log("MavenTest02", methodName + "Start");
    driver = Driver.getDriver();
    Logger.log("MavenTest02", methodName + "End");
  }

  @Test
  public void test01() {
    String methodName = "MavenTest02.test01";
    Logger.log("MavenTest02", methodName + "Start");
    WebDriverWait wait = new WebDriverWait(driver, 10);
    driver.get("http://www.google.co.in");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Google']")));
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Selenium");
    element.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@title,'Go to Google Home')]")));
    Logger.log("MavenTest02", methodName + "End");
  }

  @AfterMethod
  public void quitBrowser() {
    String methodName = "MavenTest02.quitBrowser";
    Logger.log("MavenTest02", methodName + "Start");
    Logger.log("MavenTest02", methodName + "End");
  }

}

