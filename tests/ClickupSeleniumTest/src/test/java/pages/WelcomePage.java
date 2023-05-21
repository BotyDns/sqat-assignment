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

public class WelcomePage extends PageBase {

    private By loginButtonBy = By.xpath("//div[@class='CuNavigation_contactGroup__WpPNb navigation__auth']/a[@data-testid='cu-button']");
    private By welcomeMessageBy = By.xpath("//div[@data-testid='cu-title']/h1[@data-mutiny-root='true']")

    public WelcomePage(WebDriver driver) {
        super(driver);
        this.driver.get("https://clickup.com/");
    }

    @Override
    public boolean waitAndCheckPageLoad()
    {
        String welcomeText = this.waitAndReturnElement(this.welcomeMessageBy).getText();
        return welcomeText.contains("All teams. All work. One place.");
    }

    public LoginPage pressLoginButtonAndGoToLoginPage()
    {
        this.waitAndReturnElement(loginButtonBy).click();
        return new LoginPage(this.driver);
    }

}
