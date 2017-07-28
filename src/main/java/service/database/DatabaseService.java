package service.database;

import entity.account.User;

import java.util.List;

public interface DatabaseService {

    void save(User dataSet);

    User read(long id);

    User readByName(String name);

    List<User> readAll();

    long readCountAll();

    void update(User dataSet);

    List<User> readLimitOrder(int limit);

    void shutdown();
}
