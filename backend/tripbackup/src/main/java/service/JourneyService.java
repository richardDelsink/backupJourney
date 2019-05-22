package service;

import dao.JPA;
import dao.JourneyDao;
import domain.Journey;
import domain.User;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
//@DeclareRoles({"UserRole", "AdminRole"})
public class JourneyService {

    @Inject @JPA
    private JourneyDao journeyDao;

   // @PermitAll
    public void addJourney(Journey journey){
        journeyDao.add(journey);
    }
   // @RolesAllowed({"AdminRole"})
    public void removeJourney(Journey journey){
        journeyDao.remove(journey);
    }
   // @RolesAllowed({"AdminRole"})
    public void removeJourney(String name) {
        Journey journey = journeyDao.findByName(name);
        if(journey != null)
        {
            journeyDao.remove(journey);
        }
    }
    //@PermitAll
    public Journey findByName(String name){
        return journeyDao.findByName(name);
    }
   // @PermitAll
    public List<Journey> findByUsername(String name) {
        return journeyDao.getJourneyByUser(name);
    }

    public JourneyService() {
    }
}
