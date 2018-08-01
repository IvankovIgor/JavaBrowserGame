package entity.account;

/**
 * Created by ivankov on 17.07.2017.
 */
public enum AccountStatus {
    CREATED("successfully created"),
    EMPTY_LOGIN("empty login"),
    INVALID_LOGIN("invalid login"),
    EXISTING_LOGIN("user having such login already exists"),
    EMPTY_PASSWORD("empty password"),
    INVALID_PASSWORD("invalid password"),
    EMPTY_EMAIL("empty email"),
    INVALID_EMAIL("invalid email"),
    EXISTING_EMAIL("user having such email already exists");

    private final String text;

    AccountStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
