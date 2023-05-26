package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public WebDriver getDriver() {
        return driver;
    }

    public PageBase(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    protected WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    public WebDriver navigateBack() {
        this.driver.navigate().back();
        return this.driver;
    }

    public void waitUntilFullyLoaded() {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
    }

    abstract public boolean waitAndCheckPageLoad();
}
