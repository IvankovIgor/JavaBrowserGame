package service.database.jpa.dao;

import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.jetbrains.annotations.Nullable;
import service.database.dao.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @SuppressWarnings("ConstantNamingConvention")
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private EntityManagerFactory entityManagerFactory;

    public UserDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Transactional
    @Override
    public int save(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.flush();
            entityManager.getTransaction().commit();
        } catch (HibernateException e) {
            return 0;
        }

        return 1;
    }

//    TODO
    @Override
    public @Nullable User get(long id) {
        return null;
    }

    @Override
    public @Nullable User getByLogin(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
        Root<User> userRoot = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(userRoot.get("login"), login));
        cq.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
        TypedQuery<User> q = entityManager.createQuery(cq);
        try {
            return q.getSingleResult();
        } catch (NoResultException nre){
            logger.debug("No user with login " + login);
            return null;
        }
    }
}
