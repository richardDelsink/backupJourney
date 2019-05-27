/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import auth.JWTStore;
import com.nimbusds.jose.JOSEException;
import dao.*;

import domain.Group;

import domain.RegistrationKey;
import domain.User;
import util.PasswordHash;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.NotFoundException;
import util.EmailSender;



public class AuthService {

    private PasswordHash pw;
    @Inject @JPA
    UserDao userDao;


    @Inject @JPA
    Registration registrationKeyDao;

    @Inject
    private GroupDaoJPA groupDao;
    @Inject
    Pbkdf2PasswordHash pbkdf2Hash;

    @Inject
    JWTStore jwtStore;

    @Inject
    EmailSender emailSender;

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
    }

    public void addUser(User user) throws Exception {
        if (user.getPassword() == null || user.getPassword().equals("")) {
            throw new Exception("User has no password");
        } else {
            //User richard = new User( "","rick","","","rd.richard@hotmail.com","",true,"","", "rick");
            user.setPassword(pbkdf2Hash.generate(user.getPassword().toCharArray()));
           // richard.getGroup().add(groupDao.findByName(Group.USER_GROUP));
           // richard.setVerified(false);
            userDao.add(user);

            try {
                String registrationKey = UUID.randomUUID().toString().replace("-", "");
                User createdUser = userDao.findByName(user.getName());
                RegistrationKey r = new RegistrationKey(registrationKey, createdUser);
                registrationKeyDao.add(r);
                emailSender.sendEmail(user, registrationKey);
            } catch (Exception ex) {
                Logger.getLogger(AuthService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void confirmEmail(String key) {
        RegistrationKey registrationKey = registrationKeyDao.findByName(key);
        User user = userDao.findByName(registrationKey.getUser().getName());
        user.setVerified(true);
        userDao.update(user);
    }

}

