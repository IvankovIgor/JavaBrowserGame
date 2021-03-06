package service.database.hql.dao;

import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jetbrains.annotations.Nullable;
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
        logger.debug("Saving");
        int value = 0;

        try (Session session = sessionFactory.openSession()) {
            value = Math.toIntExact((long) session.save(user));
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.debug("While saving");
        }

        return value;
    }


    @Override
    public @Nullable User get(long id) {
        logger.debug("Getting by id");
        User value = null;

        try (Session session = sessionFactory.openSession()) {
            value = session.get(User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.debug("While getting by id");
        }

        return value;
    }

    @Override
    public @Nullable User getByLogin(String login) {
        logger.debug("Getting by login");
        User value = null;

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
            query.setParameter("login", login);
            value = query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.debug("While getting by login");
        }

        return value;
    }

    @Override
    public @Nullable User getByEmail(String email) {
        logger.debug("Getting by email");
        User value = null;

        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            value = query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            logger.debug("While getting by email");
        }

        return value;
    }
}
