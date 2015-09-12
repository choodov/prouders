/*
* @(#)NewStoryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for New Story page. 
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
 * The NewStoryCommand class provides New Story command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class NewStoryCommand implements ICommand{
    
    /*logger for NewStoryCommand class*/
    private static final Logger log 
            = Logger.getLogger(NewStoryCommand.class);
    
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
        
        String page;
        List<Community> communityList;                    // list of communities
        communityList = CommunityService.readAllCommunity();// get all community
        
        request.setAttribute("communityList", communityList); // set attribute
        log.info("Community list for registration page created.");
        page = Config.getInstance().getProperty(Config.NEWSTORY);
        
        return page;
    }
}
