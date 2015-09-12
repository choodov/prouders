/*
* @(#)AboutCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for About page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The AboutCommand class provides About command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class AboutCommand implements ICommand{
    
    /*logger for AboutCommand class*/
    private static final Logger log = Logger.getLogger(AboutCommand.class);
    
    /**
     * method generate and set 'about' attribute
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {

        request.setAttribute("about", "true");
        log.info("Attribute 'about' added to responce.");
        return Config.getInstance().getProperty(Config.MAIN);
    }
}
