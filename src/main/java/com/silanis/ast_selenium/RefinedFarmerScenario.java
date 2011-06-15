package com.silanis.ast_selenium;

import static com.silanis.ast_selenium.utils.TestContext.click;
import static com.silanis.ast_selenium.utils.TestContext.init;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.testng.annotations.Test;

public class RefinedFarmerScenario {

	// wait for the page loaded, seconds
	private String baseUrl = "http://50.16.136.41:8080/farmersr5/";

	@Test(threadPoolSize = 100, invocationCount = 1, timeOut = 1000000000)
	public void runTest() throws Exception {
		// testAwspp(3, 10);
		testFarmerHtmlUtil(100);
		Thread.sleep(1000);
	}

	public void testFarmerFireFox(int times) throws InterruptedException {
		init();
		String message1 = Thread.currentThread().getName() + " loop:(%d/%d)";

		StopWatch stopWatch = new Log4JStopWatch();
		WebDriver driver = new FirefoxDriver();
		// HtmlUnitDriver driver = new HtmlUnitDriver(true);
		for (int j = 0; j < times; j++) {
			String message = String.format(message1, j, times);
			System.out.println(message);
			driver.get(baseUrl);
			
			WebElement createAndResumeButton = click(
					"1:testpage",
					message,
					driver,
					By.xpath("//input[@type='button'][@value='Create and Resume']"));
			
			WebElement loginButton = click("2:create", message, driver,
					createAndResumeButton,
					By.id("aws_adaptedAuthenticationPage_validate"));
			
			login(driver);

			WebElement acceptButton = click("3:login", message, driver,
					loginButton, By.id("aws_acceptPage_accept"));

			WebElement aws_block0 = click("4:accept", message, driver,
					acceptButton, By.id("aws_block0"));

			WebElement nextButton = click(
					"4:accept",
					message,
					driver,
					aws_block0,
					By.id("awsApprovalBlockcontract1Signature1Signature1_logo"),
					By.id("aws_signPage_accept"));

			WebElement aws_block1 = click("5:sign", message, driver,
					nextButton, By.id("aws_block0"));

			WebElement next2Button = click(
					"4:accept",
					message,
					driver,
					aws_block1,
					By.id("awsApprovalBlockcontract2Signature1Signature1_logo"),
					By.id("aws_signPage_accept"));
			
			WebElement submitButton = click("5:sign", message, driver,
					next2Button, By.id("aws_adaptedConfirmationPage_accept"));
			
			WebElement exitButton = click("9:exit", message, driver,
					submitButton, By.id("aws_adaptedDownloadPage_continue"));
			exitButton.click();

		}
		driver.close();
	}

	public void testFarmerHtmlUtil(int times) throws InterruptedException {
		init();
		String message1 = Thread.currentThread().getName() + " loop:(%d/%d)";

		StopWatch stopWatch = new Log4JStopWatch();
		//WebDriver driver = new FirefoxDriver();
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		for (int j = 0; j < times; j++) {
			String message = String.format(message1, j, times);
			System.out.println(message);
			driver.get(baseUrl);
			
			WebElement createAndResumeButton = click(
					"1:testpage",
					message,
					driver,
					By.xpath("//input[@type='button'][@value='Create and Resume']"));
			
			WebElement loginButton = click("2:create", message, driver,
					createAndResumeButton,
					By.id("aws_adaptedAuthenticationPage_validate"));
			
			login(driver);

			WebElement acceptButton = click("3:login", message, driver,
					loginButton, By.id("aws_acceptPage_accept"));

			WebElement aws_block0 = click("4:accept", message, driver,
					acceptButton, By.id("aws_block0"));

			WebElement nextButton = click(
					"4:accept",
					message,
					driver,
					aws_block0,
					By.id("awsApprovalBlockcontract1Signature1Signature1_logo"),
					By.id("aws_signPage_accept"));

			WebElement aws_block1 = click("5:sign", message, driver,
					nextButton, By.id("aws_block0"));

			WebElement next2Button = click(
					"4:accept",
					message,
					driver,
					aws_block1,
					By.id("awsApprovalBlockcontract2Signature1Signature1_logo"),
					By.id("aws_signPage_accept"));
			
			WebElement submitButton = click("5:sign", message, driver,
					next2Button, By.id("aws_adaptedConfirmationPage_accept"));
			
			WebElement exitButton = click("9:exit", message, driver,
					submitButton, By.id("aws_adaptedDownloadPage_continue"));
			exitButton.click();

		}
		driver.close();
	}

	
	
	
	private void login(WebDriver driver) {

		WebElement fieldset = driver.findElement(By.tagName("fieldset"));

		List<WebElement> lists = fieldset.findElements(By.tagName("p"));
		for (WebElement p : lists) {
			WebElement label = p.findElement(By.tagName("label"));
			// System.out.println(label.getText());

			if (label.getText().contains("Last Name")) {
				WebElement in = p.findElement(By.tagName("input"));
				in.sendKeys("parker");
			} else if (label.getText().contains(
					"Personal Identification Number")) {
				WebElement in = p.findElement(By.tagName("input"));
				in.sendKeys("1234a");
			} else if (label.getText().contains("Residence Zip Code")) {
				WebElement in = p.findElement(By.tagName("input"));
				in.sendKeys("12345a");
			}
		}
	}
}