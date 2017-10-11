import messagesystem.Abonent;
import messagesystem.MessageSystem;

public class FrontendImpl extends Abonent implements Frontend, Runnable {
//    private MessageSystem messageSystem;

    public FrontendImpl(MessageSystem messageSystem) {
        super(messageSystem);
    }

    @Override
    public void run() {
        while (true) {
            getMessageSystem().execForAbonent(this);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public MessageSystem getMessageSystem() {
//        return messageSystem;
//    }
//
//    @Override
//    public Address getAddress() {
//        return address;
//    }
}
