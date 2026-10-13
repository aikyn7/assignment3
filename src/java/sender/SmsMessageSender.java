package sender;

public class SmsMessageSender implements MessageSender {
    private boolean available = true;
    private int maxCharacterLimit = 160;

    public SmsMessageSender() {}

    public SmsMessageSender(int maxCharacterLimit) {
        this.maxCharacterLimit = maxCharacterLimit;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setMaxCharacterLimit(int maxCharacterLimit) {
        this.maxCharacterLimit = maxCharacterLimit;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) return false;

        String fullMessage = (title == null || title.isEmpty() ? "" : title + ": ") + body;
        if (fullMessage.length() > maxCharacterLimit) {
            System.err.println("SMS Failed: Exceeds character limit of " + maxCharacterLimit);
            return false;
        }

        System.out.println(String.format("[SMS to %s]: %s", recipient, fullMessage));
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "SMS";
    }
}