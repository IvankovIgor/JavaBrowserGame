package service.account;

import entity.account.AccountStatus;
import entity.account.User;
import messagesystem.Abonent;
import messagesystem.Address;
import messagesystem.Message;
import messagesystem.MessageSystem;
import messagesystem.account.MessageAuthenticate;
import util.Validator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ivankov on 13.07.2017.
 */
public class AccountServiceImpl extends Abonent implements AccountService, Runnable {
//    private static Map<String, User> userMap = new HashMap<>();
//    private final MessageSystem messageSystem;
//    private final Address address = new Address();

    public AccountServiceImpl(MessageSystem messageSystem) {
        super(messageSystem);
        messageSystem.addService(this);
        messageSystem.getAddressService().addAccountService(this);
    }

    @Override
    public Set<AccountStatus> signUp(String login, String password, String email) {
        Set<AccountStatus> accountStatuses = new HashSet<>();

        Validator validator = new Validator(accountStatuses);
        validator.validate(login, password, email);

        if (!accountStatuses.isEmpty())
            return accountStatuses;

        createUser(login, password, email);
        accountStatuses.add(AccountStatus.CREATED);

        return accountStatuses;
    }

    private void createUser(String login, String password, String email) {
        User newUser = new User();
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setEmail(email);

//        getMessageSystem().sendMessage();
    }

    @Override
    public Set<AccountStatus> signIn(String login, String password) {
        Set<AccountStatus> accountStatuses = new HashSet<>();

        Validator validator = new Validator(accountStatuses);
        validator.validate(login, password);

        if (!accountStatuses.isEmpty())
            return accountStatuses;

        Address from = this.getAddress();
        Address to = getMessageSystem().getAddressService().getDatabaseService().g
        Message msg = new MessageAuthenticate(this.getAddress(), ((Abonent)databaseService).getAddress(), login, password, "");
        getMessageSystem().sendMessage(msg);
        return accountStatuses;
    }

    @Override
    public void run() {
        while (true) {
            getMessageSystem().execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    public static Map<String, User> getUserMap() {
//        return userMap;
//    }

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
