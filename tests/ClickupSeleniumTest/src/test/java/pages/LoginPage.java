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

public class LoginPage extends PageBase {

    private By emailInputBy = By.id("login-email-input");
    private By passwordInputBy = By.id("login-password-input");
    private By submitButtonBy = By.xpath("//form[@data-form-type='login']/button[@class='login-page-new__main-form-button']");
    private By dontHaveAnAccountTextBy = By.className("login-page-new__main-bot-text");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public static LoginPage connect(WebDriver driver) {
        driver.get("https://app.clickup.com/login");
        return new LoginPage(driver);
    }

    public DashboardPage login(String email, String password) {
        this.waitAndReturnElement(this.emailInputBy).sendKeys(email);
        this.waitAndReturnElement(this.passwordInputBy).sendKeys(password);
        this.waitAndReturnElement(this.submitButtonBy).click();

        return new DashboardPage(this.driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        String text = this.waitAndReturnElement(this.dontHaveAnAccountTextBy).getText();        
        return text.contains("Don't have an account? Sign up");
    }

}
