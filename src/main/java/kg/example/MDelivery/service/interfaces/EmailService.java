package kg.example.MDelivery.service.interfaces;

public interface EmailService {

    void sendMessage(String to, String subject, String text);
}
