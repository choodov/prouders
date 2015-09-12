/*
* @(#)LoginCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Login page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Story;
import com.prouders.model.services.ProuderService;
import com.prouders.model.services.StoryService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The LoginCommand class provides Login command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class LoginCommand implements ICommand{
    private static final String EMAIL = "email";        // email attribute
    private static final String PASSWORD = "password";  // password attribute
    private final DAOFactory factory = DAOFactory.getDAOFactory();// DAO factory
    private IProuderDAO prouderDAO;                     // IProuderDAO instance
    
    /*logger for LoginCommand class*/
    private static final Logger log 
            = Logger.getLogger(LoginCommand.class);
    
    /**
     * method manage Login executes
     * @param request HttpServletRequest
     * @param responce HttpServletResponse
     * @return String
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
    
        String page = null;                      // new page name
        String email 
                = request.getParameter(EMAIL);   // prouder email 
        String password 
                = request.getParameter(PASSWORD);// prouder password
        HttpSession session 
                = request.getSession(true);      // get session
        Prouder prouder;                         // Prouder link
        List<Story> storyList;                   // list of stories
        List<Prouder> prouderList;               // list of Prouder
        
        /* if pushed logoff button - end session and go to index page */
        if(request.getParameter("logoff") != null) {
            session.invalidate();   // end session
            page = Config.getInstance().getProperty(Config.INDEX);
            log.info(email + " is logoff.");
        } else {/* if user login - check email and password */
            try {                      
                prouderDAO = factory.getProuderDAO();   // new prouder DAO
                /* check if exist equals email and password in db */
                if(prouderDAO.checkLogin(email, password)) {

                    prouder = ProuderService.readProuderByEmail(email);

                    if(prouder.getStatus().getValue() != 3) {
                    
                        request.setAttribute("email", email);
                        /* set session attribute */
                        session.setAttribute("prouder", prouder);
                        session.setAttribute("prouderID", prouder.getID());
                        session.setAttribute("user", prouder.getName());

                        /* if prouder is admin - go to admin page */
                        if(prouder.getType().getUserType().equals("admin")) {
                            storyList = StoryService.readAllNewOrPreparedStory();
                            log.info("New and prepared stories list setted"
                                    + " in admin page");
                            prouderList = ProuderService.readAllProuders();
                            log.info("Prouders list setted in admin page");
                            request.setAttribute("storyList", storyList);
                            request.setAttribute("prouderList", prouderList);
                            session.setAttribute("userrole", "admin");
                            page = Config.getInstance().getProperty(Config.ADMIN);
                            log.info("Admin " + email + " login.");
                        } else {
                            /* rutern to index page */
                            session.setAttribute("userrole", "prouder");
                            page = Config.getInstance().getProperty(Config.INDEX);
                            log.info("User " + prouder.getName() + " is logined.");
                        }
                    } else {
                        /* if user has DELETED status - send error */
                        request.setAttribute("error", "LOGIN_DELETED_ERROR");
                        page = Config.getInstance().getProperty(Config.ERROR);
                        log.info("User no exist in db" );
                    }    
                } else {
                    /* if user no exist - send error */
                    request.setAttribute("error", "LOGIN_ERROR");
                    page = Config.getInstance().getProperty(Config.ERROR);
                    log.info("User no exist in db" );
                }
            } catch (SQLException ex) {
                log.error("Could not get user info from db."
                        + " Exception: " + ex);                
            }
        }
    return page;
    }
}
