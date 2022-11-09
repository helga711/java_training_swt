package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {

    static final String browser = Browser.CHROME.browserName();
    protected static final ApplicationManager app = new ApplicationManager(browser);

    @BeforeSuite
    public void setupClass() {
        app.setUpBrowsers();
        app.init();
    }

    @AfterSuite
    public void tearDown() {
        app.stop();
    }

}
