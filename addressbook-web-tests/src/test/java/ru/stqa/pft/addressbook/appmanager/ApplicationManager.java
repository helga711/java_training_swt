package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ApplicationManager {
    private static WebDriver driver;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private SessionHelper sessionHelper;
    private ContactHelper contactHelper;

    public void init() {
        driver = new FirefoxDriver();
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
