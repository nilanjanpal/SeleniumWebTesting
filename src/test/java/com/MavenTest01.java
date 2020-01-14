package com;

import org.testng.annotations.Test;
import customLogger.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;

public class MavenTest01 {


  @BeforeMethod
  public void beforeMethod() {
    String methodName = "MavenTest01.beforeMethod";
    Logger.log("MavenTest01", methodName + "Start");
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
    Logger.log("MavenTest01", methodName + "End");
  }

  @BeforeClass
  public void beforeClass() {
    String methodName = "MavenTest01.beforeClass";
    Logger.log("MavenTest01", methodName + "Start");
    Logger.log("MavenTest01", methodName + "End");
  }

  @AfterClass
  public void afterClass() {
    String methodName = "MavenTest01.afterClass";
    Logger.log("MavenTest01", methodName + "Start");
    Logger.log("MavenTest01", methodName + "End");
  }

}
