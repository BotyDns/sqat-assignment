import org.junit.*;

import java.net.MalformedURLException;

import pages.*;

public class WelcomePageTest extends ClickupTestBase {

    private WelcomePage welcomePage;

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
        this.welcomePage = WelcomePage.connect(this.driver);
    }

    @Test
    public void testWelcomePageLoads() {
        Assert.assertTrue(this.welcomePage.waitAndCheckPageLoad());
    }

    @Test
    public void testPressLoginButtonAndGoToLoginPage() {
        LoginPage loginPage = this.welcomePage.pressLoginButtonAndGoToLoginPage();
        Assert.assertTrue(loginPage.waitAndCheckPageLoad());
    }

    @After
    @Override
    public void close() {
        this.welcomePage = null;
        super.close();
    }
}
