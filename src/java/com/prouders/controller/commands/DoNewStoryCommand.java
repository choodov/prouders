/*
* @(#)DoNewStoryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DoNewStory Command. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IStoryDAO;
import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.Story;
import com.prouders.model.services.CommunityService;
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
 * The DoNewStoryCommand class provides
 * new story creation
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class DoNewStoryCommand implements ICommand{
    
    /*logger for NewStoryCommand class*/
    private static final Logger log 
            = Logger.getLogger(NewStoryCommand.class);
    
    /**
     * method greate new Story
     * and return Registration page
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;
        List<Story> storyList;  // list of Story
        Story story = new Story();
        DAOFactory factory = DAOFactory.getDAOFactory();
        IStoryFlagDAO flagDAO;
        HttpSession session = request.getSession(true);        

        try {
            flagDAO = factory.getStoryFlagDAO();
            
            story.setHeader(request.getParameter("name"));
            story.setBody(request.getParameter("description"));
            story.setProuder((Prouder) session.getAttribute("prouder"));            
            story.setCommunity(CommunityService.readCommunityByName
                    (request.getParameter("community"))); //
            story.setTotalAmount(0L);            
            story.setFlag(flagDAO.read(1));
            story.setStatus(Status.ACTIVE);
  
            IStoryDAO storyDAO = factory.getStoryDAO(); // new storyDAO
                /* if create(registration) new story success */
                if(storyDAO.create(story)) {
                    storyList  = StoryService.readAllStory();
                    request.setAttribute("storyList", storyList);
                    /* set page like Stories page */
                    page = Config.getInstance().getProperty(Config.STORIES);
                    log.info("Stories list is setted like "
                            + "attribute for Stories page");
                    log.info("New Story write to database");
                } else {
                    /* if create(registration) new prouder failed */
                    request.setAttribute("error", "CREATE_NEW_STORY_ERROR");
                    page = Config.getInstance().getProperty(Config.ERROR);
                    log.info("Could not write new story to database");
                }
                
            } catch (SQLException ex) {
                log.error("SQL exception while write "
                        + "new story to database" + ex);
            }
        
        return page;
    }
}
