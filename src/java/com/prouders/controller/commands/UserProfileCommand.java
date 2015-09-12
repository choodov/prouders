/*
* @(#)UserProfileCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Profile/Admin page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderCommunitiesDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Story;
import com.prouders.model.services.CommunityService;
import com.prouders.model.services.ProuderService;
import com.prouders.model.services.StoryService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The UserProfileCommand class provides UserProfile command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class UserProfileCommand implements ICommand{

    /*logger for UserProfileCommand class*/
    private static final Logger log = Logger.getLogger(UserProfileCommand.class);
    
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
        
        String page;                            // new page name
        HttpSession session 
                = request.getSession(true);     // get session
        IProuderCommunitiesDAO prouderCommunities;
        List<Story> storyList;                  // list of stories
        List<Prouder> prouderList;              // list of Prouder
        List<Community> communityList = new ArrayList<>();
        List<Long> communitiesIdList;
        
        DAOFactory factory = DAOFactory.getDAOFactory();
        
        Prouder prouder = (Prouder) session.getAttribute("prouder");
        
        if(prouder.getType().getUserType().equals("admin")) {
            /* if user an admin than go to admin page */
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
            /* else go to user profile page */
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
            page = Config.getInstance().getProperty(Config.PROFILE);
        }
        
        return page;
    }
}
