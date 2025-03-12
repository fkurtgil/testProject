package com.selenium.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void googleSearchTest() {
        // Google'ı aç
        driver.get("https://www.google.com");

        // Çerezleri kabul et butonu varsa, tıklayalım (Bazı bölgelerde çıkabilir)
        try {
            WebElement acceptCookies = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Tümünü kabul et']")));
            acceptCookies.click();
        } catch (Exception e) {
            System.out.println("Çerez butonu görünmedi, devam ediliyor...");
        }

        // Arama kutusunu bul ve "Selenium WebDriver" yaz
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver");
        searchBox.submit();

        // **Arama sonuçları yüklenene kadar bekle**
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("search")));

        // **Gerçek başlığı konsola yaz ve test edelim**
        String actualTitle = driver.getTitle();
        System.out.println("Sayfa başlığı: " + actualTitle);
        assertTrue(actualTitle.toLowerCase().contains("selenium webdriver"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
