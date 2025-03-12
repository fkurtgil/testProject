import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCheckout {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test(priority = 1)
    public void checkoutTest() {
        driver.get("https://automationexercise.com");

        // Login işlemi
        driver.findElement(By.linkText("Signup / Login")).click();
        driver.findElement(By.name("email")).sendKeys("testuser@example.com");
        driver.findElement(By.name("password")).sendKeys("password123");
        driver.findElement(By.id("login")).click();

        // Sepet sayfasına git
        driver.findElement(By.xpath("//a[contains(text(), 'View Cart')]")).click();

        // Ödeme adımına ilerle
        driver.findElement(By.xpath("//button[contains(text(), 'Proceed to checkout')]")).click();
        driver.findElement(By.id("address1")).sendKeys("123 Test St");
        driver.findElement(By.id("submit")).click();

        // WebDriverWait'i Duration ile kullan
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Bu satırdaki hatayı düzelttik
        WebElement orderConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[text()='Order Confirmation']")));
        
        // Başarıyla ödeme adımına ilerlediğini doğrula
        Assert.assertTrue(orderConfirmation.isDisplayed(), "Checkout failed");
    }
}
