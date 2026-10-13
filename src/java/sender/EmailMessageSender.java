package sender;

public class EmailMessageSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) return false;
        String emailContent = String.format("Subject: %s\nTo: %s\nBody: %s\n---\nSent via Email Service", title, recipient, body);
        System.out.println(emailContent);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Email";
    }
}