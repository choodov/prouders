/*
* @(#)ProudStoryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for proud Story. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICommunityDAO;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.dao.interfaces.IProudsCounterDAO;
import com.prouders.model.dao.interfaces.IStoryDAO;
import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Country;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProudsCounter;
import com.prouders.model.entities.Story;
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
 * The ProudStoryCommand class provides ProudStory command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProudStoryCommand implements ICommand{
    
    /*logger for ProudStoryCommand class*/
    private static final Logger log = Logger.getLogger(ProudStoryCommand.class);
    
    /**
     * method change total amount for story/community/country
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */    
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        DAOFactory factory = DAOFactory.getDAOFactory();
        IStoryDAO storyDAO;
        String page = "";                   // page name
        Story story;                        // Story instance
        Country country;
        Community community;
        Prouder prouder;
        IStoryFlagDAO storyFlag;
        ICountryDAO countryDAO;
        ICommunityDAO communityDAO;
        IProudsCounterDAO proudsCounterDAO;
        IUserTypeDAO userTypeDAO;
        IProuderDAO prouderDAO; 
        HttpSession session 
                = request.getSession(true); // get session
        List<Story> storyList;              // list of Story
        List<Long> countList;

        try {
            /* read story by ID */
            storyDAO = factory.getStoryDAO();
            story = StoryService.readStory(Long.valueOf
                    (request.getParameter("storyID")));
                        
            // plus 1 to story total amount
            story.setTotalAmount(story.getTotalAmount() + 1);
            // if story flag = accepted then + 1 to community and country
            if(story.getFlag().getStoryFlag().equals("accepted")) {
                // total_amount +1 community
                communityDAO = factory.getCommunityDAO();
                community = communityDAO.read(story.getCommunity().getID());
                community.setTotalAmount(community.getTotalAmount() + 1);
                communityDAO.update(community);
                // total_amount +1 country
                countryDAO = factory.getCountryDAO();
                country = countryDAO.read(community.getCountry().getCountryID());
                country.setTotalAmount(country.getTotalAmount() + 1);
                countryDAO.update(country);
            } else if(story.getTotalAmount() 
                    > Long.valueOf(Config.getInstance().getProperty
                        (Config.STORY_REGISTR_MIN_COUNT))) {
                // if total amoun > prepared border -> set flag = prepared
                storyFlag = factory.getStoryFlagDAO();                
                story.setFlag(storyFlag.read(2)); // prepared
            } 
            
            if(storyDAO.update(story)) {
            log.info("Total amount of prouds for Story writen to DB");
            
            proudsCounterDAO = factory.getProudsCounterDAO();
            prouder = (Prouder)session.getAttribute("prouder");
            proudsCounterDAO.create(new ProudsCounter(prouder, story));
            log.info("Connection between Prouder "
                    + "and Story write to ProudsCounter");
            
            /* chack and change user status */
            countList = proudsCounterDAO.getAllStoryCount(prouder.getID());
            if(!countList.isEmpty()) {
                /* if prouder makes an active prouder */
                if((prouder.getType().getUserType().equals("prouder")) 
                    && (countList.size() 
                        > Integer.valueOf(Config.getInstance().getProperty
                                (Config.MIN_ACTIVE_PROUDER_PROUDS)))) {
                    /* change user type to active prouder */
                    userTypeDAO = factory.getUserTypeDAO();
                    prouder.setType(userTypeDAO.read(2));
                    try{
                        prouderDAO = factory.getProuderDAO();
                        if(prouderDAO.update(prouder)) {
                            log.info("Prouder type update success");
                        } else {
                            log.info("Cannot update prouder type");
                        }
                    } catch (SQLException ex) {
                        log.error("Excepption while update prouder type: " + ex);
                    }
                } else if((prouder.getType().getUserType().equals("active prouder")) 
                            && (countList.size() 
                                > Integer.valueOf(Config.getInstance().getProperty
                                (Config.MIN_PATRIOT_PROUDS)))){
                    /* change user type to patriot */
                    userTypeDAO = factory.getUserTypeDAO();
                    prouder.setType(userTypeDAO.read(3));
                    try{
                        prouderDAO = factory.getProuderDAO();
                        if(prouderDAO.update(prouder)) {
                            log.info("Prouder type update success");
                        } else {
                            log.info("Cannot update prouder type");
                        }
                    } catch (SQLException ex) {
                        log.error("Excepption while update prouder type: " + ex);
                    }
                }
            }
            
            storyList = StoryService.readAllStory();
            request.setAttribute("storyList", storyList);
            /* set page like Stories page */
            page = Config.getInstance().getProperty(Config.STORIES);
            log.info("Stories list is setted like "
                    + "attribute for Stories page");
            } else {
                request.setAttribute("error", "STORY_ERROR");
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("Could not create connection Prouder-Story.");
            }
        } catch (SQLException ex) {
                log.error("SQL exception while proud "
                        + "story:" + ex);
            }    
       return page;
    }
}