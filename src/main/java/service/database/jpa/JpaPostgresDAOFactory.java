package service.database.jpa;

import entity.resource.DatabaseSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.Configuration;
import service.database.DAOFactory;
import service.database.dao.UserDAO;
import service.database.jpa.dao.UserDAOImpl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.invoke.MethodHandles;

public class JpaPostgresDAOFactory extends DAOFactory {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static EntityManagerFactory entityManagerFactory;

    public JpaPostgresDAOFactory(DatabaseSettings dbSettings) {
        Configuration configuration = new Configuration();
        configuration.setProperty("javax.persistence.jdbc.driver", dbSettings.getDriver());
        configuration.setProperty("javax.persistence.jdbc.url", dbSettings.getUrl());
        configuration.setProperty("javax.persistence.jdbc.user", dbSettings.getUser());
        configuration.setProperty("javax.persistence.jdbc.password", dbSettings.getPassword());

        entityManagerFactory = Persistence.createEntityManagerFactory("JavaBrowserGame", configuration.getProperties());
        EntityManager entityManager = entityManagerFactory.createEntityManager();
    }

//    private static EntityManager createEntityManager() {
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//
//        builder.applySettings(configuration.getProperties());
//        ServiceRegistry serviceRegistry = builder.build();
//
//        return configuration.buildSessionFactory(serviceRegistry);
//    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOImpl(entityManagerFactory);
    }
}
