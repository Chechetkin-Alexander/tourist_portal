package org.portal.daos;

import com.google.common.base.Preconditions;
import jakarta.transaction.Transactional;
import org.portal.models.Card;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CardDao implements AbstractHibernateDao<Card> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Card find(long id) {
        return sessionFactory.getCurrentSession().get(Card.class, id);
    }

    @SuppressWarnings("unchecked")
    public List<Card> findByOrganizerId(long id) {
        return (List<Card>) sessionFactory.getCurrentSession().createQuery(
                "SELECT * FROM cards WHERE organizer_id = " + id
        ).list();
    }

    @SuppressWarnings("unchecked")
    public List<Card> findByParticipantId(long id) {
        return (List<Card>) sessionFactory.getCurrentSession().createQuery(
                "SELECT * FROM cards WHERE participant_id = " + id
        ).list();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Card> findAll() {
        return (List<Card>) sessionFactory.getCurrentSession().createQuery("SELECT * FROM cards").list();
    }

    @Override
    public Card create(Card entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public void update(Card entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
    }

    @Override
    public void delete(Card entity) {
        Preconditions.checkNotNull(entity);
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
    }

    @Override
    public void delete(long id) {
        Card entity = find(id);
        delete(entity);
    }
}
