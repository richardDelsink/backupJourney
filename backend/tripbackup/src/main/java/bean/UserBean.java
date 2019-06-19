
package bean;

import domain.Group;
import domain.User;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;


import service.UserService;

import static javax.faces.application.FacesMessage.SEVERITY_INFO;


@Named(value = "userBean")
@RequestScoped
public class UserBean implements Serializable{
    
    @Inject
    private UserService userService;

    private String id;
    private String name;
    private String filter;

    public ArrayList<User> getUsers() {
        if (filter != null && filter.length() > 0) {
            ArrayList<User> filtered = new ArrayList<>();
            for (User u : userService.getUsers()) {
                if (u.getName().toLowerCase().startsWith(filter)) {
                    filtered.add(u);
                }
            }
            return filtered;
        } else {
            return userService.getUsers();
        }
    }


    public void removeUser(String name) {
        userService.removeUser(name);
    }
    
    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void updateGroup(String name, String group) {
        try {
            User u = userService.findByName(name);
            Group g = new Group(group);
            u.getGroup().add(g);
            userService.update(u);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(SEVERITY_INFO, name +" has already been assign "+group+" role", null));
        }
    }
}
