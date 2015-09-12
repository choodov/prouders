/*
* @(#)RegistrationCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Registration page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.entities.Country;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The RegistrationCommand class provides Registration command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class RegistrationCommand implements ICommand{
    
    /*logger for RegistrationCommand class*/
    private static final Logger log 
            = Logger.getLogger(RegistrationCommand.class);
    
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
        
        DAOFactory factory 
                = DAOFactory.getDAOFactory();   // DAO factory
        ICountryDAO countryDAO;                 // ICountryDAO instance
        List<Country> list;                     // list of Countries
        
        try {
            countryDAO = factory.getCountryDAO();       // get countryDAO
            list = countryDAO.getAll();                 // get all counties
            request.setAttribute("countryList", list);  // set attribute
            log.info("Countries list for registration page created.");
        } catch (SQLException ex) {
            log.error("Could not create country list. Exception: " + ex);
        }
        
        return Config.getInstance().getProperty(Config.REGISTRATION);
    }
}
