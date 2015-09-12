/*
* @(#)MissingCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Missing command page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The MissingCommand class provides method
 * that tranship to index page.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MissingCommand implements ICommand{
    
    /**
     * method tranship user to index page.
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        return Config.getInstance().getProperty(Config.INDEX);
    }
}
