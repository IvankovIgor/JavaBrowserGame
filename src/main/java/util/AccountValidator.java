package util;

import entity.account.AccountStatus;
import service.database.dao.UserDAO;

import java.util.Set;

/**
 * @author Igor Ivankov
 */
public class AccountValidator {
    private final Set<AccountStatus> accountStatuses;

    private UserDAO userDAO;

    public AccountValidator(UserDAO userDAO, Set<AccountStatus> accountStatuses) {
        this.accountStatuses = accountStatuses;
        this.userDAO = userDAO;
    }

    public void validate(String login, String password) {
        validateLogin(login);
        validatePassword(password);
    }

    public void validate(String login, String password, String email) {
        validateLogin(login);
        validatePassword(password);
        validateEmail(email);
    }

    private void validateLogin(String login) {
        if (isEmptyLogin(login)) {
            accountStatuses.add(AccountStatus.EMPTY_LOGIN);
        } else if (isInvalidLogin(login)) {
            accountStatuses.add(AccountStatus.INVALID_LOGIN);
        } else if (isExistingLogin(login)) {
            accountStatuses.add(AccountStatus.EXISTING_LOGIN);
        }
    }

    private boolean isEmptyLogin(String login) {
        return login == null || login.isEmpty();
    }

    private boolean isInvalidLogin(String login) {
        // TODO
        return false;
    }

    private boolean isExistingLogin(String login) {
        return userDAO.getByLogin(login) != null;
    }

    private void validatePassword(String password) {
        if (isEmptyPassword(password)) {
            accountStatuses.add(AccountStatus.EMPTY_PASSWORD);
        } else if (isTooShortPassword(password)) {
            accountStatuses.add(AccountStatus.TOO_SHORT_PASSWORD);
        } else if (isInvalidPassword(password)) {
            accountStatuses.add(AccountStatus.INVALID_PASSWORD);
        }
    }

    private boolean isEmptyPassword(String password) {
        return password == null || password.isEmpty();
    }

    private boolean isTooShortPassword(String password) {
        return password.length() < 8;
    }

    private boolean isInvalidPassword(String password) {
        // TODO
        return false;
    }

    private void validateEmail(String email) {
        if (isEmptyEmail(email)) {
            accountStatuses.add(AccountStatus.EMPTY_EMAIL);
        } else if (isInvalidEmail(email)) {
            accountStatuses.add(AccountStatus.INVALID_EMAIL);
        } else if (isExistingEmail(email)) {
            accountStatuses.add(AccountStatus.EXISTING_EMAIL);
        }
    }

    private boolean isEmptyEmail(String email) {
        return email == null || email.isEmpty();
    }

    private boolean isInvalidEmail(String email) {
        return !email.contains("@") || !email.contains(".");
    }

    private boolean isExistingEmail(String email) {
        // TODO
        return false;
    }

    public Set<AccountStatus> getAccountStatuses() {
        return accountStatuses;
    }
}
