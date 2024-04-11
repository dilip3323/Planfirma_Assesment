package com.qa.planfirmatechnologies;

import java.time.Duration;

import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.bytebuddy.utility.RandomString;

import org.openqa.selenium.support.ui.*;

public class OrderFlow {

	static WebDriver driver;
	static JavascriptExecutor js;
	static Actions ac;
	WebDriverWait wait;

	@BeforeTest
	public void driverSetup() {
		ChromeOptions options = new ChromeOptions();
	//	options.setExperimentalOption("debuggerAddress", "localhost:9222");
	//	options.addArguments("--headless");
		driver = new ChromeDriver(options);
		

		js = ((JavascriptExecutor) driver);
		ac = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\dilip\\Downloads\\chromedriver_win32\\chromedriver.exe");

		driver.get("https://magento.softwaretestingboard.com/");
		driver.manage().window().maximize();
	}
	
	  
	  @Test(priority = 1) public void goToCreateAnAccountPage() {
	  
	  driver.findElement(By.xpath("//div[@class='panel header']//a[normalize-space()='Create an Account']")).click();
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50)); // //
	  String pageTitle = driver.getTitle(); 
	  }
	  
	  @Test(dependsOnMethods = "goToCreateAnAccountPage") public void
	  createNewAccount() {
	  
	  driver.findElement(By.id("firstname")).sendKeys("Dilip");
	  driver.findElement(By.id("lastname")).sendKeys("Vishwkarma");

	  
	  driver.findElement(By.id("email_address")).sendKeys("dilic.subzero"+Math.floor(Math.random() * 100)+"14@gmail.com");
	  driver.findElement(By.id("password")).sendKeys("Dilip#4567");
	  driver.findElement(By.id("password-confirmation")).sendKeys("Dilip#4567");
	  driver.findElement(By.xpath("//button[@class='action submit primary']")).click(); }
	  
	 

	/*
	 * @Test(priority = 1) public void login() throws InterruptedException {
	 * 
	 * driver.findElement(By.
	 * xpath("//div[@class='panel header']//a[contains(text(),'Sign In')]")).click()
	 * ; driver.findElement(By.xpath("//input[@id='email']")).sendKeys(
	 * "dilip_subzero@yahoo.com"); driver.findElement(By.
	 * xpath("//fieldset[@class='fieldset login']//input[@id='pass']"))
	 * .sendKeys("Assesment@123"); driver.findElement(By.
	 * xpath("//fieldset[@class='fieldset login']//span[contains(text(),'Sign In')]"
	 * )).click();
	 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); }
	 */

	@Test(priority = 2)
	public void moveToFitnessEquipmentPage() {

		WebElement Gear = driver.findElement(By.xpath("//nav[@class='navigation']//li//span[text()='Gear']"));
		WebElement FitnessEquiment = driver
				.findElement(By.xpath("//a[@id='ui-id-26']//span[contains(text(),'Fitness Equipment')]"));
		ac.moveToElement(Gear).build().perform();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		ac.moveToElement(FitnessEquiment).click().build().perform();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

	}

	@Test(priority = 3)
	public void selectCategory() throws InterruptedException {
		driver.findElement(By.xpath("//div[normalize-space()='Category']")).click();
		driver.findElement(By.xpath("//div[@id='narrow-by-list']//div[1]//div[2]//ol[1]//li[2]//a[1] ")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	}

	@Test(priority = 4)
	public void addAllItems() throws InterruptedException {

		js.executeScript("window.scrollBy(0,350)");

		driver.findElement(
				By.xpath("//a[@class='product photo product-item-photo']//img[@alt='Sprite Yoga Companion Kit']"))
				.click();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		js.executeScript("window.scrollBy(0,250)");

		driver.findElement(By.xpath("//span[text()='Customize and Add to Cart']")).click();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

		for (int i = 1; i <= 3; i++) {

			WebElement option = driver.findElement(By.xpath("//input[@id='bundle-option-1-" + i + "']"));
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@id='bundle-option-1-" + i + "']")));
			js.executeScript("arguments[0].scrollIntoView(true);", option);
			if (!option.isSelected()) {

				option.click();
				Thread.sleep(1000);
			}

			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='product-price-46']")));
			Thread.sleep(1000);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='product-addtocart-button']")))
					.click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Added']")));

			// js.executeScript("arguments[0].scrollIntoView(true);", addToCart);

		}

	}

	@Test(priority = 5)
	public void checkOut() {

		WebElement showCart = driver.findElement(By.xpath("//a[@class='action showcart']"));
		js.executeScript("arguments[0].scrollIntoView(true);", showCart);
		showCart.click();
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='top-cart-btn-checkout']"))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));

	}

	@Test(priority = 6)
	public void verifyOrderSummary() throws InterruptedException {

		boolean isAddressAdded = driver.findElements(By.xpath("//div[@class='shipping-address-item selected-item']"))
				.size() > 0;

		if (!isAddressAdded) {

			// driver.findElement(By.xpath("//span[normalize-space()='New
			// Address']")).click();
			// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='shipping-new-address-form']")));

			WebElement firstName = driver.findElement(By.xpath("//input[@name='firstname']"));
			js.executeScript("arguments[0].scrollIntoView(true);", firstName);

			if (firstName.getText().isBlank()) {
				firstName.clear();
				firstName.sendKeys("Dilip");
			}

			WebElement secondName = driver.findElement(By.xpath("//input[@name='lastname']"));
			js.executeScript("arguments[0].scrollIntoView(true);", secondName);
			if (secondName.getText().isBlank()) {
				secondName.clear();
				secondName.sendKeys("Vishwakarma");
			}

			WebElement streetAddress = driver.findElement(By.xpath("//input[@name='street[0]']"));
			js.executeScript("arguments[0].scrollIntoView(true);", streetAddress);

			if ((streetAddress.getText().isBlank())) {
				streetAddress.clear();
				streetAddress.sendKeys("B306 Impact Imperial, Lane no.14, cs colony, powai");
			}

			WebElement city = driver.findElement(By.xpath("//input[@name='city']"));
			js.executeScript("arguments[0].scrollIntoView(true);", city);

			if ((city.getText().isBlank())) {
				city.clear();
				city.sendKeys("Mumbai");
			}

			WebElement postalCode = driver.findElement(By.xpath("//input[@name='postcode']"));
			js.executeScript("arguments[0].scrollIntoView(true);", postalCode);
			if (postalCode.getText().isBlank()) {
				postalCode.clear();
				postalCode.sendKeys("400072");
			}

			Select selectCountry = new Select(driver.findElement(By.xpath("//select[@name='country_id']")));
			selectCountry.selectByValue("IN");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
			

			Select selectState = new Select(driver.findElement(By.xpath("//select[@name='region_id']")));
			selectState.selectByValue("553");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));

			
			WebElement phoneNumber = driver.findElement(By.xpath("//input[@name='telephone']"));
			js.executeScript("arguments[0].scrollIntoView(true);", phoneNumber);
			if (phoneNumber.getText().isBlank()) {
				phoneNumber.clear();
				phoneNumber.sendKeys("9820777939");
				
				

			}
			
		}
			
			WebElement nextButton = driver.findElement(By.xpath("//span[text()='Next']"));
			js.executeScript("arguments[0].scrollIntoView(true);", nextButton);
			nextButton.click();

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			WebElement chckSaveAddress = driver.findElement(By.xpath("//button[@class='action primary action-save-address']"));
			if(!chckSaveAddress.isSelected()) {
			driver.findElement(By.xpath("//button[@class='action primary action-save-address']")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));}
		

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
		wait.until(
		ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='Order Summary']")));

	}

	@Test(priority = 7)
	public void placeOrder() throws InterruptedException {

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='opc-block-summary']")));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='billing-address-details']")));
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='shipping-information-content']")));

		boolean isSameAddress = driver.findElement(By.xpath("//input[@id='billing-address-same-as-shipping-checkmo']"))
				.isSelected();
		if (!isSameAddress) {
			driver.findElement(By.xpath("//input[@id='billing-address-same-as-shipping-checkmo']")).click();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		}

		WebElement placeOrderButton = driver.findElement(By.xpath("//span[normalize-space()='Place Order']"));
		js.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);
		wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Loading...']")));
	}

	@Test(priority = 8)
	public void OrderConfirmation() {
		String orderConfirmation = "Thank you for your purchase!";
		String orderMessage = driver.findElement(By.xpath("//span[@class='base']")).getText();
		Assert.assertEquals(orderConfirmation, orderMessage);
	}

	@AfterTest
	public void exitDriver() {
	driver.quit();

	}

}
