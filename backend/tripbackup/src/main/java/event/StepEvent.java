package event;

import domain.Step;
import domain.User;

public class StepEvent {
    private Step step;
    private User user;

    public StepEvent(Step step, User user) {
        this.user = user;
        this.step = step;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
