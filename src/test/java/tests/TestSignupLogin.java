package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class TestSignupLogin {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void signupTest() {
        driver.get("https://automationexercise.com");

        // Signup linkine tıklayın
        driver.findElement(By.linkText("Signup / Login")).click();

        // Gerekli formu doldurup kayıt işlemi gerçekleştirelim
        driver.findElement(By.name("name")).sendKeys("Test User");
        driver.findElement(By.name("email")).sendKeys("testuser@example.com");
        driver.findElement(By.name("signup")).click();

        // Şifre oluşturma
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("submit")).click();

        // Hesabın başarıyla oluşturulduğunu doğrulama
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        Assert.assertTrue(logoutButton.isDisplayed(), "Signup failed");
    }

    @Test(priority = 2)
    public void loginTest() {
        driver.get("https://automationexercise.com");

        // Login linkine tıklayın
        driver.findElement(By.linkText("Signup / Login")).click();

        // Giriş yap
        driver.findElement(By.name("email")).sendKeys("testuser@example.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.id("login")).click();

        // Login olduktan sonra kullanıcı isminin göründüğünü doğrula
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        WebElement loggedInUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Test User']")));
        Assert.assertTrue(loggedInUser.isDisplayed(), "Login failed");
    }
}
