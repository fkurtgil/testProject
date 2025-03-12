package com.selenium.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void logout() {
        // Başarılı girişlerde logout yap
        try {
            WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("react-burger-menu-btn")));
            menuButton.click();
            WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
            logoutButton.click();
        } catch (Exception e) {
            System.out.println("Logout işlemi yapılmadı, kullanıcı hatalı olabilir.");
        }
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        WebElement userField = driver.findElement(By.id("user-name"));
        WebElement passField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        userField.clear();
        passField.clear();

        userField.sendKeys(username);
        passField.sendKeys(password);
        loginButton.click();
    }

    @Test
    @DisplayName("Geçerli kullanıcılarla giriş yap")
    public void validUsersLoginTest() {
        String[][] validUsers = {
            {"standard_user", "secret_sauce"},
            {"problem_user", "secret_sauce"},
            {"performance_glitch_user", "secret_sauce"}
        };

        for (String[] user : validUsers) {
            login(user[0], user[1]);
            WebElement inventory = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));
            assertNotNull(inventory, user[0] + " ile giriş başarısız!");
        }
    }

    @Test
    @DisplayName("Hatalı kullanıcılarla giriş testi")
    public void invalidUsersLoginTest() {
        String[][] invalidUsers = {
            {"locked_out_user", "secret_sauce", "Sorry, this user has been locked out."},
            {"invalid_user", "wrong_pass", "Epic sadface: Username and password do not match any user in this service"}
        };

        for (String[] user : invalidUsers) {
            login(user[0], user[1]);
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")));
            assertTrue(errorMessage.getText().contains(user[2]), "Hata mesajı beklenen gibi değil!");
        }
    }
}
