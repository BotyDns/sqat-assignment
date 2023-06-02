import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.*;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.FileInputStream;

import pages.*;

public class ClickupTestBase {
    public WebDriver driver;

    protected static String username;
    protected static String password;
    protected static Cookie idToken;
    protected static Cookie refreshToken;

    protected final static String TEST_CONFIG_PATH_VAR = "TEST_PROPERTIES_PATH";

    protected final static String idTokenKey = "id_token";
    protected final static String refreshTokenKey = "refresh_token";
    protected final static String domain = "app.clickup.com";

    public static WebDriver createWebDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT_AND_NOTIFY);
        WebDriver wd = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        wd.manage().window().maximize();
        return wd;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, String> getLocalStorageCookies(WebDriver wd) {
        Object obj = ((JavascriptExecutor) wd).executeScript("return window.localStorage;");

        Map<String, String> cookies = null;
        if (obj instanceof Map)
            cookies = (Map<String, String>) obj;

        return cookies;
    }

    protected void setup() throws MalformedURLException {
        driver = ClickupTestBase.createWebDriver();
    }

    protected void close() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected static void setupFromConfig() throws Exception {
        String configPath = System.getenv(TEST_CONFIG_PATH_VAR);

        Properties props = new Properties();
        try (FileInputStream fs = new FileInputStream(configPath)) {
            props.load(fs);
        } catch (Exception e) {
            throw e;
        }

        LoginTest.username = props.getProperty("username");
        LoginTest.password = props.getProperty("password");
    }

    protected static void setupCookies() throws Exception {
        LoginPage loginPage = LoginPage.connect(ClickupTestBase.createWebDriver());
        DashboardPage dashboardPage = loginPage.login(ClickupTestBase.username, ClickupTestBase.password);
        Assert.assertTrue(dashboardPage.waitAndCheckPageLoad());
        dashboardPage.waitUntilFullyLoaded();

        Map<String, String> cookies = ClickupTestBase.getLocalStorageCookies(dashboardPage.getDriver());

        if (cookies == null)
            throw new NullPointerException();

        ClickupTestBase.idToken = new Cookie(ClickupTestBase.idTokenKey, cookies.get(ClickupTestBase.idTokenKey),
                ClickupTestBase.domain, null, null, true, true);
        ClickupTestBase.refreshToken = new Cookie(ClickupTestBase.refreshTokenKey,
                cookies.get(ClickupTestBase.refreshTokenKey), ClickupTestBase.domain, null, null, true, true);

        dashboardPage.getDriver().quit();
    }

    protected void addCookies() {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) this.driver;

        jsExecutor
                .executeScript(
                        createAddCookieScript(DashboardTest.idToken.getName(), DashboardTest.idToken.getValue()));
        jsExecutor
                .executeScript(createAddCookieScript(DashboardTest.refreshToken.getName(),
                        DashboardTest.refreshToken.getValue()));
    }

    private String createAddCookieScript(String key, String value) {
        return String.format("window.localStorage.%s = \"%s\";", key, value);
    }
}