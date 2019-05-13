package bean;

import domain.Journey;
import service.JourneyService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;


@Named(value = "journeyBean")
@RequestScoped
public class JourneyBean implements Serializable {

    @Inject
    private JourneyService journeyService;
    private int userId;


    public JourneyBean() {
    }
    public void removeJourney(String journeyName){
        journeyService.removeJourney(journeyName);
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ArrayList<Journey> getJourneys() {
        return new ArrayList<Journey>(journeyService.findByUsername(userId));
    }



}
