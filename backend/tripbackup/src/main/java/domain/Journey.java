package domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "journey.getJourneyByUser", query = "SELECT j FROM Journey j WHERE j.userName = :userName"),
        @NamedQuery(name = "journey.findByName", query = "SELECT j FROM Journey j WHERE j.journeyName = :name")
       })
@XmlRootElement
public class Journey implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  journeyId;
    private String journeyName;
    private String journeySummary;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="CET")
    private Date startDate;
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd@HH:mm:ss.SSSZ", timezone="CET")
    private Date endDate;
    private String whoCanSee;
    private String userName;

    @Transient
    private List<Link> links = new ArrayList<>();

    public Journey(String journeyName, String journeySummary, String whoCanSee, String userName) {
        this.journeyName = journeyName;
        this.journeySummary = journeySummary;
        this.whoCanSee = whoCanSee;
        this.userName = userName;
    }

    public Journey(String journeyName, String journeySummary, Date startDate, Date endDate, String whoCanSee, String userName) {
        this.journeyName = journeyName;
        this.journeySummary = journeySummary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.whoCanSee = whoCanSee;
        this.userName = userName;
    }



    public Journey() {
    }

   /* public int getJourneyId() {
        return journeyId;
    }*/

    public String getJourneyName() {
        return journeyName;
    }

    public void setJourneyName(String journeyName) {
        this.journeyName = journeyName;
    }

    public String getJourneySummary() {
        return journeySummary;
    }

    public void setJourneySummary(String journeySummary) {
        this.journeySummary = journeySummary;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getWhoCanSee() {
        return whoCanSee;
    }

    public void setWhoCanSee(String whoCanSee) {
        this.whoCanSee = whoCanSee;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int journeyId) {
        this.journeyId = journeyId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void addLink(String rel, String url, String type)
    {
        Link link = new Link();
        link.setLink(url);
        link.setType(type);
        link.setRel(rel);
        links.add(link);
    }
}
