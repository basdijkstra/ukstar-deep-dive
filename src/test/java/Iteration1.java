import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Iteration1 {

    @Test
    public void DoLoanRequest_UsingAmountsWithinLimits_ShouldBeAccepted() {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("http://parabank.parasoft.com/parabank");

        driver.findElement(By.name("username")).sendKeys("john");
        driver.findElement(By.name("password")).sendKeys("demo");
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        driver.findElement(By.linkText("Request Loan")).click();

        driver.findElement(By.id("amount")).sendKeys("1000");
        driver.findElement(By.id("downPayment")).sendKeys("100");
        Select fromAccountId = new Select(driver.findElement(By.id("fromAccountId")));
        fromAccountId.selectByVisibleText("13122");
        driver.findElement(By.xpath("//input[@value='Apply Now']")).click();

        /*
        try {
            Thread.sleep(2000);
        }
        catch(InterruptedException ie) {
            Assert.fail("INTERRUPT");
        }
        */

        String actualStatus = driver.findElement(By.id("loanStatus")).getText();

        Assert.assertEquals("Approved", actualStatus);

        driver.quit();
    }
}
