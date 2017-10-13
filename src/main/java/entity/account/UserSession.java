package entity.account;

import java.util.UUID;

public class UserSession {
    private String userSessionId;

    public UserSession() {
        userSessionId = UUID.randomUUID().toString();
    }

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }
}
