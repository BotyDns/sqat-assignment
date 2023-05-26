import org.junit.*;

import java.net.MalformedURLException;

import pages.*;

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