package com.Webscraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.NoSuchElementException;

public class WebDriveImpl {
    private static WebDriver driver;
    private static String url;

    private WebDriveImpl() {
    }

    public static void setWebDriver(String id) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\dionysis\\Desktop\\chromedriver.exe");
        // Set up ChromeDriver with some options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Optional: Run Chrome in headless mode
        driver = new ChromeDriver(options);
        url = "https://liste-exposants.hubj2c.com/natexpo23/main?id=" + id;
        driver.get(url);
    }

    public static WebDriver getDriver() {
        return driver;
    }
    public static FluentWait<WebDriver> getFluentWait() {
        return new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30L)).pollingEvery(Duration.ofSeconds(5L)).ignoring(NoSuchElementException.class);
    }
}
