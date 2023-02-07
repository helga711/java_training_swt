package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.User;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends MailTestBase {

    @BeforeMethod
    public void ensurePreconditions() throws Exception {
        if (app.db().simpleUsers().size() == 0) {
            long now = System.currentTimeMillis();
            User user = new User()
                    .withUsername("user" + now)
                    .withMantisPassword("password")
                    .withEmail(String.format("user%s@localhost.localdomain", now));
            register(user);
            app.mailServer().drainEmail(user.getUsername(), user.getMailPassword());
        }
    }

    @Test
    public void testPasswordChanging() throws Exception {
        User user = app.db().simpleUsers().iterator().next()
                .withMantisPassword("password" + System.currentTimeMillis());
        app.uisession().loginByAdmin();
        app.password().startChanging(user.getUsername());
        List<MailMessage> mailMessages = app.mailServer().waitForMail(user.getUsername(), user.getMailPassword(), 1, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.password().finishChanging(confirmationLink, user.getMantisPassword());
        assertTrue(app.newSession().login(user.getUsername(), user.getMantisPassword()), String.format("User %s cannot login with password '%s'.", user.getUsername(), user.getMantisPassword()));
    }
}
