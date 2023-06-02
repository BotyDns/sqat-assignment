package pages;

import org.openqa.selenium.WebDriver;

public class StaticPage extends PageBase {

    private String expectedTextInTitle;

    public void setExpectedTextInTitle(String expected) {
        this.expectedTextInTitle = expected;
    }

    public StaticPage(WebDriver driver) {
        super(driver);
    }

    public static StaticPage connectTo(WebDriver driver, String url) {
        driver.get(url);
        return new StaticPage(driver);
    }

    public String getTitle() {
        this.waitUntilFullyLoaded();
        return this.driver.getTitle();
    }

    @Override
    public boolean waitAndCheckPageLoad() {
        return this.getTitle().contains(expectedTextInTitle);
    }
}
