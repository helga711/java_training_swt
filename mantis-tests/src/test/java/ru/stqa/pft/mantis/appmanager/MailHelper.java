package ru.stqa.pft.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class MailHelper implements IMailServer {

    private final ApplicationManager app;
    private Wiser wiser;

    public MailHelper(ApplicationManager app) {
        this.app = app;
        wiser = new Wiser();
    }

    public static MailMessage toModelMail(WiserMessage m) {
        try {
            MimeMessage mm = m.getMimeMessage();
            return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void start(){
        wiser.start();
    }

    public void stop(){
        wiser.stop();
    }

    @Override
    public void drainEmail(String username, String password) throws MessagingException {
        wiser.stop();
        wiser = new Wiser();
        wiser.start();
    }

    @Override
    public void createUser(String username, String password) {

    }

    @Override
    public List<MailMessage> waitForMail(String username, String password, int count, int timeout) throws Exception {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + timeout){
            if (wiser.getMessages().size() >= count){
                return wiser.getMessages().stream().map(MailHelper::toModelMail).collect(Collectors.toList());
            }
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Exception("No mails.");
    }
}
