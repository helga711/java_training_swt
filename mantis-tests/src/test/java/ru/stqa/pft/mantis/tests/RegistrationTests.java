package ru.stqa.pft.mantis.tests;

import org.testng.annotations.*;
import ru.stqa.pft.mantis.model.User;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends MailTestBase {

    @Test
    public void testRegistration() throws Exception {
        skipIfNotFixed(1);
        long now = System.currentTimeMillis();
        User user = new User()
                .withUsername("user" + now)
                .withMantisPassword("password")
                .withEmail(String.format("user%s@localhost", now));
        register(user);
        assertTrue(app.newSession().login(user.getUsername(), user.getMantisPassword()));
    }
}
