/*
* @(#)StoriesCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Stories page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.entities.Story;
import com.prouders.model.services.StoryService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The StoriesCommand class provides Stories command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class StoriesCommand implements ICommand{
    
    /*logger for StoriesCommand class*/
    private static final Logger log 
            = Logger.getLogger(StoriesCommand.class);
    
    /**
     * method generate Stories list 
     * and set his like attribute storyList
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
        List<Story> storyList 
                = StoryService.readAllStory();  // list of Story
        
        /* if list of stories not empty - set attribute*/
            if(!storyList.isEmpty()) {
                request.setAttribute("storyList", storyList);
                /* set page like Stories page */
                page = Config.getInstance().getProperty(Config.STORIES);
                log.info("Stories list is setted like "
                        + "attribute for Stories page");
            } else {
                /* if list of Stories is empty - error */
                request.setAttribute("error", "STORIES_ERROR");
                /* set page like Error page */
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("List of Story is empty");
            }
        return page;
    }
}
