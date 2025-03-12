package stepdefinitions;

import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.DataTablesPage;

import java.time.Duration;

public class DataTablesSteps {
    WebDriver driver;
    DataTablesPage dataTablesPage;
    String testFirstName = "Ali";
    String testLastName = "Veli";

    @Given("Kullanıcı {string} adresine gider")
    public void kullanıcı_adresine_gider(String url) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(url);
        dataTablesPage = new DataTablesPage(driver);
    }

    @When("Kullanıcı yeni bir kayıt ekler")
    public void kullanıcı_yeni_bir_kayıt_ekler() {
        dataTablesPage.clickAddButton();
        dataTablesPage.enterFirstName(testFirstName);
        dataTablesPage.enterLastName(testLastName);
        dataTablesPage.enterPosition("Software Engineer");
        dataTablesPage.enterOffice("Istanbul");
        dataTablesPage.enterSalary("5000");
        dataTablesPage.clickCreateButton();
    }

    @And("Kullanıcı eklenen kaydı arar")
    public void kullanıcı_eklenen_kaydı_arar() {
        dataTablesPage.searchRecord(testFirstName);
    }

    @Then("Kullanıcı aradığı kaydın listede olduğunu doğrular")
    public void kullanıcı_aradığı_kaydın_listede_olduğunu_doğrular() {
        String actualName = dataTablesPage.getSearchResult();
        Assertions.assertEquals(testFirstName, actualName, "Kayıt bulunamadı!");
        driver.quit();
    }
}
