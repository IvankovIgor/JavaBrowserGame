package service.database.hql.dao;

import entity.account.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO {

    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public void save(User dataSet) {
        session.save(dataSet);
    }

    public User read(long id) {
        return (User) session.get(User.class, id);
    }

    public User readByName(String name) {
        Criteria criteria = session.createCriteria(User.class);

        return (User) criteria.add(Restrictions.eq("login", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<User> readAll() {
        Criteria criteria = session.createCriteria(User.class);

        return (List<User>) criteria.list();
    }

    public void update(User dataSet) {
        session.update(dataSet);
    }

    @SuppressWarnings("unchecked")
    public List<User> readLimitOrder(int limit) {
        Query query = session.createSQLQuery(
                "CALL proc(:var)")
                .addEntity(User.class)
                .setParameter("var", limit);

        return (List<User>) query.list();
    }

    public long readCountAll() {
        Criteria criteria = session.createCriteria(User.class);
        criteria.setProjection(Projections.rowCount());

        return (long) criteria.list().get(0);
    }
}
