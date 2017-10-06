package messagesystem;

public abstract class Abonent {
    private MessageSystem messageSystem;
    private Address address;

    protected Abonent(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
        this.address = new Address();
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }
}
