package service.database.jpa.dao;

import entity.account.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.database.dao.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
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
        return 0;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public User getByLogin(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<User> cq = criteriaBuilder.createQuery(User.class);
        Root<User> s = cq.from(User.class);
//        List<Predicate> predicates = new ArrayList<>();
//        predicates.add(criteriaBuilder.equal(, name));
        TypedQuery<User> q = entityManager.createQuery(cq);
        List<User> users = q.getResultList();
        return users.get(0);
    }
}
