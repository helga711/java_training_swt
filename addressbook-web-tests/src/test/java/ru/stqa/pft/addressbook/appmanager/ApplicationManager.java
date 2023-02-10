package ru.stqa.pft.addressbook.appmanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    private static WebDriver driver;
    private final Properties properties;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private DBHelper dbHelper;
    private final String browser;

    public ApplicationManager(String browserName) {
        this.browser = browserName;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));

        if ("".equals(properties.getProperty("selenium.server"))) {
            if (browser.equals(Browser.FIREFOX.browserName())) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browser.equals(Browser.CHROME.browserName())) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browser.equals(Browser.EDGE.browserName())) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--no-sandbox");
                driver = new EdgeDriver(edgeOptions);
            }
        }
        else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "Windows 10")));
            URL url = new URL(properties.getProperty("selenium.server"));
            if (browser.equals(Browser.FIREFOX.browserName())) {
                WebDriverManager.firefoxdriver().remoteAddress(url).setup();
            } else if (browser.equals(Browser.CHROME.browserName())) {
                WebDriverManager.chromedriver().remoteAddress(url).setup();
            } else if (browser.equals(Browser.EDGE.browserName())) {
                WebDriverManager.edgedriver().remoteAddress(url).setup();
            }

            driver = new RemoteWebDriver(url, capabilities);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        goToBaseURL();
        getSessionHelper().login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPassword"));
    }

    public void stop() {
        driver.quit();
    }

    public void goToBaseURL() {
        driver.get(properties.getProperty("web.address"));
    }

    public GroupHelper group() {
        if (groupHelper == null) {
            groupHelper = new GroupHelper(driver);
        }
        return groupHelper;
    }

    public NavigationHelper goTo() {
        if (navigationHelper == null) {
            navigationHelper = new NavigationHelper(driver);
        }
        return navigationHelper;
    }

    public SessionHelper getSessionHelper() {
        if (sessionHelper == null) {
            sessionHelper = new SessionHelper(driver);
        }
        return sessionHelper;
    }

    public ContactHelper contact() {
        if (contactHelper == null) {
            contactHelper = new ContactHelper(driver);
        }
        return contactHelper;
    }

    public DBHelper db() {
        if (dbHelper == null) {
            dbHelper = new DBHelper();
        }
        return dbHelper;
    }
}
