package com.qa.planfirmatechnologies;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BaseClass {
	
	public static WebDriver driver;
	static JavascriptExecutor js;
	static Actions ac;
	static WebDriverWait wait;
	static TakesScreenshot sc ;
	
	public void setupDriver() {
			ChromeOptions options = new ChromeOptions();
		//	options.setExperimentalOption("debuggerAddress", "localhost:9222");
		//	options.addArguments("--headless");
			driver = new ChromeDriver(options);
			sc = ((TakesScreenshot)driver);
			js = ((JavascriptExecutor) driver);
			ac = new Actions(driver);
			wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			System.setProperty("webdriver.chrome.driver","C:\\Users\\dilip\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver.get("https://magento.softwaretestingboard.com/");
			driver.manage().window().maximize();
		
		
	}


	
	
	
	

}
