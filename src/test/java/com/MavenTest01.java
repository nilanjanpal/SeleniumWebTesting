package com;

import org.testng.annotations.Test;
import customLogger.Logger;
import driverManager.Driver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class MavenTest01 {

  WebDriver driver;

  @BeforeMethod
  public void beforeMethod() {
    String methodName = "MavenTest01.beforeMethod";
    Logger.log("MavenTest01", methodName + "Start");
    driver = Driver.getDriver();
    Logger.log("MavenTest01", methodName + "End");
  }

  @AfterMethod
  public void afterMethod() {
    String methodName = "MavenTest01.afterMethod";
    Logger.log("MavenTest01", methodName + "Start");
    Logger.log("MavenTest01", methodName + "End");
  }

  @Test
  public void testCase1() {
    String methodName = "MavenTest01.testCase1";
    Logger.log("MavenTest01", methodName + "Start");
    WebDriverWait wait = new WebDriverWait(driver, 10);
    driver.get("http://www.google.co.in");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Google']")));
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Java");
    element.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@title,'Go to Google Home')]")));
    Logger.log("MavenTest01", methodName + "End");
  }


}
