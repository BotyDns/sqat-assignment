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

import java.net.MalformedURLException;

public class LogoutTest extends ClickupTestBase {
    
    @BeforeClass
    public static void setupClass() throws Exception {
        ClickupTestBase.setupFromConfig();
        ClickupTestBase.setupCookies();
    }
    
    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
    }

    private DashboardPage connectWithCookies() {
        DashboardPage.connect(this.driver);
        addCookies();
        return DashboardPage.connect(this.driver);
    }

    @Test
    public void testLogout() {
        DashboardPage dashboardPage = connectWithCookies();
        LoginPage loginPage = dashboardPage.logout();

        Assert.assertTrue(loginPage.waitAndCheckPageLoad());
    }

    @After
    @Override
    public void close() {
        super.close();
    }
}