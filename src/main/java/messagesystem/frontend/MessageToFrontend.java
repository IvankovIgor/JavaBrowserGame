package messagesystem.frontend;

import messagesystem.Abonent;
import messagesystem.Address;
import messagesystem.Message;

public abstract class MessageToFrontend extends Message{
    public MessageToFrontend(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
//        TODO
//        if (abonent instanceof AccountService) {
//            exec((AccountService) abonent);
//        }
    }

//    abstract void exec(AccountService accountService);
}
