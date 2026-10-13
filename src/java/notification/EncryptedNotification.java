package notification;

import sender.MessageSender;
import java.util.Base64;

public class EncryptedNotification extends Notification {

    public EncryptedNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        String encryptedPayload = encryptMessage(message);
        if (sender != null) {
            sender.sendMessage(recipient, "Encrypted Message", encryptedPayload);
        }
    }

    public String encryptMessage(String plainText) {
        if (plainText == null) return "";
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }
}