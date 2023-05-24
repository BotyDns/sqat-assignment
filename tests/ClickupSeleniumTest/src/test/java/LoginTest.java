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
import java.util.Properties;
import java.io.FileInputStream;

public class LoginTest extends ClickupTestBase {
    
    private LoginPage loginPage;

    @BeforeClass
    public static void setupClass() throws Exception {
        ClickupTestBase.setupFromConfig();
    }

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
        this.loginPage = LoginPage.connect(this.driver);
    }

    @Test
    public void testLogin() {
        DashboardPage dashboardPage = this.loginPage.login(this.username, this.password);
        Assert.assertTrue(dashboardPage.waitAndCheckPageLoad());
    }
    
    @After
    @Override
    public void close() {
        this.loginPage = null;
        super.close();
    }
}
