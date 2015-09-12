/*
* @(#)NewCommunityCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for New Community page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICommunityTypeDAO;
import com.prouders.model.entities.CommunityType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The NewCommunityCommand class provides New Community command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class NewCommunityCommand implements ICommand{
    
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
        List<CommunityType> typeList;           // list of CommunityType
        
        try {
            communityTypeDAO = factory.getCommunityTypeDAO();// get countryDAO
            typeList = communityTypeDAO.getAll();            // get all counties
            request.setAttribute("typeList", typeList);      // set attribute
            log.info("CommunityType list for new community page created.");
        } catch (SQLException ex) {
            log.error("Could not create CommunityType list. Exception: " + ex);
        }
        
        return Config.getInstance().getProperty(Config.NEWCOMMUNITY);
    }
}
