package messagesystem.account;

import entity.account.AccountStatus;
import messagesystem.Address;
import messagesystem.Message;
import messagesystem.frontend.MessageIsAuthenticated;
import service.account.AccountService;

import java.util.Set;

public class MessageAuthenticate extends MessageToAccountService {
    private String name;
    private String password;
    private String sessionId;

    public MessageAuthenticate(Address from, Address to, String name, String password, String sessionId) {
        super(from, to);
        this.name = name;
        this.password = password;
        this.sessionId = sessionId;
    }

    @Override
    void exec(AccountService accountService) {
        Set<AccountStatus> result = accountService.signIn(name, password);
        Message back = new MessageIsAuthenticated(getTo(), getFrom(), sessionId, result);
        accountService.getMessageSystem().sendMessage(back);
    }
}
