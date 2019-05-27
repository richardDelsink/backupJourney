/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.sun.mail.util.MailConnectException;
import domain.User;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@Stateless
public class EmailSender {

   /* @Resource(name = "java/mail/swhp")
    Session mailSession;*/

  /*  public void sendEmail(User user, String registrationKey) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(mailSession);

        message.setSubject("Welcome to TripJourney");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("journeyj62@gmail.com", "journeyj62"));
        message.setText("Congratz with you new account! \nClick here to verify your email: "
                + "http://localhost:4200/verify/" + registrationKey);
        Transport.send(message);
    }*/
  private Session session;

    public void sendEmail(User user, String registrationKey){
        String sender = "Rekeningrijder@noreply.com";
        String to = "journeyj62@gmail.com";
        String subject = "Verify email rekeningrijder";
        String bodyText = "Click here to verify your email \nhttp://localhost:8080/RekeningAdministratie/api/account/verify/"+registrationKey;




        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("journeyj62@gmail.com", "qwe123QWE!@#");
                    }
                });



        try {
            // Create the message
            Message message = new MimeMessage(session);

            // Set the message's subject
            message.setSubject(subject);

            //set from (String)
            message.setFrom(InternetAddress.parse(sender, false)[0]);

            // Adjust to, cc, bcc: comma-separated Strings of email addresses
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Insert the message's body
            messageBodyPart.setText(bodyText);

            // create a multipart for different parts of the message
            Multipart multipart = new MimeMultipart();

            // add message's body to multipart
            multipart.addBodyPart(messageBodyPart);

            // This is not mandatory, however, it is a good
            // practice to indicate the software which
            // constructed the message.
            message.setHeader("X-Mailer", "Java Mail API");

            // set multipart as content of the message
            message.setContent(multipart);

            // Adjust the date of sending the message
            message.setSentDate(new Date());

            // Send message
            Transport.send(message);

        } catch (MessagingException  mex) {
            mex.printStackTrace();
            mex.getCause();
        }
    }
}
