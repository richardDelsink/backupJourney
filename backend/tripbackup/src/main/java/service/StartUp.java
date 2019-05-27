package service;
import dao.GroupDaoJPA;
import dao.JPA;
import dao.JourneyDaoJpa;
import domain.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.xml.stream.events.Comment;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
@Startup
public class StartUp {

    @Inject
    private UserService service;


    @Inject
    private GroupDaoJPA groupDao;

    @Inject
    private JourneyService journeyService;

    @Inject
    private StepService stepService;

    @Inject
    private MessageService messageService;

    public StartUp(){

    }

    @PostConstruct
    void initTripJourney(){
        Group adminGroup = new Group(Group.ADMIN_GROUP);
        Group userGroup = new Group(Group.USER_GROUP);
        groupDao.add(adminGroup);
        groupDao.add(userGroup);

        // make users, 1 admin
        User richard = new User( "","Richard","","","rd.richard@hotmail.com","",true,"","", "richard");
        User wesley = new User( "","Wesley","","","","",true,"","", "wesley");
        User bill = new User( "","Bill","","","","",true,"","", "bill");
        User will = new User( "","Will","","","","",true,"","", "will");
        richard.getGroup().add(adminGroup);
        richard.setVerified(true);
        wesley.setVerified(true);
        bill.setVerified(true);
        will.setVerified(true);
        service.addUser(richard);
        service.addUser(wesley);
        service.addUser(bill);
        service.addUser(will);

        service.followUser(wesley, richard.getName());
        service.followUser(bill, richard.getName());
        service.followUser(will, richard.getName());
        service.followUser(richard, richard.getName());
        String dateInString = "10-02-2019";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // make journeys
        Journey canada = new Journey("Canada","Going to Canada, feeling so happy",date,date,"friends","Richard");
        Journey france = new Journey("France","Going to France",date,date,"friends","Richard");
        Journey germany = new Journey("Germany","Going to Germany, beer!!!!!!",date,date,"friends","Bill");
        Journey newYork = new Journey("New York","Going to New York, give me that pizza",date,date,"friends","Bill");

        journeyService.addJourney(canada);
        journeyService.addJourney(france);
        journeyService.addJourney(germany);
        journeyService.addJourney(newYork);


        // make steps
        Step stepCanada = new Step(canada,"Vancouver","Stanley Park","Whahahah what is this super");
        Step stepFrance = new Step(france,"Paris","Louvre Museum","Artsey");
        Step stepGermany = new Step(germany,"Berlin","Brandenburg Gate","What a big gate there");

        stepService.addStep(stepCanada);
        stepService.addStep(stepFrance);
        stepService.addStep(stepGermany);
            // make comments

        //Message mCanada = new Message(stepService.findByName(stepCanada.getStepName()).getStepId(),richard.getName(),"vet");
        //Message mCanada2 = new Message(stepService.findByName(stepCanada.getStepName()).getStepId(),bill.getName(),"nice");
        //Message mCanada3 = new Message(stepService.findByName(stepCanada.getStepName()).getStepId(),richard.getName(),"lekker");

        //messageService.addComment(mCanada);
        //messageService.addComment(mCanada2);
        //messageService.addComment(mCanada3);

        //stepService.addCommentToStep(stepCanada, mCanada);
       // stepService.addCommentToStep(stepCanada, mCanada3);
        //stepService.addCommentToStep(stepCanada2, mCanada2);

    }
}
