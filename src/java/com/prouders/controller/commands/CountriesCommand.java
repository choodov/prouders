/*
* @(#)CountriesCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Countries page. 
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
 * The CountriesCommand class provides Countries command execution.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class CountriesCommand implements ICommand{
    private final DAOFactory factory = DAOFactory.getDAOFactory();// DAO factory
    private ICountryDAO countryDAO;                         // ICountryDAO instance
    
    /*logger for CommunitiesCommand class*/
    private static final Logger log = Logger.getLogger(CountriesCommand.class);
    
    /**
     * method generate Country list 
     * and set his like attribute countryList
     * @param request
     * @param responce
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse responce) throws ServletException, IOException {
        
        String page = null;         // new page name
        List<Country> countryList;  // list of Country
        
        try {
            countryDAO = factory.getCountryDAO();   // new countryDAO
            countryList = countryDAO.getAll();      // get all countries
            /* if list of Country not empty - set attribute*/
            if(!countryList.isEmpty()) {
                request.setAttribute("countryList", countryList);
                /* set page like Countries page */
                page = Config.getInstance().getProperty(Config.COUNTRIES);
                log.info("Countries list is setted like "
                        + "attribute for Countries page");
            } else {
                /* if list of Countries is empty - error */
                request.setAttribute("error", "COUNTRIES_ERROR");
                /* set page like Error page */
                page = Config.getInstance().getProperty(Config.ERROR);
                log.info("List of Community is empty");
            }
        } catch (SQLException ex) {
            log.error("Could not get all country, exception: " + ex);
        }      
        return page;
    }
}
