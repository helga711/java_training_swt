package ri.stqa.pft.mantis.tests;

import org.testng.annotations.*;
import ri.stqa.pft.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

    private Boolean inBuildMailServer;

    @BeforeMethod
    public void startMailServer(){
        inBuildMailServer = Boolean.valueOf(System.getProperty("inBuildMailServer", "true"));
        if (inBuildMailServer) {
            app.mail().start();
        }
    }

    @Test
    public void testRegistration() throws Exception {
        long now = System.currentTimeMillis();
        String email = String.format("user%s@localhost.localdomain", now);
        String username = "user" + now;
        String password = "password";
        if (!inBuildMailServer){
            app.james().createUser(username, password);
        }
        app.registration().start(username, email);
        List<MailMessage> mailMessages;
        if (inBuildMailServer) {
            mailMessages = app.mail().waitForMail(2, 10000);
        }
        else {
            mailMessages = app.james().waitForMail(username, password, 60000);
        }

        String confirmationLink = findConfirmationLink(mailMessages, email);
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(username, password));
    }

    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod (alwaysRun = true)
    public void stopMailServer(){
        if (inBuildMailServer) {
            app.mail().stop();
        }
    }
}
