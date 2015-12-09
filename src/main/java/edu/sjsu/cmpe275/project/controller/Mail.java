package edu.sjsu.cmpe275.project.controller;

/**
 * Created by Jihirsha on 12/8/2015.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Mail {

    private static final Logger logger = LoggerFactory.getLogger(Mail.class);
    public void sendEmail(String strRecepientAddress,String strMessage)
    {
        final String username = "cmpe275@yahoo.com";
        final String password = "sjsufall15";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("cmpe275@yahoo.com"));

            message.setRecipient(Message.RecipientType.TO, new InternetAddress(strRecepientAddress));
            message.setSubject("You are invited to collaborate");
            StringBuilder str = new StringBuilder();
            str.append("Hi,") ;
            str.append("\n\n") ;
            str.append("You are invited to collaborate in a project.You can login via following link:");
            str.append("\n\n");
            str.append(strMessage);
            str.append("\n\n") ;
            str.append("Thanks,") ;
            str.append("\n") ;
            str.append("Project Manager") ;
            message.setText(str.toString());
            Transport.send(message);

        } catch (MessagingException e) {
            logger.error("Exception in sending mail:" + e.getMessage());
        }
    }
}
