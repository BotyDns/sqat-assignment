import org.junit.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.io.FileInputStream;

public class ClickupTestBase {
    public WebDriver driver;
    
    protected static String username;
    protected static String password;
    
    protected final static String TEST_CONFIG_PATH_VAR = "TEST_PROPERTIES_PATH";

    public static WebDriver createWebDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        WebDriver wd = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        wd.manage().window().maximize();
        return wd;
    }

    public static Map<String, String> getLocalStorageCookies(WebDriver wd) {
        Object obj = ((JavascriptExecutor) wd).executeScript("return window.localStorage;");
        
        Map<String, String> cookies = null;
        if (obj instanceof Map)
            cookies = (Map<String, String>) obj;

        return cookies;
    }

    protected void setup() throws MalformedURLException  {
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
}