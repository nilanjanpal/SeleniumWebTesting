package com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MavenTest02 {
	
	WebDriver driver = null;
	
	@BeforeMethod
	public void setup() {
		System.out.println("launch FF");
		driver = new FirefoxDriver();
	}
	
	@Test
	public void test01() {
		System.out.println("Test02");
	}
	
	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	}

}

