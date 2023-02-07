package ri.stqa.pft.mantis.model;

public class MailMessage {
    public final String text;
    public final String to;

    public MailMessage(String to, String text) {
        this.to = to;
        this.text = text;
    }
}
