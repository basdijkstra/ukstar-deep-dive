package pages;

import helpers.SeleniumHelpers;
import org.openqa.selenium.*;

public class ParabankLoanApplicationPage {

    private WebDriver _driver;
    private SeleniumHelpers seleniumHelpers = new SeleniumHelpers();

    private By textfieldLoanAmount = By.id("amount");
    private By textfieldDownPayment = By.id("downPayment");
    private By dropdownFromAccount = By.id("fromAccountId");
    private By buttonApplyNow = By.xpath("//input[@value='Apply Now']");
    private By textfieldApplicationResult = By.id("loanStatus");

    public ParabankLoanApplicationPage(WebDriver driver) {

        _driver = driver;
    }

    public ParabankLoanApplicationPage setLoanAmounnt(String loanAmount) {

        seleniumHelpers.sendKeys(_driver, textfieldLoanAmount, loanAmount);
        return this;
    }

    public ParabankLoanApplicationPage setDownPayment(String downPayment) {

        seleniumHelpers.sendKeys(_driver, textfieldDownPayment, downPayment);
        return this;
    }

    public ParabankLoanApplicationPage selectFromAccount(String fromAccount) {

        seleniumHelpers.select(_driver, dropdownFromAccount, fromAccount);
        return this;
    }

    public ParabankLoanApplicationPage applyForLoan() {

        seleniumHelpers.click(_driver, buttonApplyNow);
        return this;
    }

    public String getApplicationResult() {

        return seleniumHelpers.getElementText(_driver, textfieldApplicationResult);
    }
}
