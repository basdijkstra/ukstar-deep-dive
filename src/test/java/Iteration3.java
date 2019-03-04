import helpers.SeleniumHelpers;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class Iteration3 {

    private WebDriver driver;
    private SeleniumHelpers seleniumHelpers = new SeleniumHelpers();

    @Before
    public void initializeBrowser() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted() {

        driver.get("http://parabank.parasoft.com/parabank");

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
