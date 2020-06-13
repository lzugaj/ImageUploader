package com.luv2code.imageuploader.ui;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by lzugaj on Wednesday, April 2020
 */

public class SeleniumDemoTest {

    private static WebDriver driver;

    @Test
    public void testFindH1Tag() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\lzugaj\\Programs\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8050/image-uploader/home");

        WebElement title = driver.findElement(By.tagName("h1"));

        assertEquals("Section", title.getText());

        driver.quit();
    }

    @Test
    public void testSignInProcess() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\lzugaj\\Programs\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8050/image-uploader/login");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("admin");

        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("admin");

        driver.findElement(By.id("loginBtn")).click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        WebElement title = driver.findElement(By.tagName("h1"));

        assertEquals("Section", title.getText());
    }

    @Test
    public void testSignUpProcess() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\lzugaj\\Programs\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8050/image-uploader/login");

        driver.findElement(By.id("signUpBtn")).click();

        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Luka");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Horvat");

        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("luka.horvat@gmail.com");

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("lhorvat");

        WebElement password1 = driver.findElement(By.id("password"));
        password1.sendKeys("horvat");

        WebElement password2 = driver.findElement(By.id("matchingPassword"));
        password2.sendKeys("horvat");

        driver.findElement(By.id("signUpButtonButton")).click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Test
    public void testFilterPosts() {
        System.setProperty("webdriver.chrome.driver","C:\\Users\\lzugaj\\Programs\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8050/image-uploader/home");

        WebElement hashTag = driver.findElement(By.id("hashTag"));
        hashTag.sendKeys("grgur");

        WebElement sizeFrom = driver.findElement(By.id("sizeFrom"));
        sizeFrom.sendKeys("1");

        WebElement dateFrom = driver.findElement(By.id("dateFrom"));
        dateFrom.sendKeys("06/12/2019");

        WebElement author = driver.findElement(By.id("author"));
        author.sendKeys("ad");

        driver.findElement(By.id("searchFilter")).click();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }
}
