import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Iteration2 {

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

    @Test
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted() {

        driver.get("http://localhost:8080/parabank");

        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        driver.findElement(By.linkText("Request Loan")).click();

        driver.findElement(By.id("amount")).sendKeys("1000");
        driver.findElement(By.id("downPayment")).sendKeys("100");
        Select fromAccountId = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccountId.selectByVisibleText("13122");
        driver.findElement(By.xpath("//input[@value='Apply Now']")).click();

        String actualStatus = driver.findElement(By.id("loanStatus")).getText();

        Assert.assertEquals("Approved", actualStatus);
    }

    @After
    public void tearDown() {

        driver.quit();
    }
}
