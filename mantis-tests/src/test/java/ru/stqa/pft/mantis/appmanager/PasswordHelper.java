package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class PasswordHelper extends HelperBase {

    public PasswordHelper(ApplicationManager app) {
        super(app);
    }

    public void startChanging(String username) {
        driver.get(app.getProperty("web.address") + "/manage_user_page.php");
        click(By.linkText(username));
        click(By.xpath("//input[@value='Reset Password']"));
    }

    public void finishChanging(String confirmationLink, String newPassword) {
        driver.get(confirmationLink);
        type(By.name("password"), newPassword);
        type(By.name("password_confirm"), newPassword);
        click(By.xpath("//button[@type='submit']")); }
}
