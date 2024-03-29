package domain;



import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "step.getStepsByJourney", query = "SELECT s FROM Step s WHERE s.journey = :journey"),
        @NamedQuery(name = "step.findByName", query = "SELECT s FROM Step s WHERE s.stepName = :name")})

@XmlRootElement
public class Step implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int stepId;
    @ManyToOne
    private Journey journey;
    private String location;
    private String stepName;
    private String story;
    @OneToMany()
    private List<User> like;
    @OneToMany()
    private List<Message> messages;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;


    public Step(){
    }

    public Step(Journey journey , String location, String stepName, String story) {
        this.journey = journey;
        this.location = location;
        this.stepName = stepName;
        this.story = story;
        this.like = new ArrayList<>();
        this.postDate = new Date();
        this.messages = new ArrayList<>();

    }

    public int getStepId() {
        return stepId;
    }

    public Journey getJourney() {
        return journey;
    }

    public void setJourney(Journey journey) {
        this.journey = journey;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }


    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public List<User> getLike() {
        return like;
    }

    public void setLike(List<User> like) {
        this.like = like;
    }

    public void addLike(User user) throws IllegalArgumentException {
        if (this.like.contains(user)) {
            throw new IllegalArgumentException("User didn't like this step");
        }
        this.like.add(user);
    }

    public void removeLike(User user) throws IllegalArgumentException {
        if (!this.like.contains(user)) {
            throw new IllegalArgumentException("User didn't like this step");
        }
        this.like.remove(user);
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
