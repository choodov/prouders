/*
* @(#)ProuderCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Prouder page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.Story;
import com.prouders.model.entities.UserType;
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
 * The ProuderCommand class provides ProuderCommand command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProuderCommand implements ICommand{
    
    /*logger for ProuderCommand class*/
    private static final Logger log = Logger.getLogger(ProuderCommand.class);
    
    /**
     * method generates like attribute list 
     * of all stories for corrent prouder
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page;            // new page name
        List<Story> storyList;  // list of stories
        Long prouderID;         // prouder ID
        
        DAOFactory factory = DAOFactory.getDAOFactory();
        IUserTypeDAO userTypeDAO;
        HttpSession session = request.getSession(true); 
        List<UserType> userTypeList;
        List<Status> statusList = new ArrayList<>();
        
        prouderID = Long.valueOf(request.getParameter("prouderID"));
        Prouder prouder = ProuderService.readProuder(prouderID);
        Prouder sessionProuder = (Prouder) session.getAttribute("prouder");
        /* get all stories for given prouder */
        storyList = StoryService.readAllStoryByProuder(prouder.getName());
        /* get prouder ID */
            request.setAttribute("prouder", prouder);
            
            try {
                userTypeDAO = factory.getUserTypeDAO();
                userTypeList = userTypeDAO.getAll();
            
                statusList.add(Status.ACTIVE);
                statusList.add(Status.BLOCKED);
                statusList.add(Status.DELETED);

                request.setAttribute("statusList", statusList);
                request.setAttribute("userTypeList", userTypeList);
            } catch (SQLException ex) {
                log.error("Exception while prepare userType:" + ex);
            }
            
            /* if list of stories is not empty - set attributes and page */
            if(!storyList.isEmpty()) {
                request.setAttribute("storyList", storyList);
            } else {
            /* if list of stories is empty - error message plus page*/
            request.setAttribute("amptyStoryList", "emptylist");
            }  
            
            request.setAttribute("sessionProuder", sessionProuder);
            log.info("All attributes for Prouders page are setted.");
            /* set Prouders page */
            page = Config.getInstance().getProperty(Config.PROUDER);
        
        return page;
    }
}
