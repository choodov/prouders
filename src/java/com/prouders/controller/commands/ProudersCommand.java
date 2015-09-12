/*
* @(#)ProudersCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Prouders page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.entities.Prouder;
import com.prouders.model.services.ProuderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The ProudersCommand class provides Prouders command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProudersCommand implements ICommand{
    
    /*logger for ProudersCommand class*/
    private static final Logger log 
            = Logger.getLogger(ProudersCommand.class);
    
    /**
     * method generate Prouders list 
     * and set his like attribute prouderList
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page;                                // new page name
        List<Prouder> prouderList 
                = ProuderService.readAllProuders(); // list of Prouder
        /* if list of prouders not empty - set attribute*/
            if(!prouderList.isEmpty()) {
                request.setAttribute("prouderList", prouderList);
                /* set page like Prouder page */
                page = Config.getInstance().getProperty(Config.PROUDERS);
                log.info("Prouders list is setted like "
                        + "attribute for Prouders page");
            } else {
                /* if list of Prouders is empty - error */
                request.setAttribute("error", "PROUDERS_ERROR");
                /* set page like Error page */
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("List of Prouders is empty");
            }
        return page;
    }
}
