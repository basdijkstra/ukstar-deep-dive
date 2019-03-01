import com.tngtech.java.junit.dataprovider.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import static io.restassured.RestAssured.given;

@RunWith(DataProviderRunner.class)
public class Iteration7 {

    private WebDriver driver;

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