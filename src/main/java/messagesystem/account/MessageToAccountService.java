package messagesystem.account;

import messagesystem.Abonent;
import messagesystem.Address;
import messagesystem.Message;
import service.account.AccountService;

public abstract class MessageToAccountService extends Message {
    public MessageToAccountService(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof AccountService) {
            exec((AccountService) abonent);
        }
    }

    abstract void exec(AccountService accountService);
}
