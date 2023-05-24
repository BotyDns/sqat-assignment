import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;

public class DashboardTest extends ClickupTestBase {

    private static Cookie idToken;
    private static Cookie refreshToken;

    private final static String idTokenKey = "id_token";
    private final static String refreshTokenKey = "refresh_token";
    private final static String domain = "app.clickup.com";

    @BeforeClass
    public static void setupClass() throws Exception {
        ClickupTestBase.setupFromConfig();
        LoginPage loginPage = LoginPage.connect(ClickupTestBase.createWebDriver());
        DashboardPage dashboardPage = loginPage.login(ClickupTestBase.username, ClickupTestBase.password);
        Assert.assertTrue(dashboardPage.waitAndCheckPageLoad());
        dashboardPage.waitUntilFullyLoaded();

        Map<String, String> cookies = ClickupTestBase.getLocalStorageCookies(dashboardPage.driver);

        if (cookies == null)
            throw new NullPointerException();

        DashboardTest.idToken = new Cookie(DashboardTest.idTokenKey, cookies.get(DashboardTest.idTokenKey), DashboardTest.domain, null, null, true, true);
        DashboardTest.refreshToken = new Cookie(DashboardTest.refreshTokenKey, cookies.get(DashboardTest.refreshTokenKey), DashboardTest.domain, null, null, true, true);

        dashboardPage.driver.quit();
    }

    private String createAddCookieScript(String key, String value) {
        return String.format("window.localStorage.%s = \"%s\";", key, value);
    }

    private void addCookies() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;
        
        jsExecutor
            .executeScript(createAddCookieScript(DashboardTest.idToken.getName(), DashboardTest.idToken.getValue()));
        jsExecutor
            .executeScript(createAddCookieScript(DashboardTest.refreshToken.getName(), DashboardTest.refreshToken.getValue()));
    }

    private DashboardPage connectWithCookies() {
        DashboardPage.connect(this.driver);
        addCookies();
        return DashboardPage.connect(this.driver);
    }

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
    }

    @Test
    public void testSettingsOpen() {
        DashboardPage dashboardPage = connectWithCookies();
        SettingsPage settingsPage =  dashboardPage.openSettings();

        Assert.assertTrue(settingsPage.waitAndCheckPageLoad());
    }
    
    @After
    @Override
    public void close() {
        super.close();
    }
}