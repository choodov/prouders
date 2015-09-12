/*
* @(#)AdminChangeProuderCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for change prouders status or type. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
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
 * The AdminChangeProuderCommand class provides 
 * AdminChangeProuder command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class AdminChangeProuderCommand implements ICommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory();
    
    /*logger for AdminChangeStoryCommand class*/
    private static final Logger log 
            = Logger.getLogger(AdminChangeStoryCommand.class);
    
    /**
     * method generates like attribute list 
     * of all stories
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */    
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;            // new page name
        HttpSession session 
                = request.getSession(true);      // get session
        
        List<Story> storyList;  // list of Story
        List<Prouder> prouderList; // list of Prouder
    
        IProuderDAO prouderDAO;
        IUserTypeDAO userTypeDAO;
        
        Prouder prouder = ProuderService.readProuder(Long.valueOf
                (request.getParameter("prouderID")));
     
        try{
            prouderDAO = factory.getProuderDAO();
            userTypeDAO = factory.getUserTypeDAO();
            
            prouder.setType(userTypeDAO.readByName
                    (request.getParameter("prouderType")));
                                    
            switch(request.getParameter("prouderStatus")) {
                case "ACTIVE": prouder.setStatus(Status.ACTIVE);
                    break;
                case "BLOCKED": prouder.setStatus(Status.BLOCKED);
                    break;
                case "DELETED": prouder.setStatus(Status.DELETED);
                    break;
                default: prouder.setStatus(Status.ACTIVE);
            }
            
            if(prouderDAO.update(prouder)) {
                storyList = StoryService.readAllNewOrPreparedStory();
                log.info("New and prepared stories list setted"
                        + " in profile page");
                prouderList = ProuderService.readAllProuders();
                log.info("Prouders list setted in profile page");

                request.setAttribute("storyList", storyList);
                request.setAttribute("prouderList", prouderList);
                session.setAttribute("userrole", "admin");
                page = Config.getInstance().getProperty(Config.ADMIN);
            } else {
                /* if list of Stories is empty - error */
                request.setAttribute("error", "TEXT_PROUDER_CHANG_ERROR");
                /* set page like Error page */
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("List of Story is empty");
            }
        } catch (SQLException ex) {
            log.error("Could not update story. Exception: " + ex);
        }
        
        return page;
    }
}
