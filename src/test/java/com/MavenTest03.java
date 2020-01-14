package com;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import customLogger.Logger;

public class MavenTest03 {
  @BeforeMethod
  public void beforeMethod() {
    String methodName = "MavenTest03.beforeMethod";
    Logger.log("MavenTest03", methodName + "Start");
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
    Logger.log("MavenTest03", methodName + "End");
    Assert.assertEquals(true, false);
  }

  @BeforeClass
  public void beforeClass() {
    String methodName = "MavenTest03.beforeClass";
    Logger.log("MavenTest03", methodName + "Start");
    Logger.log("MavenTest03", methodName + "End");
  }

  @AfterClass
  public void afterClass() {
    String methodName = "MavenTest03.afterClass";
    Logger.log("MavenTest03", methodName + "Start");
    Logger.log("MavenTest03", methodName + "End");
  }

}
