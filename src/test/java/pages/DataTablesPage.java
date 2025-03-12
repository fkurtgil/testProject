package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DataTablesPage {
    WebDriver driver;

    public DataTablesPage(WebDriver driver) {
        this.driver = driver;
    }

    // Web element locators
    private By addButton = By.cssSelector("button.buttons-create");
    private By firstNameField = By.id("DTE_Field_first_name");
    private By lastNameField = By.id("DTE_Field_last_name");
    private By positionField = By.id("DTE_Field_position");
    private By officeField = By.id("DTE_Field_office");
    private By salaryField = By.id("DTE_Field_salary");
    private By createButton = By.xpath("//button[contains(text(),'Create')]");
    private By searchBox = By.cssSelector("input[type='search']");
    private By searchResult = By.cssSelector("table#example tbody tr td:nth-child(1)");

    // Actions
    public void clickAddButton() {
        driver.findElement(addButton).click();
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterPosition(String position) {
        driver.findElement(positionField).sendKeys(position);
    }

    public void enterOffice(String office) {
        driver.findElement(officeField).sendKeys(office);
    }

    public void enterSalary(String salary) {
        driver.findElement(salaryField).sendKeys(salary);
    }

    public void clickCreateButton() {
        driver.findElement(createButton).click();
    }

    public void searchRecord(String name) {
        driver.findElement(searchBox).sendKeys(name);
    }

    public String getSearchResult() {
        return driver.findElement(searchResult).getText();
    }
}
