import com.tngtech.java.junit.dataprovider.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import static io.restassured.RestAssured.given;

@RunWith(DataProviderRunner.class)
public class Iteration6 {

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
            post("http://parabank.parasoft.com/parabank/services/bank/initializeDB").
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
    public void DoLoanRequest_UsingVariousAmounts_ShouldBeAcceptedOrDeniedAsExpected
        (String loanAmount, String downPayment, String fromAccountId, String expectedStatus) {

        new ParabankLoginPage(driver).
            loginAs("john","demo");

        new ParabankSideMenu(driver).
            selectMenuItem("Request Loan");

        String actualStatus =

            new ParabankLoanApplicationPage(driver).
                applyForLoanAndRetrieveResult(loanAmount,downPayment,fromAccountId);

        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
