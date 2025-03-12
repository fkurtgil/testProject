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
public class WebTablesTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Web Table'a Yeni Kayıt Ekleme, Güncelleme ve Silme")
    public void webTableCRUDTest() {
        driver.get("https://demoqa.com/webtables");

        // "Add" düğmesine bas
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='addNewRecordButton']")));
        addButton.click();

        // Yeni kayıt ekleme
        WebElement firstNameInput = driver.findElement(By.xpath("//input[@id='firstName']"));
        WebElement lastNameInput = driver.findElement(By.xpath("//input[@id='lastName']"));
        WebElement emailInput = driver.findElement(By.xpath("//input[@id='userEmail']"));
        WebElement ageInput = driver.findElement(By.xpath("//input[@id='age']"));
        WebElement salaryInput = driver.findElement(By.xpath("//input[@id='salary']"));
        WebElement departmentInput = driver.findElement(By.xpath("//input[@id='department']"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@id='submit']"));

        firstNameInput.sendKeys("Ali");
        lastNameInput.sendKeys("Veli");
        emailInput.sendKeys("ali.veli@example.com");
        ageInput.sendKeys("30");
        salaryInput.sendKeys("5000");
        departmentInput.sendKeys("IT");
        submitButton.click();

        // Eklenen kaydın doğrulanması
        WebElement addedRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rt-tbody']//div[text()='Ali']")));
        assertNotNull(addedRow, "Yeni kayıt eklenmedi!");

        // Kaydı düzenleme
        WebElement editButton = driver.findElement(By.xpath("//span[@title='Edit']"));
        editButton.click();

        WebElement departmentEditInput = driver.findElement(By.xpath("//input[@id='department']"));
        departmentEditInput.clear();
        departmentEditInput.sendKeys("HR");
        submitButton.click();

        // Düzenlenen kaydın doğrulanması
        WebElement updatedDepartment = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='rt-tbody']//div[text()='HR']")));
        assertNotNull(updatedDepartment, "Kayıt güncellenmedi!");

        // Kaydı silme
        WebElement deleteButton = driver.findElement(By.xpath("//span[@title='Delete']"));
        deleteButton.click();

        // Silinen kaydın doğrulanması
        boolean isRecordDeleted = driver.findElements(By.xpath("//div[@class='rt-tbody']//div[text()='Ali']")).isEmpty();
        assertTrue(isRecordDeleted, "Kayıt silinmedi!");
    }
}
