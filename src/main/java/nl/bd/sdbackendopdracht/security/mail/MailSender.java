package nl.bd.sdbackendopdracht.security.mail;

public interface MailSender {
    void send(String to, String email);
}
