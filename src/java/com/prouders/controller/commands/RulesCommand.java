/*
* @(#)RulesCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Rules page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.controller.manager.Message;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The RulesCommand class provides Rules command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class RulesCommand implements ICommand{
    
    /*logger for RulesCommand class*/
    private static final Logger log = Logger.getLogger(RulesCommand.class);
    
    /**
     * method generate and set 'rules' attribute
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
                
        request.setAttribute("rules", "true");
        log.info("Attribute 'rules' added to responce.");
        return Config.getInstance().getProperty(Config.MAIN);
    }
}
