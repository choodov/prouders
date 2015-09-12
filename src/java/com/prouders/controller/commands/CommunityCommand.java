/*
* @(#)CommunityCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Community page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderCommunitiesDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
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
 * The CommunityCommand class provides Community command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class CommunityCommand implements ICommand{
    
    /*logger for CommunityCommand class*/
    private static final Logger log = Logger.getLogger(CommunityCommand.class);
    
    /**
     * method generates like attribute list 
     * of all stories for corrent community 
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
        List<Story> storyList;  // list of stories
        Community community;       // community name attribute
        DAOFactory factory = DAOFactory.getDAOFactory();
        HttpSession session = request.getSession(true);
        IProuderCommunitiesDAO prouderCommunitiesDAO;
        
        community = CommunityService.readCommunityByName
                (request.getParameter("community"));// get community attribute
        /* get all stories for given community */
        storyList = StoryService.readAllStoryByCommunityName(community.getName());
        
        Prouder prouder = (Prouder)session.getAttribute("prouder");
        
        try {
            prouderCommunitiesDAO = factory.getProuderCommunitiesDAO();

            if(prouder != null) {
                if(prouderCommunitiesDAO.check(prouder, community)) {
                    request.setAttribute("prouderCommunities", "true");
                }
            } 

            /* if list of stories is not empty - set attributes and page */
            if(!storyList.isEmpty()) {
                request.setAttribute("storyList", storyList);
            } else {
                /* if list of stories is empty - error message plus page*/
                request.setAttribute("amptyStoryList", "emptylist");
            }  
            request.setAttribute("community", community.getName());
            log.info("All attributes for Community page are setted.");
            /* set Community page */
            page = Config.getInstance().getProperty(Config.COMMUNITY);
            
        } catch (SQLException ex) {
                log.error("SQLException while check prouderCommunity: " + ex);
        }
        
        return page;
    }
}
