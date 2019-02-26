import helpers.SeleniumHelpers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static io.restassured.RestAssured.given;

public class Iteration4 {

    private WebDriver driver;
    private SeleniumHelpers seleniumHelpers = new SeleniumHelpers();

    @Before
    public void initializeDatabase() {

        given().
        when().
            post("http://localhost:8080/parabank/services/bank/initializeDB").
        then().
            log().
            all();
    }

    @Before
    public void initializeBrowser() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted() {

        driver.get("http://localhost:8080/parabank");

        seleniumHelpers.sendKeys(driver, By.name("username"), "john");
        seleniumHelpers.sendKeys(driver, By.name("password"), "demo");
        seleniumHelpers.click(driver, By.xpath("//input[@value='Log In']"));

        seleniumHelpers.click(driver, By.linkText("Request Loan"));

        seleniumHelpers.sendKeys(driver, By.id("amount"), "1000");
        seleniumHelpers.sendKeys(driver, By.id("downPayment"), "100");
        seleniumHelpers.select(driver, By.id("fromAccountId"), "13122");
        seleniumHelpers.click(driver, By.xpath("//input[@value='Apply Now']"));

        String actualStatus = seleniumHelpers.getElementText(driver, By.id("loanStatus"));

        Assert.assertEquals("Approved", actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
