/*
* @(#)AddCommunityToProuder.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides AddCommunityToProuder Command. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderCommunitiesDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProuderCommunities;
import com.prouders.model.services.CommunityService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 * The AddCommunityToProuder class provides
 * additing community to prouder communities
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class AddCommunityToProuder implements ICommand{
    
    /*logger for AddCommunityToProuder class*/
    private static final Logger log 
            = Logger.getLogger(AddCommunityToProuder.class);
    
    /**
     * method add community to prouder communities
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        DAOFactory factory 
                = DAOFactory.getDAOFactory();        // DAO factory
        IProuderCommunitiesDAO prouderCommunitiesDAO;// IProuderCommunitiesDAO
        ProuderCommunities prouderCommunities 
                = new ProuderCommunities();          // ProuderCommunities
        Prouder prouder;                             // Prouder
        Community community;                         // Community
        List<Community> communityList;               // get all community
        String page = null;
        HttpSession session  = request.getSession(true);
        
        try {
            prouder = (Prouder)session.getAttribute("prouder");
            community = CommunityService.readCommunityByName
                    (request.getParameter("communityname"));
            
            prouderCommunitiesDAO = factory.getProuderCommunitiesDAO();
            
            prouderCommunities.setProuder(prouder);
            prouderCommunities.setCommunity(community);

            /* if add community to prouder success */
            if(prouderCommunitiesDAO.create(prouderCommunities)) {

                communityList = CommunityService.readAllCommunity();
                request.setAttribute("communityList", communityList);
                /* set page like Communities page */
                page = Config.getInstance().getProperty
                                (Config.COMMUNITIES);
                log.info("Communities list is setted like "
                        + "attribute for Communities page");
                log.info("New ProuderCommunity write to database");
            } else {
                /* if add community to prouder failed */
                request.setAttribute("error", "ADD_COMM_TO_PROUDER_ERROR");
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("Could not write new community to database");
            }

            } catch (SQLException ex) {
                log.error("SQL exception while write "
                        + "new community to database" + ex);
            }
        
        return page;
    }
}
