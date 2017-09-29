package service.account;

import entity.account.AccountStatus;
import entity.account.User;
import messagesystem.Address;
import messagesystem.MessageSystem;
import util.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by ivankov on 13.07.2017.
 */
public class AccountServiceImpl implements AccountService, Runnable {
    private static Map<String, User> userMap = new HashMap<>();
    private final MessageSystem messageSystem;
    private final Address address = new Address();

    public AccountServiceImpl(MessageSystem messageSystem) {
        this.messageSystem = messageSystem;
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
        userMap.put(login, newUser);
    }

    @Override
    public Set<AccountStatus> signIn(String login, String password) {
        Set<AccountStatus> accountStatuses = new HashSet<>();

        Validator validator = new Validator(accountStatuses);
        validator.validate(login, password);

        if (!accountStatuses.isEmpty())
            return accountStatuses;

        return accountStatuses;
    }

    @Override
    public void run() {
        while (true) {
            messageSystem.execForAbonent(this);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, User> getUserMap() {
        return userMap;
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
