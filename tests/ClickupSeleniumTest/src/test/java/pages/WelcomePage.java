import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

public class WelcomePage extends PageBase {

    private By loginButtonBy = By.xpath("//div[@class='CuNavigation_contactGroup__WpPNb navigation__auth']/a[@data-testid='cu-button']");
    private By loginBannerBy = By.xpath("//div[@data-testid='cu-title']");

    public WelcomePage(WebDriver driver) {
        super(driver);
    }

    public static WelcomePage connect(WebDriver driver) {
        driver.get("https://clickup.com/");
        return new WelcomePage(driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        this.waitAndReturnElement(this.loginBannerBy);
        return this.driver.getTitle().contains("One app to replace them all");
    }

    public LoginPage pressLoginButtonAndGoToLoginPage() {
        this.waitAndReturnElement(this.loginButtonBy).click();
        return new LoginPage(this.driver);
    }

}
