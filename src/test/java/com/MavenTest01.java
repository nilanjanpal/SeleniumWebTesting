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

  private String className = "MavenTest01";
  private WebDriver driver;

  @BeforeMethod
  public void beforeMethod() {
    
    String methodName = "beforeMethod";
    Logger.log("Method Start");
    driver = Driver.getDriver();
    Logger.log("Method End");
  }

  @AfterMethod
  public void afterMethod() {
    String methodName = "afterMethod";
    Logger.log("Method Start");
    Logger.log("Method End");
  }

  @Test
  public void testCase1() {
    String methodName = "testCase1";
    Logger.log("Method Start");
    WebDriverWait wait = new WebDriverWait(driver, 10);
    driver.get("http://www.google.co.in");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title='Search']")));
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Java");
    element.sendKeys(Keys.ENTER);
    //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@title,'Go to Google Home')]")));
    //Driver.waitForLoad(driver);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    Logger.log("Method End");
  }


}
