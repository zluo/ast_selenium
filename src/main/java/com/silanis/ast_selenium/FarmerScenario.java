package com.silanis.ast_selenium;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.perf4j.LoggingStopWatch;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.thoughtworks.selenium.Selenium;

public class FarmerScenario {
	
	// wait for the page loaded, seconds
	private int waitTimeout=5;
    private String baseUrl = "http://50.16.136.41:8080/farmersr5/";
	private static int delay =5000;
	private static int i =1;
	private static Random random = new Random();
    
	@Test(threadPoolSize = 100, invocationCount = 10, timeOut = 1000000000)
	public void runTest() throws Exception {
//		testAwspp(3, 10);
		testFarmerHtmlUtil(100);
		Thread.sleep(1000);
	}
	
	
	public void testFarmerHtmlUtil(int times) throws InterruptedException {
		init();
		String message1=Thread.currentThread().getName() + " loop:(%d/%d)";

		StopWatch stopWatch = new Log4JStopWatch();
		WebDriver driver = new FirefoxDriver();		
//		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		for (int j = 0; j < times; j++) {
			System.out.format(message1,j,times);
			stopWatch.start();
			WebElement createAndResumeButton= goToTestPage(driver);
			stopWatch.stop("TestPage", String.format(message1, j,times));


			
			randomDelay();
			stopWatch.start();
			WebElement beginButton= createAndResumeTransaction(driver,createAndResumeButton);
			stopWatch.stop("Create/Resume", String.format(message1, j,times) );

			
			stopWatch.start();
			WebElement accept= login(driver,beginButton);
			stopWatch.stop("login", String.format(message1, j,times));

			stopWatch.start();
			WebElement aws_block0 = accept(driver,accept);
			stopWatch.stop("accept", String.format(message1, j,times));			

			
			stopWatch.start();
			WebElement next = clickToSign(driver,aws_block0);
			stopWatch.stop("clickToSgin", String.format(message1, j,times));			

			stopWatch.start();
			aws_block0 = next(driver,next);
			stopWatch.stop("next", String.format(message1, j,times));			

			stopWatch.start();
			next = clickToSign1(driver,aws_block0);
			stopWatch.stop("clickToSgin", String.format(message1, j,times));	
			
			stopWatch.start();
			WebElement submit = next2(driver,next);
			stopWatch.stop("next", String.format(message1, j,times));	

			stopWatch.start();
			WebElement exit = submit(driver,next);
			stopWatch.stop("submit", String.format(message1, j,times));	
			
			stopWatch.start();
			exit(driver,exit);
			stopWatch.stop("exit", String.format(message1, j,times));	

		}
		driver.close();
	}
	
	private WebElement goToTestPage(WebDriver driver)
	{
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		// create/resume transaction 
		WebElement createAndResumeButton =driver.findElement(By.xpath("//input[@type='button'][@value='Create and Resume']")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		return createAndResumeButton;
	}
	
	private WebElement createAndResumeTransaction(WebDriver driver, WebElement input)
	{
		input.click();
		WebElement beginButton =driver.findElement(By.id("aws_adaptedAuthenticationPage_validate")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
		return beginButton;
	}

	private WebElement login(WebDriver driver, WebElement input)
	{

		WebElement fieldset= driver.findElement(By.tagName("fieldset"));
		
		List<WebElement> lists = fieldset.findElements(By.tagName("p"));
		for(WebElement p: lists)
		{
			WebElement label= p.findElement(By.tagName("label"));
//			System.out.println(label.getText());
			
			if (label.getText().contains("Last Name"))
			{
			   WebElement in =p.findElement(By.tagName("input"));
			   in.sendKeys("parker");
			}
			else if(label.getText().contains("Personal Identification Number"))
			{
				   WebElement in =p.findElement(By.tagName("input"));
				   in.sendKeys("1234a");
			}
			else if(label.getText().contains("Residence Zip Code"))
			{
				   WebElement in =p.findElement(By.tagName("input"));
				   in.sendKeys("12345a");
			}
		}
		
		input.click();
		WebElement acceptButton =driver.findElement(By.id("aws_acceptPage_accept")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
		return acceptButton;
	}
	

	private WebElement accept(WebDriver driver, WebElement input)
	{
		input.click();
		WebElement next =driver.findElement(By.id("aws_block0")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		return next;
	}

	private WebElement clickToSign(WebDriver driver, WebElement input)
	{
		input.click();
		WebElement logo =driver.findElement(By.id("awsApprovalBlockcontract1Signature1Signature1_logo"));
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  

		WebElement next =driver.findElement(By.id("aws_signPage_accept"));
		return next;
	}

	private WebElement clickToSign1(WebDriver driver, WebElement input)
	{
		input.click();
		WebElement logo =driver.findElement(By.id("awsApprovalBlockcontract2Signature1Signature1_logo"));
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		
		WebElement next =driver.findElement(By.id("aws_signPage_accept"));
		return next;
	}
	
	private WebElement next(WebDriver driver, WebElement input)
	{
//		input.click();
		WebElement next =driver.findElement(By.id("aws_signPage_accept"));
		next.click();
		WebElement aws_block0 =driver.findElement(By.id("aws_block0")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		return aws_block0;
	}

	private WebElement next2(WebDriver driver, WebElement input)
	{
//		input.click();
		WebElement next =driver.findElement(By.id("aws_signPage_accept"));
		next.click();
		WebElement submit =driver.findElement(By.id("aws_adaptedConfirmationPage_accept")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		return submit;
	}

	private WebElement submit(WebDriver driver, WebElement input)
	{
//		input.click();
		WebElement submit =driver.findElement(By.id("aws_adaptedConfirmationPage_accept")); 
		submit.click();
		WebElement exit =driver.findElement(By.id("aws_adaptedDownloadPage_continue")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		return exit;
	}
	
	private void exit(WebDriver driver, WebElement input)
	{
		input.click();
		String url =driver.getCurrentUrl(); 
//		assert (url.equalsIgnoreCase("www.farmers.com"));
	}
		
	
	private void submit(WebDriver driver)
	{
		WebElement submitButton =driver.findElement(By.name("button")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		
		submitButton.click();
		
		WebElement awsForm =driver.findElement(By.name("awsForm")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		awsForm.submit();
		
	}

	
	
	
	
	
	
	
	private void accept(WebDriver driver, String message)
	{
		StopWatch stopWatch = new Log4JStopWatch();
		stopWatch.start();
		
		WebElement img =driver.findElement(By.xpath("//img[@alt='Click to sign']")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		img.click();
		
		
		List<WebElement> signed_imgs = driver.findElements(By.xpath("//img[@alt='Signed']")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		
		if (signed_imgs.size() !=0)
		{
			stopWatch.stop("accept", message);
		}
		else
		{
	    randomDelay();
		stopWatch.start();
		WebElement awsForm =driver.findElement(By.name("awsForm")); 

		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		awsForm.submit();
		
		signed_imgs = driver.findElements(By.xpath("//img[@alt='Signed']")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		stopWatch.stop("accept'", message );
		}
	}
	
	
	
	private static void init() 
	{
       Thread.currentThread().setName("User" + i);
       i++;
       try {
		Thread.sleep(i*delay);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
	
	private static void randomDelay() 
	{
       try {
		Thread.sleep(random.nextInt(delay));
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	}
	
}
