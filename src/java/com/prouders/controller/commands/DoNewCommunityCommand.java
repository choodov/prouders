/*
* @(#)DoNewCommunityCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DoNewCommunity Command. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICommunityDAO;
import com.prouders.model.dao.interfaces.ICommunityTypeDAO;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
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
 * The DoNewCommunityCommand class provides
 * new community creation
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class DoNewCommunityCommand implements ICommand{
    
    /*logger for NewCommunityCommand class*/
    private static final Logger log 
            = Logger.getLogger(NewCommunityCommand.class);
    
    /**
     * method set Country list for Registration page
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
        
        DAOFactory factory 
                = DAOFactory.getDAOFactory();   // DAO factory
        ICommunityTypeDAO communityTypeDAO;     // ICommunityTypeDAO instance
        Community community = new Community();
        List<Community> communityList;          // get all community
        String page = null;
        HttpSession session  = request.getSession(true);
        
        try {
            // create new community entity
            ICommunityDAO communityDAO = factory.getCommunityDAO();
            community.setName(request.getParameter("name"));
            community.setDescription(request.getParameter("description"));
            Prouder prouder = (Prouder) session.getAttribute("prouder");
            ICountryDAO countryDAO = factory.getCountryDAO();            
            community.setCountry(countryDAO.read
                    (prouder.getCountry().getCountryID()));
            community.setTotalAmount(0L);
            communityTypeDAO = factory.getCommunityTypeDAO();
            community.setType(communityTypeDAO.readByType
                    (request.getParameter("type")));
            community.setStatus(Status.ACTIVE);
            
                /* if create(registration) new story success */
                if(communityDAO.create(community)) {
                    communityList = CommunityService.readAllCommunity();
                    request.setAttribute("communityList", communityList);
                    /* set page like Communities page */
                    page = Config.getInstance().getProperty(Config.COMMUNITIES);
                    log.info("Communities list is setted like "
                            + "attribute for Communities page");
                    log.info("New Community write to database");
                } else {
                    /* if create(registration) new prouder failed */
                    request.setAttribute("error", "CREATE_NEW_COMM_ERROR");
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
