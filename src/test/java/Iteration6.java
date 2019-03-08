import com.tngtech.java.junit.dataprovider.*;
import io.github.bonigarcia.wdm.WebDriverManager;
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

    @BeforeClass
    public static void manageBrowserDriver() {

        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void initializeBrowser() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
