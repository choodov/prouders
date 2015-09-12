/*
* @(#)StoryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Story page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.controller.manager.Message;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProudsCounterDAO;
import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.Story;
import com.prouders.model.entities.StoryFlag;
import com.prouders.model.services.StoryService;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The StoryCommand class provides Story command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class StoryCommand implements ICommand{
    
    /*logger for StoryCommand class*/
    private static final Logger log = Logger.getLogger(StoryCommand.class);
    
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
        
        String page = null;    // page name
        Story story;    // Story instance
        Prouder prouder;
        DAOFactory factory = DAOFactory.getDAOFactory();
        IProudsCounterDAO proudsCounterDAO;
        IStoryFlagDAO storyFlagDAO;
        HttpSession session = request.getSession(true); 
        List<StoryFlag> storyFalgList = null;
        List<Status> statusList = new ArrayList<>();
        
        try {
            /* read story by ID */
            story = StoryService.readStory(Long.valueOf
                    (request.getParameter("storyID")));
            prouder = (Prouder)session.getAttribute("prouder");
            proudsCounterDAO = factory.getProudsCounterDAO();
            /* if story exist - set attribute */
            if(story != null) {
                if(prouder != null) {
                    if(!proudsCounterDAO.check(prouder, story)) {
                    } else {
                        request.setAttribute("proudercounter", "true");
                    }                
                }

                storyFlagDAO = factory.getStoryFlagDAO();
                storyFalgList = storyFlagDAO.getAll();

                statusList.add(Status.ACTIVE);
                statusList.add(Status.BLOCKED);
                statusList.add(Status.DELETED);

                request.setAttribute("statusList", statusList);
                request.setAttribute("storyFalgList", storyFalgList);
                
                request.setAttribute("story", story);
                page = Config.getInstance().getProperty(Config.STORY);
                log.info(story.getHeader() + " is finded");
            } else {
                /* else - no story */
                request.setAttribute("error", "STORY_ERROR");
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("No story with ID: " + request.getParameter("storyID"));
            }
        } catch(SQLException ex) {
            log.error("In StoryCommand exception: " + ex);
        }
       return page;
    }
}
