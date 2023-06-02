package util;

public class PageConfig {
    public String url;
    public String expectedTextInTitle;

    public PageConfig(String url, String expectedTextInTitle) {
        this.url = url;
        this.expectedTextInTitle = expectedTextInTitle;
    }
}
