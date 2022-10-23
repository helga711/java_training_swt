package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

public class ApplicationManager {
    private static WebDriver driver;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;
    private String browser;

    public ApplicationManager(String browserName) {
        this.browser = browserName;
    }

    public void init() {
        if (browser.equals(Browser.FIREFOX.browserName())) {
            driver = new FirefoxDriver();
        }
        else if (browser.equals(Browser.CHROME.browserName())) {
            driver = new ChromeDriver();
        }
        else if (browser.equals(Browser.EDGE.browserName())) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.addArguments("--no-sandbox");
            driver = new EdgeDriver(edgeOptions);
        }
        driver.get("http://localhost/addressbook/");
        getSessionHelper().login("admin", "secret");
    }

    public void stop() {
        driver.quit();
    }

    public GroupHelper getGroupHelper() {
        if (groupHelper == null) {
            groupHelper = new GroupHelper(driver);
        }
        return groupHelper;
    }

    public NavigationHelper getNavigationHelper() {
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

    public ContactHelper getContactHelper() {
        if (contactHelper == null) {
            contactHelper = new ContactHelper(driver);
        }
        return contactHelper;
    }
}
