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
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        Card response = session.get(Card.class, id);
        transaction.commit();
        return response;
    }

    @Override
    public List<Card> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Card> response = session.createQuery("SELECT c FROM Card c", Card.class).getResultList();
        transaction.commit();
        return response;
    }

    public List<Card> findByOrganizerId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Card> response = session.createQuery(
                "SELECT c FROM Card c WHERE c.organizer.id = " + id, Card.class
        ).getResultList();
        transaction.commit();
        return response;
    }

    public List<Card> findByParticipantId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Card> response = session.createQuery(
                "SELECT c FROM Card c WHERE c.participant is not null and c.participant.id = " + id, Card.class
        ).getResultList();
        transaction.commit();
        return response;
    }

    public List<Card> findFree() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Card> response = session.createQuery(
                "SELECT c FROM Card c WHERE c.participant is null", Card.class
        ).getResultList();
        transaction.commit();
        return response;
    }

    public List<Card> findCompleted() {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        List<Card> response = session.createQuery(
                "SELECT c FROM Card c WHERE c.completeDate is null", Card.class
        ).getResultList();
        transaction.commit();
        return response;
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