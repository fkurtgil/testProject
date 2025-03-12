package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class TestAddToCart {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void addToCartTest() {
        driver.get("https://automationexercise.com");

        // Login işlemi
        driver.findElement(By.linkText("Signup / Login")).click();
        driver.findElement(By.name("email")).sendKeys("testuser@example.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.id("login")).click();

        // Ürün sayfasına git
        driver.findElement(By.linkText("Women")).click();

        // Ürünü sepete ekle
        WebElement product = driver.findElement(By.xpath("//a[contains(text(), 'Blouse')]"));
        product.click();
        driver.findElement(By.xpath("//button[contains(text(), 'Add to cart')]")).click();

        // Sepeti kontrol et
        driver.findElement(By.xpath("//a[contains(text(), 'View Cart')]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        WebElement cartProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(), 'Blouse')]")));
        Assert.assertTrue(cartProduct.isDisplayed(), "Product not added to cart");
    }
}
