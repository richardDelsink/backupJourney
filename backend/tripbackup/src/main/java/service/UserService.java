package service;

import dao.GroupDaoJPA;
import dao.JPA;
import dao.UserDao;
import domain.Group;
import domain.User;
import event.UserEvent;
import interceptor.LoggingInterceptor;
import util.PasswordHash;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.NotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Stateless
@DeclareRoles({"UserRole", "AdminRole"})
@Interceptors(LoggingInterceptor.class)
public class UserService {

    @Inject @JPA
    private UserDao userDao;

    @Inject
    private GroupDaoJPA groupDao;

   /* @Inject
    private Event<UserEvent> userEvent;*/
   @PermitAll
    public void addUser(User user) {
        if(user != null)
        {
           user.setPassword(PasswordHash.stringToHash(user.getPassword()));
           user.getGroup().add(groupDao.findByName(Group.USER_GROUP));
           userDao.add(user);
        }
    }
    @RolesAllowed({"AdminRole"})
    public void removeUser(User user){
        if(user != null)
        {
            userDao.remove(user);
        }
    }
    @RolesAllowed({"AdminRole"})
    public void removeUser(String name) {
        User user = userDao.findByName(name);
        if(user !=null)
        {
            userDao.remove(user);
        }
    }

    @PermitAll
    public User findByName(String name){
        return userDao.findByName(name);
    }

    @RolesAllowed({"UserRole"})
    public List<User> getFollowing(String username)throws NotFoundException{
        User user = userDao.findByName(username);

        if (user == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        return user.getFollowing();
    }
    @RolesAllowed({"UserRole"})
    public List<User> getFollowers(String name) throws NotFoundException {

        User user = userDao.findByName(name);

        if (user == null) {
            throw new NotFoundException("User " + name + " was not found");
        }

        return userDao.getFollowers(user);
    }

    @RolesAllowed({"UserRole"})
    public void followUser(User user, String username)throws NotFoundException {
        User toFollow = userDao.findByName(username);
        User user1 = userDao.findByName("Richard");

        if (toFollow == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        userDao.followUser(user1, toFollow);
    }
    @RolesAllowed({"UserRole"})
    public void unfollowUser(User user, String username)throws NotFoundException{
        User toUnfollow = userDao.findByName(username);

        if (toUnfollow == null) {
            throw new NotFoundException("User " + username + " was not found");
        }

        userDao.unfollowUser(user, toUnfollow);
    }
    public boolean login(String username, String password){
        return userDao.login(username, generateSha256(password));
    }

    public String generateSha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return text;
        }
    }

    /*public void addUserEvent(User user) {
        userEvent.fire(new UserEvent(user));
    }*/
    public UserService(){

    }
    @PermitAll
    public void update(User user){
        userDao.update(user);
    }
    @PermitAll
    public ArrayList<User> getUsers() {
        return userDao.getUsers();
    }
}
