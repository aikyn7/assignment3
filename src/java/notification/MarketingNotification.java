package notification;

import sender.MessageSender;

public class MarketingNotification extends Notification {
    private String promoCode;

    public MarketingNotification(MessageSender sender, String promoCode) {
        super(sender);
        this.promoCode = promoCode;
    }

    @Override
    public void send(String recipient, String message) {
        String marketingMessage = message + " Use promo code: " + promoCode;
        if (sender != null) {
            sender.sendMessage(recipient, "Special Offer!", marketingMessage);
        }
    }
}