package ri.stqa.pft.mantis.appmanager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {
    private static WebDriver driver;
    private final Properties properties;
    private RegistrationHelper registrationHelper;
    private FtpHelper ftpHelper;
    private MailHelper mailHelper;
    private JamesHelper jamesHelper;
    private final String browser;

    public ApplicationManager(String browserName) {
        this.browser = browserName;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(String.format("src/test/resources/%s.properties", target)));
    }

    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    public void goToBaseURL() {
        driver.get(properties.getProperty("web.address"));
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public FtpHelper ftp() {
        if (ftpHelper == null) {
            ftpHelper = new FtpHelper(this);
        }
        return ftpHelper;
    }

    public MailHelper mail() {
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }

    public JamesHelper james() {
        if (jamesHelper == null) {
            jamesHelper = new JamesHelper(this);
        }
        return jamesHelper;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            if (browser.equals(Browser.FIREFOX.browserName())) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            else if (browser.equals(Browser.CHROME.browserName())) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            else if (browser.equals(Browser.EDGE.browserName())) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--no-sandbox");
                driver = new EdgeDriver(edgeOptions);
            }
            driver.manage().timeouts().implicitlyWait(Duration.ZERO);
            goToBaseURL();
        }
        return driver;
    }
}
