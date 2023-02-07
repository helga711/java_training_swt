package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {
    public SessionHelper(ApplicationManager app) {
        super(app);
    }

    public void login(String userName, String password) {
        driver.get(app.getProperty("web.address") + "/login_page.php");
        type(By.name("username"), userName);
        click(By.xpath("//input[@type='submit']"));
        type(By.name("password"), password);
        click(By.xpath("//input[@type='submit']"));
    }

    public void loginByAdmin() {
        login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));
    }
}
