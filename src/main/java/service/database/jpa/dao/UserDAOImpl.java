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

    @Override
    public int save(User user) {
        EntityManager em = null;

        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return 1;
        } catch (HibernateException e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            return 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

//    TODO
    @Override
    public @Nullable User get(long id) {
        return null;
    }

    @Override
    public @Nullable User getByLogin(String login) {
        EntityManager em = null;

        try {
            em = entityManagerFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = cq.from(User.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(userRoot.get("login"), login));
            cq.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
            TypedQuery<User> q = em.createQuery(cq);
            return q.getSingleResult();
        } catch (NoResultException nre){
            logger.debug("No user with login " + login);
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public @Nullable User getByEmail(String email) {
        EntityManager em = null;

        try {
            em = entityManagerFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
            Root<User> userRoot = cq.from(User.class);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(userRoot.get("email"), email));
            cq.where(criteriaBuilder.and(predicates.toArray(new Predicate[]{})));
            TypedQuery<User> q = em.createQuery(cq);
            return q.getSingleResult();
        } catch (NoResultException nre){
            logger.debug("No user with email " + email);
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
