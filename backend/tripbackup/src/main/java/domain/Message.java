package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


@Entity
@NamedQueries({
        @NamedQuery(name = "comment.getCommentsByStep", query = "SELECT c FROM Message c WHERE c.stepId = :stepId"),
        @NamedQuery(name = "comment.getCommentsByUser", query = "SELECT c FROM Message c WHERE c.userName = :userName"),
        @NamedQuery(name = "comment.findByName", query = "SELECT c FROM Message c WHERE c.comment = :name")})
@XmlRootElement
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private int stepId;
    private String userName;
    private String comment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;

    public Message() {
    }

    public Message(int stepId, String userName, String comment) {
        this.stepId = stepId;
        this.userName = userName;
        this.comment = comment;
        this.postDate = new Date();
    }


    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void setMessageId(int messageId) {
        this.commentId = messageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
