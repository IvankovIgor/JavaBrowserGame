package service.database.hql.mysql;

import entity.account.User;
import entity.resource.DatabaseSettings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import service.database.DAOFactory;
import service.database.dao.UserDAO;
import service.database.hql.mysql.dao.UserDAOImpl;

public class HqlMySqlDAOFactory extends DAOFactory {
    private SessionFactory sessionFactory;

    public HqlMySqlDAOFactory(DatabaseSettings databaseSettings) {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(User.class);
        configuration.setProperty("hibernate.dialect", databaseSettings.getDialect());
        configuration.setProperty("hibernate.connection.driver_class", databaseSettings.getDriver());
        configuration.setProperty("hibernate.connection.url", configureConnectionUrl(databaseSettings));
        configuration.setProperty("hibernate.connection.username", databaseSettings.getUser());
        configuration.setProperty("hibernate.connection.password", databaseSettings.getPassword());
        configuration.setProperty("hibernate.show_sql", databaseSettings.getShowSql());
        configuration.setProperty("hibernate.hbm2ddl.auto", databaseSettings.getMode());

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl(sessionFactory);
    }
}
