package service.database.dao;

import entity.account.User;

public interface UserDAO {
    public int save(User user);
    public User get(long id);
    public User getByLogin(String name);
}
