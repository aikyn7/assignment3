package notification;

import sender.MessageSender;
import java.util.List;

public class ScheduledBatchNotification extends Notification {
    private List<String> recipients;

    public ScheduledBatchNotification(MessageSender sender, List<String> recipients) {
        super(sender);
        this.recipients = recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    @Override
    public void send(String unusedRecipient, String message) {
        sendBatch(message);
    }

    public String sendBatch(String message) {
        int total = recipients != null ? recipients.size() : 0;
        int successful = 0;
        int failed = 0;

        if (recipients != null) {
            for (String recipient : recipients) {
                if (sender != null && sender.isAvailable() && sender.sendMessage(recipient, "Batch Update", message)) {
                    successful++;
                } else {
                    failed++;
                }
            }
        }

        String summary = String.format("Total: %d\nSuccessful: %d\nFailed: %d", total, successful, failed);
        System.out.println(summary);
        return summary;
    }
}