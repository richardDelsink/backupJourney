package event;

import domain.Journey;

public class JourneyEvent {
    private Journey journey;

    public JourneyEvent(Journey journey) {
        this.journey = journey;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }
}
