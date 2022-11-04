package ru.stqa.pft.addressbook.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {

    static final String browser = Browser.CHROME.browserName();
    protected final ApplicationManager app = new ApplicationManager(browser);

    @BeforeSuite
    static void setupClass() {
        if (browser.equals(Browser.FIREFOX.browserName())) {
            WebDriverManager.firefoxdriver().setup();
        }
        else if (browser.equals(Browser.CHROME.browserName())) {
            WebDriverManager.chromedriver().setup();
        }
        else if (browser.equals(Browser.EDGE.browserName())) {
            WebDriverManager.edgedriver().setup();
        }
    }

    @BeforeMethod
    public void setUp() {
        app.init();
    }

    @AfterMethod
    public void tearDown() {
        app.stop();
    }

}
