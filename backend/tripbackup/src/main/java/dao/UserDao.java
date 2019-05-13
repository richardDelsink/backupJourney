package dao;

import domain.Group;
import domain.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface UserDao extends GenericInterface<User> {

    List<User> getFollowers(User user);
    void followUser(User u, User toFollow);
    void unfollowUser(User u, User toUnfollow);
    boolean login(String username, String password);
    ArrayList<User> getUsers();
}
