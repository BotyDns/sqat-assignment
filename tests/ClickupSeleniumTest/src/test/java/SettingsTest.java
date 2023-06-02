import org.junit.*;

import java.net.MalformedURLException;

import pages.*;

public class SettingsTest extends ClickupTestBase {

    private SettingsPage settingsPage;

    @BeforeClass
    public static void setupClass() throws Exception {
        ClickupTestBase.setupFromConfig();
        ClickupTestBase.setupCookies();
    }

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
        this.settingsPage = connectWithCookies();
    }

    private SettingsPage connectWithCookies() {
        DashboardPage.connect(this.driver);
        addCookies();
        return DashboardPage.connect(this.driver).openSettings();
    }

    @Test
    public void testSelectingTimezoneRandomly() throws InterruptedException {
        String randomTimezone = settingsPage.selectTimezoneRandomly();
        Assert.assertTrue(settingsPage.timezonesMatch(randomTimezone));
    }

    @Test
    public void testDarkModeIsCorrectlySet() throws InterruptedException {
        boolean wasDarkmodeOn = settingsPage.isDarkModeOn();
        settingsPage.toggleDarkMode();
        settingsPage.sendForm();

        Assert.assertNotEquals(wasDarkmodeOn, settingsPage.isDarkModeOn());
    }

    @After
    @Override
    public void close() {
        super.close();
    }

}
