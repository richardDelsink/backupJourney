package dao;

import domain.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GroupDaoJPA implements GenericInterface<Group> {

    @PersistenceContext(unitName = "t")
    private EntityManager em;

    @Override
    public void add(Group group) {
        em.persist(group);
    }

    @Override
    public void remove(Group group) {
        em.remove(em.merge(group));
    }

    @Override
    public void update(Group group) {
        em.merge(group);
    }

    @Override
    public Group findByName(String name) {
        return em.find(Group.class,name);
    }

    protected EntityManager getEntityManager() {
        return em;
    }
}
