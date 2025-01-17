package org.portal.daos;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import org.portal.models.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Transactional
public class RoleDao implements AbstractHibernateDao<Role> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role find(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Role response = session.get(Role.class, id);
        transaction.commit();
        return response;
    }
    @Override
    public List<Role> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Role> response = session.createQuery("SELECT r FROM Role r", Role.class).getResultList();
        transaction.commit();
        return response;
    }

    public Role findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Role result = session.createQuery("SELECT r FROM Role r WHERE r.name = ?1", Role.class)
                .setParameter(1, name).getSingleResultOrNull();

        transaction.commit();

        return result;
    }

    @Override
    public Role create(Role entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public void update(Role entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
    }

    @Override
    public void delete(Role entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
    }

    @Override
    public void delete(long id) {
        Role entity = find(id);
        delete(entity);
    }
}
