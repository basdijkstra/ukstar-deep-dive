import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.ParabankLoanApplicationPage;
import pages.ParabankLoginPage;
import pages.ParabankSideMenu;

import static io.restassured.RestAssured.given;

public class Iteration4 {

    private WebDriver driver;

    @Before
    public void initializeBrowser() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
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
