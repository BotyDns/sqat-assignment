import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.interactions.Actions;
import java.net.URL;
import java.net.MalformedURLException;


public class DashboardPage extends PageBase {
    private By calendarBy = By.xpath("//a[@data-test='data-view-item__view-id-body-Calendar']");
    private By sidebarButtonBy = By.xpath("//button[@data-test='collapsed-sidebar__toggle-icon-hover']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public static DashboardPage connect (WebDriver driver) {
        driver.get("https://app.clickup.com/");
        return new DashboardPage(driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        String text = this.waitAndReturnElement(this.calendarBy).getText();  
        return text.contains("Calendar");
    }

    public void hoverOverSidebarButton() {
        WebElement sidebarButton = this.waitAndReturnElement(this.sidebarButtonBy);

        Actions actions = new Actions(this.driver);
        actions.moveToElement(sidebarButton);
    }

    // public SettingsPage visitSettings() {
        
    // }
}