package dao;

import domain.RegistrationKey;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@JPA
@Stateless
public class RegistrationJpa implements Registration{

    @PersistenceContext(unitName = "t")
    private EntityManager em;

    @Override
    public RegistrationKey findByName(String keyName) {
        TypedQuery<RegistrationKey> query = em.createNamedQuery("registrationKey.findKey", RegistrationKey.class);
        query.setParameter("registrationKey", keyName);
        List<RegistrationKey> keys = query.getResultList();
        if (keys != null && !keys.isEmpty()) {
            return keys.get(0);
        }
        return null;
    }

    @Override
    public void add(RegistrationKey registrationKey) {
        em.persist(registrationKey);
    }

    @Override
    public void remove(RegistrationKey registrationKey) {
        em.remove(registrationKey);
    }

    @Override
    public void update(RegistrationKey registrationKey) {
        em.merge(registrationKey);
    }


    protected EntityManager getEntityManager() {
        return this.em;
    }
}
