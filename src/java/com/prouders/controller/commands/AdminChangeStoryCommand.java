/*
* @(#)AdminChangeStoryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for change story status or flag. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICommunityDAO;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.dao.interfaces.IStoryDAO;
import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Country;
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
 * The AdminChangeStoryCommand class provides AdminChangeStory command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class AdminChangeStoryCommand implements ICommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory();
    private IStoryDAO storyDAO;
    
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
        
        String page = null;                 // new page name
        HttpSession session 
                = request.getSession(true); // get session
        Story story;
        Country country;
        Community community;
        
        List<Story> storyList;              // list of Story
        List<Prouder> prouderList;          // list of Prouder
        IStoryFlagDAO storyFlagDAO;
        ICountryDAO countryDAO;
        ICommunityDAO communityDAO;
        
        story = StoryService.readStory(Long.valueOf
                    (request.getParameter("storyID")));
        Prouder prouder = (Prouder) session.getAttribute("prouder");
      
        try{
            storyDAO = factory.getStoryDAO();
            storyFlagDAO = factory.getStoryFlagDAO();
            
            story.setFlag(storyFlagDAO.readByName
                    (request.getParameter("storyFlag")));
            if(story.getFlag().getStoryFlag().equals("accepted")) {
                // total_amount community + story total amount
                communityDAO = factory.getCommunityDAO();
                community = communityDAO.read(story.getCommunity().getID());
                community.setTotalAmount(community.getTotalAmount() 
                        + story.getTotalAmount());
                communityDAO.update(community);
                // total_amount country + story total amount
                countryDAO = factory.getCountryDAO();
                country = countryDAO.read(community.getCountry().getCountryID());
                country.setTotalAmount(country.getTotalAmount() 
                        + story.getTotalAmount());
                countryDAO.update(country);
            }
            
            switch(request.getParameter("storyStatus")) {
                case "ACTIVE": story.setStatus(Status.ACTIVE);
                    break;
                case "BLOCKED": story.setStatus(Status.BLOCKED);
                    break;
                case "DELETED": story.setStatus(Status.DELETED);
                    break;
                default: story.setStatus(Status.ACTIVE);
            }
            
            if(storyDAO.update(story)) {
                if(prouder.getType().getUserType().equals("admin")) {
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
                    /* prouder is 'admin patriot' type */
                    storyList = StoryService.readAllStory();
                    request.setAttribute("storyList", storyList);
                    /* set page like Stories page */
                    page = Config.getInstance().getProperty(Config.STORIES);
                    log.info("Stories list is setted like "
                            + "attribute for Stories page");
                }
            } else {
                /* if list of Stories is empty - error */
                request.setAttribute("error", "TEXT_STORY_CHANG_ERROR");
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
