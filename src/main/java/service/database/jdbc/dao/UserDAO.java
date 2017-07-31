package service.database.jdbc.dao;


import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.jdbc.executor.TExecutor;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private Connection connection;

    public UserDAO(Connection connection){
        this.connection = connection;
    }

    public int save(User user) {
        TExecutor exec = new TExecutor();
        String sql = "INSERT INTO user(id, login, password, email) VALUES(?, ?, ?, ?)";
        return exec.execPreparedUpdate(connection, sql, stmt -> {
            stmt.setLong(1, user.getId());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getPassword());
            stmt.setString(4, user.getEmail());
        });
    }

    public User get(long id) throws SQLException {
        TExecutor exec = new TExecutor();
        String sql = "SELECT id, login, password, email FROM user WHERE id=" + id;
        return exec.execQuery(connection, sql, result -> {
            result.next();
            return new User(result.getLong(1), result.getString(2), result.getString(3),
                    result.getString(4));
        });
    }
}