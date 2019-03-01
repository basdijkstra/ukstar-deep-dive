import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import helpers.SeleniumHelpers;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ParabankLoanApplicationPage;
import pages.ParabankLoginPage;
import pages.ParabankSideMenu;

import static io.restassured.RestAssured.given;

@RunWith(DataProviderRunner.class)
public class Iteration6 {

    private WebDriver driver;
    private SeleniumHelpers seleniumHelpers = new SeleniumHelpers();

    @DataProvider
    public static Object[][] createTestDataObject() {
        return new Object[][] {
            {"1000", "100", "13122", "Approved"},
            {"500", "50", "13122", "Approved"},
            {"10000", "100", "13122", "Denied"}
        };
    }

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
    @UseDataProvider("createTestDataObject")
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted
        (String loanAmount, String downPayment, String fromAccountId, String expectedStatus) {

        new ParabankLoginPage(driver).
            setUsername("john").
            setPassword("demo").
            doLogin();

        new ParabankSideMenu(driver).
            selectMenuItemByVisibleText("Request Loan");

        String actualStatus =

        new ParabankLoanApplicationPage(driver).
            setLoanAmount(loanAmount).
            setDownPayment(downPayment).
            selectFromAccount(fromAccountId).
            applyForLoan().
            getApplicationResult();

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
