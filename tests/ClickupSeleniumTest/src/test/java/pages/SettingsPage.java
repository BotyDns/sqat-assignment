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

public class SettingsPage extends PageBase {
    private By settingsTextBy = By.xpath("//nav[@data-test='workspace-settings-nav']//h2");

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        String text = this.waitAndReturnElement(this.settingsTextBy).getText();  
        return text.contains("Settings");
    }
}