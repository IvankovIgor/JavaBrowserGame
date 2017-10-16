package service.database.jdbc.mysql;

import entity.resource.DatabaseSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.DAOFactory;
import service.database.dao.UserDAO;
import service.database.jdbc.mysql.dao.UserDAOImpl;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcMySqlDAOFactory extends DAOFactory {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static String connectionUrl;
    private static String user;
    private static String password;

    public JdbcMySqlDAOFactory(DatabaseSettings databaseSettings) {
        try {
            DriverManager.registerDriver((Driver) Class.forName(databaseSettings.getDriver()).newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
            logger.warn("While registring JDBC driver");
        }
        connectionUrl = databaseSettings.getUrl();
        user = databaseSettings.getUser();
        password = databaseSettings.getPassword();
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, user, password);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
