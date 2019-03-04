package pages;

import helpers.SeleniumHelpers;
import org.openqa.selenium.*;

public class ParabankSideMenu {

    private WebDriver _driver;
    private SeleniumHelpers seleniumHelpers = new SeleniumHelpers();

    public ParabankSideMenu(WebDriver driver) {

        _driver = driver;
    }

    public void selectMenuItem(String linkText) {

        seleniumHelpers.click(_driver, By.linkText(linkText));
    }
}
