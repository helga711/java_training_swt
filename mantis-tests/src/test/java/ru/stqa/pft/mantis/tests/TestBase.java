package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.Browser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    static final String browser = System.getProperty("browser", Browser.CHROME.browserName());
    protected static final ApplicationManager app = new ApplicationManager(browser);

    @BeforeSuite
    public void setupClass() throws IOException {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p)
    {
        logger.info(String.format("Start %s with parameters %s", m.getName(), Arrays.asList(p)));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m)
    {
        logger.info(String.format("Stop %s", m.getName()));
    }
}
