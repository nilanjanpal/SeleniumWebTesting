package com;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import customLogger.Logger;

public class MavenTest02 {

  WebDriver driver = null;

  @BeforeMethod
  public void setup() {
    String methodName = "MavenTest02.setup";
    Logger.log("MavenTest02", methodName + "Start");
    driver = new FirefoxDriver();
    Logger.log("MavenTest02", methodName + "End");
  }

  @Test
  public void test01() {
    String methodName = "MavenTest02.test01";
    Logger.log("MavenTest02", methodName + "Start");
    driver.get("http://www.google.co.in");
    WebElement element = driver.findElement(By.xpath("//input[@title='Search']"));
    element.sendKeys("Selenium");
    element.sendKeys(Keys.ENTER);
    Logger.log("MavenTest02", methodName + "End");
  }

  @AfterMethod
  public void quitBrowser() {
    String methodName = "MavenTest02.quitBrowser";
    Logger.log("MavenTest02", methodName + "Start");
    driver.quit();
    Logger.log("MavenTest02", methodName + "End");
  }

}

