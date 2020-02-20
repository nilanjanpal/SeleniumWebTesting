package com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import customLogger.Logger;
import driverManager.Driver;
import utils.Result;

public class MavenTest03 {

  private String className = "MavenTest03";
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
    WebDriverWait wait;
    try {
      wait = new WebDriverWait(driver, 10);
      driver.get("http://www.google.co.in");
      wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title='Search']")));
      WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
      element.sendKeys("Maven");
      element.sendKeys(Keys.ENTER);
      Thread.sleep(5000);
      Logger.log("Method End");
      Assert.assertEquals(true, false);
      Logger.logResult(methodName, Result.PASS);
    } catch (AssertionError aer) {
      Logger.logResult(methodName, Result.FAIL);
      aer.printStackTrace();
      throw aer;
    } catch (Exception e) {
      Logger.logResult(methodName, Result.FAIL);
      e.printStackTrace();
    }
  }

}
