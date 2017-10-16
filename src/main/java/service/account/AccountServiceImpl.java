package service.account;

import entity.account.AccountStatus;
import entity.account.User;
import main.Context;
import service.database.DAOFactory;
import util.AccountValidator;

import java.util.EnumSet;
import java.util.Hashtable;
import java.util.Set;

public class AccountServiceImpl implements AccountService {
    private final DAOFactory daoFactory;
    private final Hashtable<String, String> userSessions = new Hashtable<>();

    public AccountServiceImpl(Context context) {
        daoFactory = (DAOFactory) context.get(DAOFactory.class);
    }

    @Override
    public String getLoginBySession(String sessionId) {
        return userSessions.get(sessionId);
    }

    @Override
    public void saveUserSession(String login, String sessionId) {
        userSessions.put(sessionId, login);
    }

    @Override
    public Set<AccountStatus> signUp(String login, String password, String email) {
        Set<AccountStatus> accountStatuses = EnumSet.noneOf(AccountStatus.class);

        AccountValidator accountValidator = new AccountValidator(daoFactory.getUserDAO(), accountStatuses);
        accountValidator.validate(login, password, email);

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
        daoFactory.getUserDAO().save(newUser);
    }

    @Override
    public Set<AccountStatus> signIn(String login, String password) {
        Set<AccountStatus> accountStatuses = EnumSet.noneOf(AccountStatus.class);

        AccountValidator accountValidator = new AccountValidator(daoFactory.getUserDAO(), accountStatuses);
        accountValidator.validate(login, password);


        if (!accountStatuses.isEmpty())
            return accountStatuses;
        if (daoFactory.getUserDAO().getByLogin(login) != null) {
//            TODO
//            userSessions.put(login, "asdasd");
        } else {
            accountStatuses.add(AccountStatus.INVALID_LOGIN);
        }

        return accountStatuses;
    }
}
