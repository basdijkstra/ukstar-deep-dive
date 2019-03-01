import helpers.SeleniumHelpers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ParabankLoanApplicationPage;
import pages.ParabankLoginPage;
import pages.ParabankSideMenu;

import static io.restassured.RestAssured.given;

public class Iteration5 {

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

        new ParabankLoginPage(driver).
            setUsername("john").
            setPassword("demo").
            doLogin();

        new ParabankSideMenu(driver).
            selectMenuItemByVisibleText("Request Loan");

        String actualStatus =

        new ParabankLoanApplicationPage(driver).
            setLoanAmounnt("1000").
            setDownPayment("100").
            selectFromAccount("13122").
            applyForLoan().
            getApplicationResult();

        Assert.assertEquals("Approved", actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
