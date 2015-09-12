/*
* @(#)CommunitiesCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Communities page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.entities.Community;
import com.prouders.model.services.CommunityService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The CommunitiesCommand class provides Communities command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class CommunitiesCommand implements ICommand{
   
    /*logger for CommunitiesCommand class*/
    private static final Logger log 
            = Logger.getLogger(CommunitiesCommand.class);
    
    /**
     * method generate Community list 
     * and set his like attribute communityList
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page;                    // new page name
        List<Community> communityList;  // list of communities
        communityList = CommunityService.readAllCommunity();// get all community
        
        /* if list of communities not empty - set attribute*/
            if(!communityList.isEmpty()) {
                request.setAttribute("communityList", communityList);
                /* set page like Communities page */
                page = Config.getInstance().getProperty(Config.COMMUNITIES);
                log.info("Communities list is setted like "
                        + "attribute for Communities page");
            } else {
                /* if list of communities is empty - error */
                request.setAttribute("error", "COMMUNITIES_ERROR");
                /* set page like Error page */
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("List of Community is empty");
            }
        return page;
    }
}
