import org.junit.*;

import java.net.MalformedURLException;

import pages.*;

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
        DashboardPage dashboardPage = this.loginPage.login(LoginTest.username, LoginTest.password);
        Assert.assertTrue(dashboardPage.waitAndCheckPageLoad());
    }

    @After
    @Override
    public void close() {
        this.loginPage = null;
        super.close();
    }
}
