package domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "registrationKey.findKey", query = "SELECT r FROM RegistrationKey r WHERE r.registrationKey =:registrationKey")})

public class RegistrationKey {
    @Id
    @GeneratedValue
    private long id;

    private String registrationKey;

    @OneToOne
    private User user;

    public RegistrationKey() {
    }

    public RegistrationKey(String registrationKey, User user) {
        this.registrationKey = registrationKey;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationKey() {
        return registrationKey;
    }

    public void setRegistrationKey(String registrationKey) {
        this.registrationKey = registrationKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
