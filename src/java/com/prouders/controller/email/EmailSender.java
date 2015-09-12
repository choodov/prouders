/*
* @(#)EmailSender.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides email sending work. 
*/

package com.prouders.controller.email;

import com.prouders.controller.manager.Config;
import com.prouders.controller.manager.Message;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.Logger;

/**
 * The EmailSender class provides method to send email to user
 * if he forgot his password.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class EmailSender {
    Session session;        // session
    Properties properties;  // properties
    Transport transport;    // transport
    MimeMessage message;    // message
    String subject;         // subject of message
    String body;            // body of message
    
    /*logger for EmailSender class*/
    private static final Logger log = Logger.getLogger(EmailSender.class);
    
    /**
     * constractor without parameters 
     * that get properties / session instance 
     * and initialize message session parameters 
     */
    public EmailSender() {
        properties = System.getProperties();     // get properties instance
        properties.put("mail.smtp.port",         // put SMTP port to properties
                Config.getInstance().getProperty(Config.SMTPPORT));
        properties.put("mail.smtp.auth", "true");// authorization - true
        properties.put("mail.smtp.starttls.enable", "true"); // tls - true
        log.info("Prouperties for email session is added.");
        /* get instance of session with properties */
        session = Session.getDefaultInstance(properties, null);
        log.info("Email session is created");
        message = new MimeMessage(session);      // new message with properties
        log.info("Message instance for email created");
    }
    
    /**
     * method provides sending message with new user password to given email  
     * @param toEmails
     * @param newPwd
     * @throws NoSuchProviderException
     * @throws MessagingException 
     */
    public void sendEmail(String[] toEmails, String newPwd) 
            throws NoSuchProviderException, MessagingException {
        
        /* set parameters for connection to gmail SMTP */
        String fromUser = Config.getInstance().getProperty(Config.EMAILUSER);
        String fromUserEmailPassword 
                = Config.getInstance().getProperty(Config.EMAILPWD);
        String emailHost = Config.getInstance().getProperty(Config.EMAILHOST);
        /* get parameters for message from message.properties */
        subject = Message.getInstance().getProperty(Message.EMAIL_SUBJECT);
        body = Message.getInstance().getProperty(Message.EMAIL_BODY) 
                + newPwd;
 
        /* set message parameters */
        for (int i = 0; i < toEmails.length; i++)
        {
            message.addRecipient(javax.mail.Message.RecipientType.TO, 
                    new InternetAddress(toEmails[i]));
        }
        message.setSubject(subject);
        message.setFrom(Config.getInstance().getProperty(Config.EMAILFROM));
        message.setText(body);
        log.info("Message is created.");

        /* create transport and send prepared message */
        transport = session.getTransport("smtp");
        transport.connect(emailHost, fromUser, fromUserEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        log.info("Message is sended.");
    }
}