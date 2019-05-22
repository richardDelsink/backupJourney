/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import auth.JWTStore;
import com.nimbusds.jose.JOSEException;
import dao.JPA;
import dao.UserDaoJpa;

import domain.Group;

import domain.User;
import dao.UserDao;
import util.PasswordHash;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.NotFoundException;


public class AuthService {

    private PasswordHash pw;
    @Inject @JPA
    UserDao userDao;


    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;

    @Inject
    JWTStore jwtStore;



    public String login(String username, String password) throws JOSEException {
        User foundUser = userDao.findByName(username);
        if (foundUser == null) {
            throw new NotFoundException();
        }
        /*if (!foundUser.isVerified()) {
            throw new UnauthorizedException();
        }*/

        if (pbkdf2Hash.verify(password.toCharArray(), foundUser.getPassword())) {
            List<String> userRoles = new ArrayList<>();
            for (Group group : foundUser.getGroup()) {
                userRoles.add(group.getGroupName());
            }
            return jwtStore.generateToken(username, userRoles);
        }else{
            return "somnething";
        }
    }}

    /*public void addUser(User user) throws InvalidUserException {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new InvalidUserException("User has no password");
        } else {
            user.setPassword(pbkdf2Hash.generate(user.getPassword().toCharArray()));
            userDao.create(user);

            try {
                String registrationKey = UUID.randomUUID().toString().replace("-", "");
                User createdUser = userDao.find(user.getUsername());
                registrationKeyDao.create(new RegistrationKey(registrationKey, createdUser));
                emailSender.sendEmail(user, registrationKey);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
*/
