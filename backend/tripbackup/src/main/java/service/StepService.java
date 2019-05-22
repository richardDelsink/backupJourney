package service;


import dao.JPA;
import dao.StepDao;
import domain.Journey;
import domain.Message;
import domain.Step;
import domain.User;
import event.StepEvent;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;

@Stateless
public class StepService {

    @Inject @JPA
    private StepDao stepDao;
    @Inject
    private JourneyService journeyService;
    @Inject
    private UserService userService;

    @Inject
    private Event<StepEvent> stepEvent;

    public void addStep(Step step){

        Step s = new Step(step.getJourney(),step.getLocation(),step.getStepName(),step.getStory());
        User u = userService.findByName(s.getJourney().getUserName());
        stepDao.add(step);
        stepEvent.fire(new StepEvent(s,u));
    }

    public void removeStep(Step step){
        stepDao.remove(step);
    }

    public void removeStep(String name) {
        Step step = findByName(name);
        stepDao.remove(step);
    }

    public Step findStepById(int id){
       return stepDao.findStepById(id);
    }

    public void addCommentToStep(Step s, Message m){
        stepDao.addCommentStep(s,m);
    }
    public Step findByName(String name){
        return stepDao.findByName(name);
    }

    public List<Step> findByJourney(Journey journey) {
        return stepDao.getStepByJourney(journey);
    }

    public Step likeStep(int stepId, String userName)  {
        User u = userService.findByName(userName);
        Step s = stepDao.findStepById(stepId);
        if (s == null) {
            throw new NotFoundException("Step does not exist");
        }
        return stepDao.likeStep(s, u);
    }

    public Step unlikeStep(int stepId, String userName) throws NotFoundException {
        User u = userService.findByName(userName);
        Step s = stepDao.findStepById(stepId);
        if (s == null) {
            throw new NotFoundException("Step does not exist");
        }
        if (!s.getLike().contains(u)) {
            throw new NotFoundException("User didn't like this step");
        }

        return stepDao.unlikeStep(s, u);
    }

   /* public void addStepEvent(Step step) {

        stepEvent.fire(new StepEvent(step));
    }*/
}
