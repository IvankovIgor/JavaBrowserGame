package service.database.jdbc;

import entity.account.User;
import entity.resource.DatabaseSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.DatabaseService;
import service.database.jdbc.dao.UserDAO;
import service.database.jdbc.executor.TExecutor;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseServiceMySQLImpl implements DatabaseService {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private TExecutor tExecutor;
    private String connectionUrl;

    public DatabaseServiceMySQLImpl(DatabaseSettings databaseSettings) {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
            logger.warn("While registring JDBC driver");
        }
        this.connectionUrl = configureConnectionUrl(databaseSettings);
        tExecutor = new TExecutor();
    }

    private String configureConnectionUrl(DatabaseSettings dbSettings) {
        return dbSettings.getType() +
                dbSettings.getHost() +
                ':' +
                dbSettings.getPort() +
                '/' +
                dbSettings.getSchema() +
                "?user=" +
                dbSettings.getUser() +
                "&password=" +
                dbSettings.getPassword() +
                "&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    @Override
    public int save(User dataSet) {
        int value = 0;

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            UserDAO userDAO = new UserDAO(connection);
            value = userDAO.save(dataSet);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("While creating connection for saving user");
        }

        return value;
    }

    @Override
    public User read(long id) {
        User user = null;

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.get(id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn("While creating connection for saving user");
        }

        return user;
    }

//    @Override
//    public User readByName(String name) {
//        return tExecutor.execQuery(
//                (session, parameter) -> {
//                    UserDAO dao = new UserDAO(session);
//
//                    return dao.readByName(parameter);
//                }, name);
//    }
//
//    @Override
//    public List<User> readAll() {
//        return tExecutor.execQuery(
//                (session, parameter) -> {
//                    UserDAO dao = new UserDAO(session);
//
//                    return dao.readAll();
//                }, null);
//    }
//
//    @Override
//    public long readCountAll() {
//        return tExecutor.execQuery(
//                (session, parameter) -> {
//                    UserDAO dao = new UserDAO(session);
//
//                    return dao.readCountAll();
//                }, null);
//    }
//
//    @Override
//    public void update(User dataSet) {
//        tExecutor.execUpdate(
//                (session, parameter) -> {
//                    UserDAO dao = new UserDAO(session);
//                    dao.update(dataSet);
//                }, dataSet);
//    }
//
//    @Override
//    public List<User> readLimitOrder(int limit) {
//        Session session = sessionFactory.openSession();
//        UserDAO dao = new UserDAO(session);
//
//        return dao.readLimitOrder(limit);
//    }
//
//    @Override
//    public void shutdown() {
//        sessionFactory.close();
//    }
}
