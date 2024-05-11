package org.portal.daos;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import org.portal.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class UserDao implements AbstractHibernateDao<User> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User find(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        User response = session.get(User.class, id);
        transaction.commit();
        return response;
    }

    public User find(String login) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        User response = session.createQuery(
                "SELECT u FROM User u WHERE u.login = ?1", User.class
        ).setParameter(1, login).getSingleResultOrNull();

        transaction.commit();
        return response;
    }

    @Override
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<User> response = session.createQuery("SELECT u FROM User u", User.class).getResultList();
        transaction.commit();
        return response;
    }

    @Override
    public User create(User entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public void update(User entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
    }

    @Override
    public void delete(User entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
    }

    @Override
    public void delete(long id) {
        User entity = find(id);
        delete(entity);
    }
}
