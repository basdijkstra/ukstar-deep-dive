package helpers;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class SeleniumHelpers {

    public SeleniumHelpers() {
    }

    public void click(WebDriver driver, By by) {

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();
        }
        catch (TimeoutException | NoSuchElementException ex) {
            System.out.println("Error in click(): Element " + by.toString() + " could not be found\n"  + ex.getMessage());
            Assert.fail();
        }
    }

    public void sendKeys(WebDriver driver, By by, String textToSend) {

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(textToSend);
        }
        catch (TimeoutException | NoSuchElementException ex) {
            System.out.println("Error in sendKeys(): Element " + by.toString() + " could not be found\n" + ex.getMessage());
            Assert.fail();
        }
    }

    public void select(WebDriver driver, By by, String valueToBeSelected) {

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(by));
            new Select(driver.findElement(by)).selectByVisibleText(valueToBeSelected);
        }
        catch (TimeoutException | NoSuchElementException ex) {
            System.out.println("Error occurred when trying to select '" + valueToBeSelected + "' from dropdown " + by.toString() + "\n" + ex.getMessage());
            Assert.fail();
        }
    }

    public String getElementText(WebDriver driver, By by) {

        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(by));
        }
        catch (TimeoutException te)
        {
            System.out.println("Error in getElementText(): Element " + by.toString() + " could not be found\n" + te.getMessage());
            return "";
        }
        return driver.findElement(by).getText();
    }
}
