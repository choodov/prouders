/*
* @(#)LocaleCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for changing language. 
*/

package com.prouders.controller.commands;
import com.prouders.controller.manager.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;


/**
 * The LocaleCommand class provides change Locale command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class LocaleCommand implements ICommand{
    
    /*logger for LocaleCommand class*/
    private static final Logger log 
            = Logger.getLogger(LocaleCommand.class);
    
    /**
     * method change language
     * 
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
        
        String language = request.getParameter("language");

        request.setAttribute("language", language);


        page = Config.getInstance().getProperty(Config.INDEX); // current page    
        return page;
    }
}