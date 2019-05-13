package service;
import dao.GroupDaoJPA;
import dao.JPA;
import dao.JourneyDaoJpa;
import domain.Group;
import domain.Journey;
import domain.User;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
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

    public StartUp(){

    }

    @PostConstruct
    void initTripJourney(){
        Group adminGroup = new Group(Group.ADMIN_GROUP);
        Group userGroup = new Group(Group.USER_GROUP);
        groupDao.add(adminGroup);
        groupDao.add(userGroup);

        // make users, 1 admin
        User richard = new User( "","Richard","","","","",true,"","", "richard");
        User wesley = new User( "","Wesley","","","","",true,"","", "wesley");
        User bill = new User( "","Bill","","","","",true,"","", "bill");
        User will = new User( "","Will","","","","",true,"","", "will");
        richard.getGroup().add(adminGroup);
        service.addUser(richard);
        service.addUser(wesley);
        service.addUser(bill);
        service.addUser(will);

        String dateInString = "10-02-2019";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        Date date = null;
        try {
            date = sdf.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // make journeys
        Journey canada = new Journey("Canada","Going to Canada, feeling so happy",date,date,"friends",1);
        Journey france = new Journey("France","Going to France",date,date,"friends",2);
        Journey germany = new Journey("Germany","Going to Germany, beer!!!!!!",date,date,"friends",3);
        Journey newYork = new Journey("New York","Going to New York, give me that pizza",date,date,"friends",4);

        journeyService.addJourney(canada);
        journeyService.addJourney(france);
        journeyService.addJourney(germany);
        journeyService.addJourney(newYork);


        // make steps
        // make comments

    }
}
