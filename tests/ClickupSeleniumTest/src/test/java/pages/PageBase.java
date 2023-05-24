import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public abstract class PageBase {

    protected WebDriver driver;
    protected WebDriverWait wait;

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
