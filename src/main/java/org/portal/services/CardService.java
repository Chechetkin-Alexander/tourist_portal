package org.portal.services;

import org.portal.daos.CardDao;
import org.portal.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CardService implements AbstractService<Card> {
    @Autowired
    protected CardDao DAO;

    @Override
    public Card find(long id) {
        return DAO.find(id);
    }

    @Override
    public List<Card> findAll() {
        return DAO.findAll();
    }

    @Override
    public Card create(Card entity) {
        return DAO.create(entity);
    }

    @Override
    public void update(Card entity) {
        DAO.update(entity);
    }

    @Override
    public void delete(Card entity) {
        DAO.delete(entity);
    }

    @Override
    public void delete(long id) {
        DAO.delete(id);
    }
}