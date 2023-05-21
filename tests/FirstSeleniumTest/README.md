# Video

Docker compose: https://youtu.be/7LtPNJetTFA

Selenium testing: https://youtu.be/82_cVdEpVkM

Weekly task: https://youtu.be/xnZkZL6EVRA

# Selenium testing

We can test UI or webpage with Selenium tests.
Need to define the actions what the user should do to do different actions.

# Docker Compose

The Docker has a second level of organization. 
The composing. 
At that level we can initate multiple containers with different configurations.

https://docs.docker.com/compose/gettingstarted/

We have a prepared docker compose that contain all of the necessary tools that makes us avaiable to run selenium tests in docker.
That tool will setup a noVNC server, a chrome running in an image and an image that has gradle and java to be able to run the Selenium tests.

The Selenium Chrome image has a port open 4444, where we can connect to the chrome.

The noVNC server can be watched on the http://localhost:8081/ url after you started the compose.

You can start the composition of images with the following command: 

```
docker compose up
```

After all of the containers are running we can have a console where we have the gradle with:

```
docker exec -it selenium_testenv bash
```

# Gradle

We need to add new dependencies that makes us avaiable to use Selenium tests.
We will use chrome driver but you can find other drivers as well: https://mvnrepository.com/artifact/org.seleniumhq.selenium

Our new dependencies will be the following:
```
    testImplementation 'org.seleniumhq.selenium:selenium-java:2.52.0'
    testImplementation 'org.seleniumhq.selenium:selenium-chrome-driver:3.141.59'
    testImplementation('junit:junit:4.12'){
        exclude group: 'org.hamcrest'
    }
    testImplementation 'org.hamcrest:hamcrest-library:1.3'
    testImplementation "org.slf4j:slf4j-simple:1.7.9"
```

These dependencies loads everything that we will need for Selenium testing.

# Init and close the web driver

We have to initialize the chrome driver first. We connect to the `selenium` image 4444 port to run there our selenium tests.

```
    ChromeOptions options = new ChromeOptions();
    driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
```

We need also a variable to store the driver:
```
    private WebDriver driver;
```

https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/WebDriver.html


Next we maximize the window.

```
    this.driver.manage().window().maximize();
```

We put this into the setup function that runs before the tests.


```
    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        this.driver.manage().window().maximize();
    }
```

In the after phase we quit from the browser if it is initialized well (non-null).
```
    @After
    public void close() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }
```

We have to import the used libraries:

```
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;
```

# Create a basic test

As an entry point, we can ask the driver, that created in the `setup`, to open a webpage.

```
    @Test
    public void testSelenium() {
        this.driver.get("https://www.inf.elte.hu/en/");
    }
```

# HTML

https://developer.mozilla.org/en-US/docs/Learn/HTML/Introduction_to_HTML/Getting_started

# Check a text in the webpage


## Create a wait

In setup phase we create a `WebDriverWait` that handles us how long do we will wait for a page loading or an element appearance.
Not we set this timeout to 10 seconds, so it will throw a timeout exception if 10 second passes without fulfilling a condition.

In the setup:
```
this.wait = new WebDriverWait(driver, 10);
```

In the class we add a variable:
```
private WebDriverWait wait;
```

What condition?

```
this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
```

To use this we have to import ExpectedConditions:

```
import org.openqa.selenium.support.ui.*;
```

With this line, we ask our `WebDriverWait` to wait for appearing of an element.
This element will be denoted by tag name "body".
That means in normal words: please wait until the body of the website will appear or throw an exception if it is longer than 10 seconds.

## Get something

Now we know there is a body, so we can get it... and get the text of it.
We ask the driver to please return us the body and from that point the elemnet can give us the text of the whole body.

```
    WebElement resultElement = this.driver.findElement(By.tagName("body"));
    System.out.println(resultElement.getText());
```

By default the gradle won't show standard output on every version, so we may have to enable now.
Add into gradle the following:
```
test {
    testLogging.showStandardStreams = true
}
```

We can use multiple `By` functions, like `className`, `cssSelector`, `id​`, `name`, `linkText`, `partialLinkText​`, `tagName`.

For more info: https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/By.html

# Write nicer code

If we create a final variable in the class that defines the `By`, we do not have to repeat ourselves.
```
    private final By bodyLocator = By.tagName("body");
```

After this we can use shorter version:
```
    this.wait.until(ExpectedConditions.visibilityOfElementLocated(bodyLocator));
    WebElement resultElement = this.driver.findElement(bodyLocator);
```

# Do some interaction

```
    this.driver.get("https://www.inf.elte.hu/en/");

    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("footer-block")));
    WebElement resultElement = this.driver.findElement(By.className("footer-block"));
    System.out.println(resultElement.getText());
    Assert.assertTrue(resultElement.getText().contains("2021 ELTE Faculty of Informatics"));

    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("search-bar-toggler")));
    WebElement searchBarToggler = this.driver.findElement(By.className("search-bar-toggler"));
    searchBarToggler.click();

    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search")));
    WebElement searchBar = this.driver.findElement(By.name("search"));
    searchBar.sendKeys("Students\n");

    this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
    WebElement bodyAgain = this.driver.findElement(By.tagName("body"));
    System.out.println(bodyAgain.getText());
    Assert.assertTrue(bodyAgain.getText().contains("FOUND"));
    Assert.assertTrue(bodyAgain.getText().contains("Current Students"));
```

# Do it nicer

We can introduce a new function that waits for the visibility and after returns the element.

```
    private WebElement waitAndReturnElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }
```

With this function we can write shorter our test case.

```
    this.driver.get("https://www.inf.elte.hu/en/");

    WebElement resultElement = waitVisibiiltyAndFindElement(bodyLocator );
    System.out.println(resultElement.getText());
    Assert.assertTrue(resultElement.getText().contains("2021 ELTE Faculty of Informatics"));

    WebElement searchTogglerElement = waitVisibiiltyAndFindElement(searchTogglerLocator);
    searchTogglerElement.click();

    WebElement searchBarElement = waitVisibiiltyAndFindElement(searchLocator);
    searchBarElement.sendKeys("Student \n");

    WebElement bodyElement = waitVisibiiltyAndFindElement(bodyLocator);
    System.out.println(bodyElement.getText());
    Assert.assertTrue(bodyElement.getText().contains("Student Support Centre"));
```

# Task

- Open the http://the-internet.herokuapp.com/login page with selenium
- Check if you logged in correctly.
- Logout
- Try wrong credentials to login and check the login fail.

