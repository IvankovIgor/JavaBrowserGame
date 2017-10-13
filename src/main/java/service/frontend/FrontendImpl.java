package service.frontend;

import messagesystem.Abonent;
import messagesystem.MessageSystem;

public class FrontendImpl extends Abonent implements Frontend, Runnable {
//    private MessageSystem messageSystem;

    public FrontendImpl(MessageSystem messageSystem) {
        super(messageSystem);
        messageSystem.addService(this);
        messageSystem.getAddressService().addFrontendService(this);
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
}
