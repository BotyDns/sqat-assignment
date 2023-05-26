package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class DashboardPage extends PageBase {
    private By calendarBy = By.xpath("//a[@data-test='data-view-item__view-id-body-Calendar']");
    private By sidebarButtonBy = By.xpath("//button[@data-test='collapsed-sidebar__toggle-icon-hover']");
    private By profileToggleButtonBy = By.xpath("//div[@data-test='user-settings-dropdown-toggle']");
    private By settingsButtonBy = By.xpath("//a[@data-test='user-settings-menu-item-my-settings']");
    private By logoutButtonBy = By.xpath("//a[@data-test='user-settings-menu-item-log-out']");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public static DashboardPage connect(WebDriver driver) {
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

    public void toggleProfile() {
        WebElement profileButton = this.waitAndReturnElement(this.profileToggleButtonBy);
        profileButton.click();
    }

    public SettingsPage openSettings() {
        toggleProfile();
        WebElement settingsButton = this.waitAndReturnElement(this.settingsButtonBy);
        settingsButton.click();
        return new SettingsPage(this.driver);
    }

    public LoginPage logout() {
        toggleProfile();
        WebElement logoutButton = this.waitAndReturnElement(this.logoutButtonBy);
        logoutButton.click();
        return new LoginPage(this.driver);
    }
}