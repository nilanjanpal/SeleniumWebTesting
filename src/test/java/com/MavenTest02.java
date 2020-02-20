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
  
  private String className = "MavenTest02";
  private WebDriver driver = null;

  @BeforeMethod
  public void setup() {
    String methodName = "setup";
    Logger.log("Method Start");
    driver = Driver.getDriver();
    Logger.log("Method End");
  }

  @Test
  public void test01() {
    String methodName = "test01";
    Logger.log("Method Start");
    WebDriverWait wait = new WebDriverWait(driver, 10);
    driver.get("http://www.google.co.in");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@title='Search']")));
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Selenium");
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

  @AfterMethod
  public void quitBrowser() {
    String methodName = "quitBrowser";
    Logger.log("Method Start");
    Logger.log("Method End");
  }

}

