import notification.*;
import sender.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotificationGatewayTest {

    private TelegramMessageSender telegram;
    private EmailMessageSender email;
    private SmsMessageSender sms;
    private PushNotificationSender push;

    @BeforeEach
    void setUp() {
        telegram = new TelegramMessageSender();
        email = new EmailMessageSender();
        sms = new SmsMessageSender();
        push = new PushNotificationSender();
    }

    @Test
    void testSuccessfulDelivery() {
        Notification notification = new UrgentNotification(telegram);
        assertDoesNotThrow(() -> notification.send("user1", "Hello"));
    }

    @Test
    void testUnavailableProvider() {
        telegram.setAvailable(false);
        assertFalse(telegram.isAvailable());
        assertFalse(telegram.sendMessage("user1", "Title", "Body"));
    }

    @Test
    void testFallbackMechanism() {
        telegram.setAvailable(false);
        UrgentNotification urgent = new UrgentNotification(telegram, sms);

        assertDoesNotThrow(() -> urgent.send("+12345", "Panic alert"));
    }

    @Test
    void testSmsLengthRestriction() {
        SmsMessageSender shortSms = new SmsMessageSender(20);
        assertFalse(shortSms.sendMessage("+123", "Title", "This text is way too long for 20 characters"));
    }

    @Test
    void testEncryptedMessage() {
        EncryptedNotification encryptedNotif = new EncryptedNotification(email);
        String plainText = "MySecretData";
        String encoded = encryptedNotif.encryptMessage(plainText);

        assertNotEquals(plainText, encoded);
        assertEquals("TXlTZWNyZXREYXRh", encoded);
    }

    @Test
    void testBatchNotificationSummary() {
        ScheduledBatchNotification batch = new ScheduledBatchNotification(sms, List.of("user1", "user2"));
        String summary = batch.sendBatch("Weekly Digest");

        assertTrue(summary.contains("Total: 2"));
        assertTrue(summary.contains("Successful: 2"));
        assertTrue(summary.contains("Failed: 0"));
    }

    @Test
    void testRuntimeSenderSwitching() {
        Notification notification = new UrgentNotification(telegram);
        assertEquals("Telegram", notification.getSender().getChannelName());

        notification.setSender(email);
        assertEquals("Email", notification.getSender().getChannelName());
    }

    @Test
    void testNewPushNotificationSender() {
        Notification notification = new UrgentNotification(push);
        assertTrue(push.isAvailable());
        assertTrue(push.sendMessage("user1", "Push Title", "Push Body"));
    }

    @Test
    void testNewMarketingNotification() {
        MarketingNotification marketing = new MarketingNotification(telegram, "SALE50");
        assertDoesNotThrow(() -> marketing.send("user1", "Huge Sale!"));
    }

    @Test
     void testFailureScenarioAllProvidersDown() {
        telegram.setAvailable(false);
        sms.setAvailable(false);

        UrgentNotification urgent = new UrgentNotification(telegram, sms);
        assertDoesNotThrow(() -> urgent.send("user1", "Critical failure test"));
    }
}