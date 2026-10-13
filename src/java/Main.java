import notification.*;
import sender.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();
        SmsMessageSender sms = new SmsMessageSender();
        PushNotificationSender push = new PushNotificationSender();

        System.out.println("=== SCENARIO 1: UrgentNotification + Telegram ===");
        Notification urgent = new UrgentNotification(telegram);
        urgent.send("user1", "System reboot required immediately!");

        System.out.println("\n=== SCENARIO 2: EncryptedNotification + Email ===");
        Notification encrypted = new EncryptedNotification(email);
        encrypted.send("user2@example.com", "Secret password token");

        System.out.println("\n=== SCENARIO 3: ScheduledBatchNotification + SMS ===");
        ScheduledBatchNotification batch = new ScheduledBatchNotification(sms, List.of("+123456", "+789012"));
        batch.sendBatch("Server maintenance at 2 AM");

        System.out.println("\n=== SCENARIO 4: Runtime switching (Telegram -> Email) ===");
        Notification runtimeNotif = new UrgentNotification(telegram);
        runtimeNotif.send("user3", "First message via Telegram");
        runtimeNotif.setSender(email);
        runtimeNotif.send("user3@example.com", "Second message via Email");

        System.out.println("\n=== SCENARIO 5: Fallback Mechanism (Telegram unavailable -> SMS) ===");
        telegram.setAvailable(false); // Имитируем падение Telegram
        UrgentNotification fallbackNotif = new UrgentNotification(telegram, sms);
        fallbackNotif.send("+11223344", "CRITICAL: Database connection lost!");

        System.out.println("\n=== SCENARIO 6: MarketingNotification + PushNotificationSender ===");
        Notification marketing = new MarketingNotification(push, "AUTUMN2026");
        marketing.send("user4", "Get 30% off on all items!");
    }
}