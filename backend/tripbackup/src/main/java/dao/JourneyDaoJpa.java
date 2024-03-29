package dao;

import domain.Journey;
import domain.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Stateless @JPA
public class JourneyDaoJpa implements JourneyDao{

    @PersistenceContext(unitName = "t")
    private EntityManager em;

    public JourneyDaoJpa() {
    }

    @Override
    public List<Journey> getJourneyByUser(String name) {
        TypedQuery<Journey> query = em.createNamedQuery("journey.getJourneyByUser", Journey.class);
        query.setParameter("userName", name);
        return query.getResultList();
    }

    @Override
    public void add(Journey journey) {
        em.persist(journey);
    }

    @Override
    public void remove(Journey journey) {
        Journey j = em.merge(journey);
        em.remove(j);
    }

    @Override
    public void update(Journey journey) {
        em.merge(journey);
    }

    @Override
    public Journey findByName(String name) {
        TypedQuery<Journey> query = em.createNamedQuery("journey.findByName", Journey.class);
        query.setParameter("name", name);
        List<Journey> result = query.getResultList();
        return result.get(0);
    }

    @Override
    public List<Journey> searchJourney(String journeyName) {
        TypedQuery<Journey> query = em.createNamedQuery("journey.findByName", Journey.class);
        query.setParameter("name", journeyName);
        return query.getResultList();
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
