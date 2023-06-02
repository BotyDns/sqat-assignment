package pages;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SettingsPage extends PageBase {
    private By settingsTextBy = By.xpath("//nav[@data-test='workspace-settings-nav']//h2");
    private By timezoneDropdownBy = By
            .xpath("//div[@id='timezone']/div[2]//div[@class='cu-dropdown__toggle']");
    private By openedTimezoneDropdownBy = By.xpath("//div[@data-test='profile-settings__timezone-dropdown']");
    private By dropdownElementsBy = By.xpath(
            "//div[@data-test='profile-settings__timezone-dropdown']/div[2]/div[@class='cu-user-settings__dropdown-options-item ng-star-inserted']");
    private By submitButtonBy = By.xpath("//button[@data-test='profile-settings__btn_fw']");

    private By bodyBy = By.tagName("body");

    private String darkmodeCheckboxString = "//input[@formcontrolname='darkTheme']";

    private int waitTimeMs = 3000;

    public SettingsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        String text = this.waitAndReturnElement(this.settingsTextBy).getText();
        return text.contains("Settings");
    }

    private List<WebElement> openAndListTimezoneDropdown() {
        WebElement timezoneDropdown = this.waitAndReturnElement(this.timezoneDropdownBy);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].click()", timezoneDropdown);

        WebElement openedTimezoneDropdown = this.waitAndReturnElement(this.openedTimezoneDropdownBy);
        return openedTimezoneDropdown.findElements(this.dropdownElementsBy);
    }

    public String selectTimezoneRandomly() throws InterruptedException {
        List<WebElement> timezones = openAndListTimezoneDropdown();
        this.extraWait();
        Random rand = new Random();
        WebElement randomTimezone = timezones.get(rand.nextInt(timezones.size()));
        String timezoneIdentifier = randomTimezone.getText();
        randomTimezone.click();

        return timezoneIdentifier;
    }

    public void sendForm() {
        WebElement submitButton = this.waitAndReturnElement(this.submitButtonBy);
        submitButton.click();
    }

    public void toggleDarkMode() throws InterruptedException {
        this.waitUntilFullyLoaded();
        this.extraWait();
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

        jsExecutor.executeScript(createClickScript(darkmodeCheckboxString));
    }

    public boolean isDarkModeOn() {
        WebElement body = this.waitAndReturnElement(bodyBy);
        return body.getAttribute("class").contains("dark-theme");
    }

    public WebElement getCurrentTimezone() {
        return this.waitAndReturnElement(this.timezoneDropdownBy);
    }

    public boolean timezonesMatch(String timezone) {
        return timezone.contains(this.getCurrentTimezone().getText());
    }

    private String createClickScript(String xpath) {
        return String.format(
                "document.evaluate(\"%s\", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue.click();",
                xpath);
    }

    private void extraWait() throws InterruptedException {
        synchronized (this.wait) {
            this.wait.wait(waitTimeMs);
        }
    }
}