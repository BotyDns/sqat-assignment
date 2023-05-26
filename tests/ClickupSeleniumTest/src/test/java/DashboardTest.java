import org.junit.*;

import java.net.MalformedURLException;

import pages.*;

public class DashboardTest extends ClickupTestBase {

    @BeforeClass
    public static void setupClass() throws Exception {
        ClickupTestBase.setupFromConfig();
        ClickupTestBase.setupCookies();
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
        SettingsPage settingsPage = dashboardPage.openSettings();

        Assert.assertTrue(settingsPage.waitAndCheckPageLoad());
    }

    @After
    @Override
    public void close() {
        super.close();
    }
}