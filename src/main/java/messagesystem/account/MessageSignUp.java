package messagesystem.account;

import entity.account.AccountStatus;
import messagesystem.Address;
import service.account.AccountService;

import java.util.Set;

public class MessageSignUp extends MessageToAccountService {
    private String login;
    private String password;
    private String email;

    public MessageSignUp(Address from, Address to, String login, String password, String email) {
        super(from, to);
        this.login = login;
        this.password = password;
        this.email = email;
    }

    @Override
    void exec(AccountService accountService) {
        Set<AccountStatus> result = accountService.signUp(login, password, email);
//        Message back = new MessageIsAuthenticated(getTo(), getFrom(), , result);
//        Abonent abonent = (Abonent) accountService;
//        abonent.getMessageSystem().sendMessage(back);
    }
}
