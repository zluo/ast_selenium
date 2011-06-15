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

public class SoapProxySimulator {
	
	// wait for the page loaded, seconds
	private int waitTimeout=5;
    private String baseUrl = "http://localhost:8080/awspp";
	private static int delay =5000;
	private static int i =1;
	private static Random random = new Random();
    
	@Test(threadPoolSize = 100, invocationCount = 100, timeOut = 1000000000)
	public void runTest() throws Exception {
//		testAwspp(3, 10);
//		testAwsppWebDriver(3, 10);
		testAwsppHtmlUtil(3, 100);
		Thread.sleep(1000);
	}
	
	public void testAwspp(int docs, int times) throws InterruptedException 
	{
		WebDriver driver = new FirefoxDriver();

		Selenium selenium = new WebDriverBackedSelenium(driver, baseUrl);
		selenium.open(baseUrl);
		for (int j = 0; j < times; j++) {
			selenium.waitForPageToLoad(""+ waitTimeout);
			selenium.type("name=docs", "" + docs);
			selenium.click("xpath=//input[@type='button' or @type='submit' or @type='image' or @type='reset'][@value='Create Transaction'][@id='create']");
			selenium.waitForPageToLoad(""+ waitTimeout);
			selenium.click("xpath=//input[@type='button' or @type='submit' or @type='image' or @type='reset'][@value='Resume Transaction'][@id='resume']");

			for (int i = 0; i < docs; i++) {
				selenium.click("xpath=//img[@alt='Click to sign']");
				selenium.waitForPageToLoad(""+ waitTimeout);
				selenium.click("xpath=//input[@type='button' or @type='submit' or @type='image' or @type='reset'][@value='Click!!!']");
				selenium.waitForPageToLoad(""+ waitTimeout);
			}
			selenium.click("link=Go Back to Parent Page");
		}
		selenium.stop();
	}
	
	public void testAwsppWebDriver(int docs, int times) throws InterruptedException 
	{
	    init();
		WebDriver driver = new FirefoxDriver();
//		WebDriver driver = new HtmlUnitDriver();
		for (int j = 0; j < times; j++) {
			driver.get(baseUrl);
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
			// create/resume transaction 
			WebElement createButton =driver.findElement(By.id("resume")); 
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
			createButton.click();
			for (int i = 0; i < docs; i++) {
				
				WebElement img =driver.findElement(By.xpath("//img[@alt='Click to sign']")); 
				driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
				img.click();
				
				WebElement submitButton =driver.findElement(By.name("button")); 
				driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  

				submitButton.click();
		
			}
			WebElement link = driver.findElement(By.linkText("Go Back to Parent Page"));
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
			link.click();

		}
			driver.close();
	}
	
	public void testAwsppHtmlUtil(int docs, int times) throws InterruptedException {
		init();
		String message =Thread.currentThread().getName() + " loop:(%d/%d), docs(%d/%d) ";
		String message1 =Thread.currentThread().getName() + " loop:(%d/%d)";
		
		StopWatch stopWatch = new Log4JStopWatch();
		
		HtmlUnitDriver driver = new HtmlUnitDriver(true);
		for (int j = 0; j < times; j++) {
			
			stopWatch.start();
			goToTestPage(driver);
			stopWatch.stop("TestPage", String.format(message1, j,times));

			randomDelay();
			stopWatch.start();
			createTransaction(driver);
			stopWatch.stop("Create/Resume", String.format(message, j,times, i,docs) );
			
			
			for (int i = 0; i < docs; i++) {
				
				accept(driver, String.format(message, j,times, i,docs));
				
				randomDelay();
				stopWatch.start();
				submit(driver);
                if (i== docs-1)
                {
        			WebElement link = driver.findElement(By.linkText("Go Back to Parent Page"));
        			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
                }
                else
                {
                	WebElement img =driver.findElement(By.xpath("//img[@alt='Click to sign']")); 
            		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS); 
                }
                stopWatch.stop("Submit", message);
			}

			WebElement link = driver.findElement(By.linkText("Go Back to Parent Page"));
			driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
			stopWatch.stop("Submit-f", String.format(message, j,times, i,docs));
			link.click();
			
		}
		driver.close();
	}
	
	private void goToTestPage(WebDriver driver)
	{
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		// create/resume transaction 
		WebElement createButton =driver.findElement(By.id("resume")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
	}
	
	private void createTransaction(WebDriver driver)
	{
		WebElement createButton =driver.findElement(By.id("resume")); 
		createButton.click();
		WebElement img =driver.findElement(By.xpath("//img[@alt='Click to sign']")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
	}

	private void submit(WebDriver driver)
	{
		WebElement submitButton =driver.findElement(By.name("button")); 
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);  
		
		submitButton.click();
		
		WebElement awsForm =driver.findElement(By.name("awsForm")); 
//		System.out.println(awsForm.toString());
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
