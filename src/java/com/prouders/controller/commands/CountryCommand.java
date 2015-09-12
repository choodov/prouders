/*
* @(#)CountryCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Country page. 
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
 * The CountryCommand class provides Country command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class CountryCommand implements ICommand{
    
    /*logger for CountryCommand class*/
    private static final Logger log = Logger.getLogger(CountryCommand.class);

    /**
     * method generates like attribute list 
     * of all stories for corrent country 
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page;    // new page name
        String country; // country name attribute
        Long countryID; // country ID
        
        country = request.getParameter("country");// get country attribute
        /* get all stories for given country */
        List<Story> storyList = StoryService.readAllStoryByCountryName(country);
        /* get country ID */
        countryID = Long.valueOf(request.getParameter("countryID"));
        
        /* if list of stories is not empty - set attributes and page */
        if(!storyList.isEmpty()) {
            request.setAttribute("country", country);
            request.setAttribute("countryID", countryID);
            request.setAttribute("storyList", storyList);
            log.info("All attributes for Country page are setted.");
            /* set Countries page */
            page = Config.getInstance().getProperty(Config.COUNTRIES);
        } else {
            /* if list of stories is empty - error message plus page*/
            request.setAttribute("error", "COUNTRY_ERROR");
            page = Config.getInstance().getProperty(Config.ERROR);
            log.error("List of stories for Country " 
                    + country + " is empty");
        }
        return page;
    }
}
