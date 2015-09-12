/*
* @(#)ICommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file interface for all commans. 
*/
package com.prouders.controller.commands;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The ICommand interface with one method execute.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface ICommand {
    
    /**
     * method execute some command
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    public String execute(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException;
}
