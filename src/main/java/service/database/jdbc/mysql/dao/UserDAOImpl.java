package service.database.jdbc.mysql.dao;


import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import service.database.dao.UserDAO;
import service.database.jdbc.executor.TExecutor;
import service.database.jdbc.mysql.JdbcMySqlDAOFactory;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public int save(User user) {
        logger.debug("Saving user");
        int value = 0;
        TExecutor exec = new TExecutor();

        try (Connection connection = JdbcMySqlDAOFactory.createConnection()) {
            String sql = "INSERT INTO user(login, password, email) VALUES(?, ?, ?)";
            value = exec.execPreparedUpdate(connection, sql, stmt -> {
                stmt.setString(1, user.getLogin());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
            });
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("While saving");
        }

        return value;
    }

    @Override
    public @Nullable User get(long id) {
        logger.debug("Getting user");
        User value = null;
        TExecutor exec = new TExecutor();

        try (Connection connection = JdbcMySqlDAOFactory.createConnection()) {
            String sql = "SELECT id, login, password, email FROM user WHERE id = " + id;
            value = exec.execQuery(connection, sql, result -> {
                result.next();
                return new User(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
            });
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("While getting");
        }

        return value;
    }

    @Override
    public @Nullable User getByLogin(String name) {
        logger.debug("Getting user by name");
        User value = null;
        TExecutor exec = new TExecutor();

        try (Connection connection = JdbcMySqlDAOFactory.createConnection()) {
            String sql = "SELECT id, login, password, email FROM user WHERE name = " + name;
            value = exec.execQuery(connection, sql, result -> {
                result.next();
                return new User(result.getLong(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
            });
        } catch (SQLException e) {
            e.printStackTrace();
            logger.debug("While getting");
        }

        return value;
    }
}