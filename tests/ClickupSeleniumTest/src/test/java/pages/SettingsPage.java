package pages;

import org.openqa.selenium.*;

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