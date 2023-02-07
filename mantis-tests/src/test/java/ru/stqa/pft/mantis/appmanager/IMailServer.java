package ru.stqa.pft.mantis.appmanager;

import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.util.List;

public interface IMailServer {

    void createUser(String username, String password);

    List<MailMessage> waitForMail(String username, String password, int count, int timeout) throws Exception;

    void start();

    void stop();

    void drainEmail(String username, String password) throws MessagingException;
}
