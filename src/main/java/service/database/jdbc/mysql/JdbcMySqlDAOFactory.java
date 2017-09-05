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

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static String connectionUrl;

    public JdbcMySqlDAOFactory(DatabaseSettings databaseSettings) {
        try {
            DriverManager.registerDriver((Driver) Class.forName(JDBC_DRIVER).newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
            logger.warn("While registring JDBC driver");
        }
        connectionUrl = databaseSettings.getUrl();
    }

    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl();
    }
}
