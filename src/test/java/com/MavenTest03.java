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

public class MavenTest03 {
  
  WebDriver driver;
  
  @BeforeMethod
  public void beforeMethod() {
    String methodName = "MavenTest03.beforeMethod";
    Logger.log("MavenTest03", methodName + "Start");
    driver = Driver.getDriver();
    Logger.log("MavenTest03", methodName + "End");
  }

  @AfterMethod
  public void afterMethod() {
    String methodName = "MavenTest03.afterMethod";
    Logger.log("MavenTest03", methodName + "Start");
    Logger.log("MavenTest03", methodName + "End");
  }

  @Test
  public void testCase1() {
    String methodName = "MavenTest03.testCase1";
    Logger.log("MavenTest03", methodName + "Start");
    WebDriverWait wait;
    wait = new WebDriverWait(driver, 10);
    driver.get("http://www.google.co.in");
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@alt='Google']")));
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Maven");
    element.sendKeys(Keys.ENTER);
    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(@title,'Go to Google Home')]")));
    Logger.log("MavenTest03", methodName + "End");
    Assert.assertEquals(true, false);
  }

}
