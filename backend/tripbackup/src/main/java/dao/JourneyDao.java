package dao;

import domain.Journey;
import domain.User;

import java.util.List;

public interface JourneyDao extends GenericInterface<Journey> {

    List<Journey> getJourneyByUser(String name);
    List<Journey> searchJourney(String journeyName);
}
