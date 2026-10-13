package sender;

public class KafkaEventSender implements MessageSender {
    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) return false;
        System.out.println(String.format("[Kafka Event Published] Topic: notification-events | Key: %s | Payload: {%s: %s}", recipient, title, body));
        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}