package service.database.dao;

import entity.account.User;
import org.jetbrains.annotations.Nullable;

public interface UserDAO {
    int save(User user);
    @Nullable User get(long id);
    @Nullable User getByLogin(String login);
    @Nullable User getByEmail(String email);
}
