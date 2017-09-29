import messagesystem.Address;
import messagesystem.MessageSystem;

public class FrontendImpl implements Frontend, Runnable {
    private MessageSystem messageSystem;
    private Address address = new Address();

    public FrontendImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
