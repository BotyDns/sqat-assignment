import org.junit.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.remote.RemoteWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import java.util.*;  

import java.net.URL;
import java.net.MalformedURLException;

public class WelcomePageTest extends ClickupTestBase {
    
    private WelcomePage welcomePage;

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
        this.welcomePage = new WelcomePage(this.driver);
    }

    @Test
    public void testWelcomePageLoads() {
        Assert.assertTrue(this.welcomePage.waitAndCheckPageLoad());
    }
    
    @Test
    public void testPressLoginButtonAndGoToLoginPage() {

    }

    @After
    @Override
    public void close() {
        this.welcomePage = null;
        super.close();
    }
}
