/*
* @(#)ForgotPasswordCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for ForgotPassword page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.controller.email.EmailSender;
import com.prouders.controller.email.PasswordGenerator;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The ForgotPasswordCommand class provides 
 * ForgotPasswordCommand command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ForgotPasswordCommand implements ICommand{
    private static final String EMAIL = "email";    // email attribute
    private final DAOFactory factory = DAOFactory.getDAOFactory();// DAO factory
    private IProuderDAO prouderDAO;                 // IProuderDAO instance
    
    /*logger for ForgotPasswordCommand class*/
    private static final Logger log 
            = Logger.getLogger(ForgotPasswordCommand.class);
    
    /**
     * method generate new password and send him to user by email
     * @param request HttpServletRequest
     * @param responce HttpServletResponse
     * @return String
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;     // new page name

        try {
            prouderDAO = factory.getProuderDAO();   // new prouderDAO
            /* if email not null and exist in db - send */
            if(request.getParameter(EMAIL) != null){
                if(prouderDAO.checkEmail(request.getParameter(EMAIL))) {
                    
                EmailSender sender = new EmailSender();     // new sender
                String[] toEmails = new String[1];          // list of resivers
                toEmails[0] = request.getParameter(EMAIL);  // email address
                
                /* generate new password */
                String newPwd 
                    = PasswordGenerator.getNewPwd(request.getParameter(EMAIL));
                log.info("New password is genereted.");
                sender.sendEmail(toEmails, newPwd); // send mail
                log.info("Email with new password sended");
                /* set attribute message and set page */
                request.setAttribute("message", "true");
                page = Config.getInstance().getProperty(Config.FORGOTPWD);
                } else {
                    /* email not exist in db */
                    request.setAttribute("error", "FORGOTPWD_ERROR");
                    page = Config.getInstance().getProperty(Config.ERROR);
                    log.info("Email not exist in db");
                }
            } else {
                /* if email == null return to page */
                page = Config.getInstance().getProperty(Config.FORGOTPWD);
            }            
        } catch (SQLException | MessagingException ex) {
            log.error("Exception while prapare and send email: " + ex);
        }      
        return page;
    }
}
