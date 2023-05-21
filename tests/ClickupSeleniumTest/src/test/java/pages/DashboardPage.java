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


public class DashboardPage extends PageBase {
    private By dashboardTextBy = By.tagName("title");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        String text = this.waitAndReturnElement(this.dashboardTextBy).getText();
        System.out.println(text);     
        return text.contains("Workspace");
    }
}