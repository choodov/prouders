/*
* @(#)ProuderService.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "prouders" MySQL database. 
*/

package com.prouders.model.services;

import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.entities.Prouder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The ProuderService class provides several methods to work with DAO layer:
 * readProuder(), readProuderByEmail(), readAllProuders()
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProuderService {
    private static final DAOFactory factory 
            = DAOFactory.getDAOFactory();       // getting DAO factory
    
    /*logger for ProuderService class*/
    private static final Logger log 
            = Logger.getLogger(ProuderService.class);
    
    /**
     * synchronized method that read all data about Prouder
     * @param prouderID Long
     * @return Community
     */
    public static synchronized Prouder readProuder(Long prouderID) {
        IProuderDAO prouderDAO;     // link to prouderDAO
        IUserTypeDAO userTypeDAO;   // link to userTypeDAO
        ICountryDAO countryDAO;     // link to countryDAO
        Prouder prouder = null;     // link to Prouder object

        try {
            prouderDAO = factory.getProuderDAO();   // new prouderDAO
            countryDAO = factory.getCountryDAO();   // new countryDAO
            userTypeDAO = factory.getUserTypeDAO(); // new userTypeDAO

            /* read Prouder by ID */
            prouder = prouderDAO.read(prouderID);
            /* fill country and set this country to prouder */
            prouder.setCountry(countryDAO.fill(prouder.getCountry()));
            /* fill user type and set this type to prouder */
            prouder.setType(userTypeDAO.fill(prouder.getType()));

        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
    return prouder;
    }
    
    /**
     * synchronized method that read Prouder by email
     * @param email String
     * @return Prouder
     */
    public static synchronized Prouder readProuderByEmail(String email) {
        IProuderDAO prouderDAO;     // link to prouderDAO
        IUserTypeDAO userTypeDAO;   // link to userTypeDAO
        ICountryDAO countryDAO;     // link to countryDAO    
        Prouder prouder = null;     // link to Prouder object

        try {
            prouderDAO = factory.getProuderDAO();   // new prouderDAO
            countryDAO = factory.getCountryDAO();   // new countryDAO    
            userTypeDAO = factory.getUserTypeDAO(); // new userTypeDAO

            /* read Prouder by email */
            prouder = prouderDAO.readByEmail(email);
            /* fill country and set this country to prouder */
            prouder.setCountry(countryDAO.fill(prouder.getCountry()));
            /* fill user type and set this type to prouder */
            prouder.setType(userTypeDAO.fill(prouder.getType()));

        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return prouder;
    }
    
       
    /**
     * synchronized method that read all Prouder
     * @return List
     */
    public static synchronized List<Prouder> readAllProuders() {
        IProuderDAO prouderDAO;                 // link to prouderDAO
        IUserTypeDAO userTypeDAO;               // link to userTypeDAO
        ICountryDAO countryDAO;                 // link to countryDAO
        List<Prouder> list = new ArrayList<>(); // new ArrayList of Prouder

        try {
            prouderDAO = factory.getProuderDAO();   // new prouderDAO
            countryDAO = factory.getCountryDAO();   // new countryDAO
            userTypeDAO = factory.getUserTypeDAO(); // new userTypeDAO

            /* get all Prouder list */
            list = prouderDAO.getAll();
            for(Prouder prouder : list) {
                /* fill country and set this country to prouder */
                prouder.setCountry(countryDAO.fill(prouder.getCountry()));
                /* fill user type and set this type to prouder */
                prouder.setType(userTypeDAO.fill(prouder.getType()));
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return list;
    }
}
