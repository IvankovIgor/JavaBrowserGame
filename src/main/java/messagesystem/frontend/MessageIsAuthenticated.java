package messagesystem.frontend;

import entity.account.AccountStatus;
import messagesystem.Address;

import java.util.Set;

public class MessageIsAuthenticated extends MessageToFrontend {
    private String sessionId;
    private Set<AccountStatus> result;

    public MessageIsAuthenticated(Address from, Address to, String sessionId, Set<AccountStatus> result) {
        super(from, to);
        this.sessionId = sessionId;
        this.result = result;
    }

//    TODO
//    void exec
}
