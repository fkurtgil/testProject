package com.selenium.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class SauceDemoTest9 {
    WebDriver driver;
    WebDriverWait wait;
    String productName;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(priority = 1)
    public void loginTest() {
        driver.get("https://www.saucedemo.com/");
        
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        WebElement loginButton = driver.findElement(By.xpath("//input[@id='login-button']"));

        usernameInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        loginButton.click();

        WebElement inventoryContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='inventory_container']")));
        Assert.assertNotNull(inventoryContainer, "Giriş başarısız!");
    }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void addToCartTest() {
        List<WebElement> products = driver.findElements(By.xpath("//div[@class='inventory_item']"));
        
        Random rand = new Random();
        int randomIndex = rand.nextInt(products.size());

        WebElement randomProduct = products.get(randomIndex);
        productName = randomProduct.findElement(By.xpath(".//div[@class='inventory_item_name']")).getText();
        
        WebElement addToCartButton = randomProduct.findElement(By.xpath(".//button[contains(text(),'Add to cart')]"));
        addToCartButton.click();

        WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='shopping_cart_badge']")));
        Assert.assertEquals(cartBadge.getText(), "1", "Ürün sepete eklenmedi!");
    }

    @Test(priority = 3, dependsOnMethods = "addToCartTest")
    public void proceedToCheckoutTest() {
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();

        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='inventory_item_name' and text()='" + productName + "']")));
        Assert.assertNotNull(cartItem, "Sepette ürün bulunamadı!");

        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        WebElement checkoutPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Checkout: Your Information']")));
        Assert.assertNotNull(checkoutPage, "Checkout sayfası açılmadı!");
    }

    @Test(priority = 4, dependsOnMethods = "proceedToCheckoutTest")
    public void completeCheckoutTest() {
        driver.findElement(By.xpath("//input[@id='first-name']")).sendKeys("Ali");
        driver.findElement(By.xpath("//input[@id='last-name']")).sendKeys("Veli");
        driver.findElement(By.xpath("//input[@id='postal-code']")).sendKeys("34000");
        driver.findElement(By.xpath("//input[@id='continue']")).click();

        WebElement finishButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='finish']")));
        finishButton.click();

        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='Thank you for your order!']")));
        Assert.assertNotNull(successMessage, "Sipariş tamamlanamadı!");
    }
}
