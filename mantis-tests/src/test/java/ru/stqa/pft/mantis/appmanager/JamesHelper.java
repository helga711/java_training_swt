package ru.stqa.pft.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stqa.pft.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JamesHelper implements IMailServer {
    private final ApplicationManager app;

    private final TelnetClient telnet;
    private InputStream in;
    private PrintStream out;

    private final Session mailSession;
    private Store store;
    private String mailServer;

    public JamesHelper(ApplicationManager app) {
        this.app = app;
        this.telnet = new TelnetClient();
        mailSession = Session.getDefaultInstance(System.getProperties());
    }

    public boolean doesUserExist(String name) {
        initTelnetSession();
        write("verify " + name);
        String result = readUntil("exist");
        closeTelnetSession();
        return result != null && result.trim().equals("User " + name + " exist");
    }

    public void createUser(String username, String password) {
        initTelnetSession();
        write("adduser " + username + " " + password);
        readUntil("User " + username + " added");
        closeTelnetSession();
    }

    @Override
    public List<MailMessage> waitForMail(String username, String password, int count, int timeout) throws Exception {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() < start + timeout){
            List<MailMessage> allMail = getAllMail(username, password);
            if (allMail.size() > 0) {
                return allMail;
            }
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new Exception("No mails.");
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    private void closeTelnetSession() {
        write("quit");
    }

    public void deleteUser(String username, String password) {
        initTelnetSession();
        write("adduser " + username + " " + password);
        readUntil("User " + username + " deleted");
        closeTelnetSession();
    }

    private void initTelnetSession() {
        mailServer = app.getProperty("mailserver.host");
        int port = Integer.parseInt(app.getProperty("mailserver.port"));
        String login = app.getProperty("mailserver.adminLogin");
        String password = app.getProperty("mailserver.adminPassword");
        try {
            telnet.connect(mailServer, port);
            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        readUntil("Login id:");
        write("");
        readUntil("Password:");
        write("");

        readUntil("Login id:");
        write(login);
        readUntil("Password:");
        write(password);

        readUntil("Welcome " + login + ". HELP for a list of commands");
    }

    private void write(String value) {
        try {
            out.println(value);
            out.flush();
            System.out.println(value);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private @Nullable String readUntil(@NotNull String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuilder sb = new StringBuilder();
            char ch = (char) in.read();
            while (true) {
                System.out.println(ch);
                sb.append(ch);
                if (ch == lastChar){
                    if (sb.toString().endsWith(pattern)){
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void drainEmail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
        for (Message message : inbox.getMessages()) {
            message.setFlag(Flags.Flag.DELETED, true);
        }
        closeFolder(inbox);
    }

    private @NotNull Folder openInbox(String username, String password) throws MessagingException {
        store = mailSession.getStore("pop3");
        store.connect(mailServer, username, password);
        Folder folder = store.getDefaultFolder().getFolder("INBOX");
        folder.open(Folder.READ_WRITE);
        return folder;
    }

    private void closeFolder(@NotNull Folder folder) throws MessagingException {
        folder.close(true);
        store.close();
    }

    private List<MailMessage> getAllMail(String username, String password) throws MessagingException {
        Folder inbox = openInbox(username, password);
        List<MailMessage> messages = Arrays.stream(inbox.getMessages())
                .map(JamesHelper::toModelMail).collect(Collectors.toList());
        closeFolder(inbox);
        return messages;
    }

    public static @Nullable MailMessage toModelMail(@NotNull Message m) {
        try {
            return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
