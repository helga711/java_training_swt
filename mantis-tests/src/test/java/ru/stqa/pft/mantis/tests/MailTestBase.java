package ru.stqa.pft.mantis.tests;

import org.jetbrains.annotations.NotNull;
import org.testng.annotations.*;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.User;

import java.util.List;

public class MailTestBase extends TestBase {

    @BeforeSuite
    public void startMailServer(){
        app.mailServer().start();
    }

    protected void register(@NotNull User user) throws Exception {
        app.mailServer().createUser(user.getUsername(), user.getMailPassword());
        app.registration().start(user.getUsername(), user.getEmail());
        List<MailMessage> mailMessages = app.mailServer().waitForMail(user.getUsername(), user.getMailPassword(), 2, 60000);
        String confirmationLink = findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, user.getMantisPassword());
    }

    protected String findConfirmationLink(@NotNull List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findAny().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterSuite
    public void stopMailServer(){
        app.mailServer().stop();
    }
}
