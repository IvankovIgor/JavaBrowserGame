package service.database;

import entity.resource.DatabaseSettings;
import org.jetbrains.annotations.Nullable;
import service.database.dao.UserDAO;
import service.database.hql.HqlDAOFactory;
import service.database.jdbc.mysql.JdbcMySqlDAOFactory;
import service.database.jpa.JpaMySqlDAOFactory;

public abstract class DAOFactory {
    public static final int JDBC_MYSQL = 1;
    public static final int JDBC_POSTGRES = 2;
    public static final int HQL = 3;
    public static final int JPA_MYSQL = 4;

    public abstract UserDAO getUserDAO();

    public static @Nullable DAOFactory getDAOFactory(int whichFactory, DatabaseSettings dbSettings) {
        switch (whichFactory) {
            case JDBC_MYSQL:
                return new JdbcMySqlDAOFactory(dbSettings);
            case JDBC_POSTGRES:
                return null;
            case HQL:
                return new HqlDAOFactory(dbSettings);
            case JPA_MYSQL:
                return new JpaMySqlDAOFactory(dbSettings);
            default:
                return null;
        }
    }

//    protected String configureConnectionUrl(DatabaseSettings dbSettings) {
//        return dbSettings.getType() +
//                dbSettings.getHost() +
//                ':' +
//                dbSettings.getPort() +
//                '/' +
//                dbSettings.getSchema() +
//                "?user=" +
//                dbSettings.getUser() +
//                "&password=" +
//                dbSettings.getPassword() +
//                "&useLegacyDatetimeCode=false&serverTimezone=UTC";
//    }
}
