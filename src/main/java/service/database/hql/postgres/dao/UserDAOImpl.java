package service.database.hql.postgres.dao;

import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import service.database.dao.UserDAO;

import java.lang.invoke.MethodHandles;

public class UserDAOImpl implements UserDAO {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private SessionFactory sessionFactory;

    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int save(User user) {
        return 0;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User getByLogin(String name) {
        return null;
    }
}
