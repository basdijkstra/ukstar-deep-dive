import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

import static io.restassured.RestAssured.given;

public class Iteration5 {

    private WebDriver driver;

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
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted() {

        new ParabankLoginPage(driver).
            loginAs("john","demo");

        new ParabankSideMenu(driver).
            selectMenuItem("Request Loan");

        String actualStatus =

            new ParabankLoanApplicationPage(driver).
                applyForLoanAndRetrieveResult("1000","100","13122");

        Assert.assertEquals("Approved", actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
