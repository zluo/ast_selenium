package com.silanis.ast_selenium.utils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.perf4j.StopWatch;
import org.perf4j.log4j.Log4JStopWatch;

/**
 * 
 * This class provide some methods that wrap the functionality
 * of {@link www.google.com}WebElement click action. the action will return next WebEl fully finished by validating the,
 * 
 * @author zluo
 *
 */

public class TestContext {
	private static Random random = new Random();
	private static int randamDelay = 1000;
	private static int initDelay = 1000;
	private static int i = 1;
	private static int waitTimeout = 500;

	public static WebElement click(String tag, String message, WebDriver driver,
			WebElement input, By validateElement, By findElement) {
		StopWatch stopWatch = new Log4JStopWatch();
		randomDelay();
		stopWatch.start();
		input.click();
		driver.findElement(validateElement);
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
		stopWatch.stop(tag, message);
		return driver.findElement(findElement);
	}

	public static WebElement click(String tag, String message, WebDriver driver,
			By validateElement, By findElement) {
		randomDelay();
		StopWatch stopWatch = new Log4JStopWatch();
		stopWatch.start();
		driver.findElement(validateElement);
		driver.manage().timeouts().implicitlyWait(waitTimeout, TimeUnit.SECONDS);
		stopWatch.stop(tag, message);
		return driver.findElement(findElement);
	}

	public static WebElement click(String tag, String message, WebDriver driver,
			WebElement input, By validateElement) {
		return click(tag, message, driver, input, validateElement,validateElement);
	}

	public static WebElement click(String tag, String message, WebDriver driver,
			By validateElement) {
		return click(tag, message, driver, validateElement, validateElement);
	}

	public static void init() {
		Thread.currentThread().setName("User" + i);
		i++;
		try {
			Thread.sleep(i * initDelay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void randomDelay(int delay) {
		try {
			Thread.sleep(random.nextInt(delay));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void randomDelay() {
		randomDelay(randamDelay);
	}

}
