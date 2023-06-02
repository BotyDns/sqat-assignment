import org.junit.*;

import java.net.MalformedURLException;

import pages.*;
import util.PageConfig;

public class StaticPageTest extends ClickupTestBase {

    private PageConfig[] pages = {
            new PageConfig("https://clickup.com/", "One app to replace them all"),
            new PageConfig("https://clickup.com/onboarding", "How to Get Started"),
            new PageConfig("https://clickup.com/teams/marketing", "Marketing Project")
    };

    @Before
    @Override
    public void setup() throws MalformedURLException {
        super.setup();
    }

    @Test
    public void runStaticPageTests() {
        for (PageConfig conf : pages) {
            StaticPage page = StaticPage.connectTo(driver, conf.url);
            page.setExpectedTextInTitle(conf.expectedTextInTitle);
            Assert.assertTrue(
                    String.format("url: %s, expected text: %s, actual text: %s", conf.url, conf.expectedTextInTitle,
                            page.getTitle()),
                    page.waitAndCheckPageLoad());
        }
    }

    @After
    @Override
    public void close() {
        super.close();
    }
}
