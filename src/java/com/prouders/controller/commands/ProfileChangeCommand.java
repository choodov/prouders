/*
* @(#)ProfileChangeCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for changing user Profile. 
*/
package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderCommunitiesDAO;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.services.CommunityService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The ProfileChangeCommand class provides ProfileChange command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProfileChangeCommand implements ICommand{
    /* final fields for attributes in registration form */
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CONFPASSWORD = "confpassword";
    
    private final DAOFactory factory = DAOFactory.getDAOFactory();// DAO factory
    private IProuderDAO prouderDAO;                   // IProuderDAO instance

    /*logger for ProfileChangeCommand class*/
    private static final Logger log 
            = Logger.getLogger(ProfileChangeCommand.class);
    
    /**
     * method forword to user profile page or admin page 
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;                      // new page name
        HttpSession session 
                = request.getSession(true);      // get session
        IProuderCommunitiesDAO prouderCommunities;
        List<Community> communityList = new ArrayList<>();
        List<Long> communitiesIdList;
        
        Prouder prouder = (Prouder) session.getAttribute("prouder");
             
        String checkEmailError = "false";
        String checkPasswordsError = "false";
        
        boolean checkFlag = false;    // flag for all errors
        
        /* if email not valid - error + checkFlag = true */
        if(!checkEmail(request.getParameter(EMAIL))) {
            checkEmailError = "true";
            checkFlag = true;
            log.info("ProfileChange: email checking failed.");
        }
        
        /* if password not valid - error + checkFlag = true */
        if(!checkPasswords(request.getParameter(PASSWORD),
                request.getParameter(CONFPASSWORD))) {
            checkPasswordsError = "true";
            checkFlag = true;
            log.info("ProfileChange: password checking failed.");
        }
       
        /* if at least one checking fail - return to registration page */
        if(checkFlag) {
            
            try {
                prouderCommunities = factory.getProuderCommunitiesDAO();
                communitiesIdList = prouderCommunities.getAllCommunitiesID
                                (prouder.getID());
                /* use of functional oparation: */
                communitiesIdList.stream().forEach((item) -> {
                    communityList.add(CommunityService.readCommunity(item));
                });
            } catch (SQLException ex){
                log.error("SQLException while getAllCommunitiesID: " + ex);
            }
            request.setAttribute("communityList", communityList);
            
            request.setAttribute("checkFlag", checkFlag);
            request.setAttribute("checkPasswordsError", checkPasswordsError);
            request.setAttribute("checkEmailError", checkEmailError);
            log.info("Change profile: error attribute setted.");
            page = Config.getInstance().getProperty(Config.PROFILE);
        } else {
            /* all checking executes good - change profile data process */
            /* set new data to Prouders fields */
            prouder.setEmail(request.getParameter(EMAIL));
            prouder.setPassword(request.getParameter(PASSWORD));
            
            log.info("Prouders data updated");

            try {
                prouderDAO = factory.getProuderDAO(); // new prouderDAO
                /* if create(registration) new prouder success */
                if(prouderDAO.update(prouder)) {
                    
                    try {
                        prouderCommunities = factory.getProuderCommunitiesDAO();
                        communitiesIdList 
                                = prouderCommunities.getAllCommunitiesID
                                        (prouder.getID());
                        /* use of functional oparation: */
                        communitiesIdList.stream().forEach((item) -> {
                            communityList.add(CommunityService.readCommunity(item));
                        });
                    } catch (SQLException ex){
                        log.error("SQLException while getAllCommunitiesID: " + ex);
                    }
                    request.setAttribute("communityList", communityList);
                    
                    request.setAttribute("profilechangeMessage", "true");
                    page = Config.getInstance().getProperty(Config.PROFILE);
                    log.info("New Prouder data write to database");
                } else {
                    /* if create(registration) new prouder failed */
                    request.setAttribute("error", "CHANGE_PROFILE_ERROR");
                    page = Config.getInstance().getProperty(Config.ERROR);
                    log.info("Could not write new prouder data to database");
                }
            } catch (SQLException ex) {
                log.error("SQL exception while write "
                        + "new prouder data to database" + ex);
            } 
        }
        
        return page;
    }
    
    /**
     * method check new prouder email
     * @param email String
     * @return boolean
     */
    private boolean checkEmail(String email) {
        /* create pattern for check email: 
         * sDomen - part of email before and after @ 
         */
        String sDomen = "[a-z][a-z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
        /* sDomen2 - end part of email: com, ua, etc. */
        String sDomen2 = "([a-z]){2,4}";
        Pattern pattern     // compile pattern
                = Pattern.compile(sDomen + "@" + sDomen + "\\u002E" + sDomen2);
        Matcher matcher = pattern.matcher(email); // get matcher
        return matcher.matches(); // match pattern and email
    }
    
    /**
     * method check new prouder password
     * @param password String
     * @param confirm String
     * @return boolean
     */
    private boolean checkPasswords(String password, String confirm) {
        /* entered passwords are equal and paswords not empty */
        return (password.equals(confirm) && (password.length() > 0)) 
                ? true : false;
    }
}
