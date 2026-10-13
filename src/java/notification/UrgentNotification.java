package notification;

import sender.MessageSender;

public class UrgentNotification extends Notification {
    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    public UrgentNotification(MessageSender primarySender, MessageSender fallbackSender) {
        super(primarySender);
        this.fallbackSender = fallbackSender;
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentMessage = "[URGENT] " + message;

        if (sender != null && sender.isAvailable()) {
            boolean success = sender.sendMessage(recipient, "URGENT ALERT", urgentMessage);
            if (success) return;
        }

        // Fallback mechanism
        if (fallbackSender != null && fallbackSender.isAvailable()) {
            System.out.println("Primary sender (" + (sender != null ? sender.getChannelName() : "null") +
                    ") failed/unavailable. Switching to fallback: " + fallbackSender.getChannelName());
            fallbackSender.sendMessage(recipient, "URGENT ALERT (FALLBACK)", urgentMessage);
        } else {
            System.err.println("Failed to send urgent notification: All providers unavailable.");
        }
    }
}