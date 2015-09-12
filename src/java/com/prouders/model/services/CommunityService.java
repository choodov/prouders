/*
* @(#)CommunityService.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "Community" MySQL database. 
*/

package com.prouders.model.services;

import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICommunityDAO;
import com.prouders.model.dao.interfaces.ICommunityTypeDAO;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.entities.Community;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The CommunityService class provides several methods to work with DAO layer:
 * readCommunity(), readAllCommunity(), readTopCommunity()
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class CommunityService {
    private static final DAOFactory factory 
            = DAOFactory.getDAOFactory();   // getting DAO factory
    
    /*logger for CommunityService class*/
    private static final Logger log 
            = Logger.getLogger(CommunityService.class);
    
    /**
     * synchronized method that read all data about Community
     * @param communityID Long
     * @return Community
     */
    public static synchronized Community readCommunity(Long communityID) {
        ICommunityDAO communityDAO;         // link to communityDAO
        ICommunityTypeDAO communityTypeDAO; // link to communityTypeDAO
        ICountryDAO countryDAO;             // link to countryDAO
        Community community = null;         // link to Community object

        try {
            communityDAO = factory.getCommunityDAO();   // new communityDAO
            countryDAO = factory.getCountryDAO();       // new countryDAO
            communityTypeDAO 
                    = factory.getCommunityTypeDAO();    // new communityTypeDAO

            /* read Community by ID */
            community = communityDAO.read(communityID);
            /* fill country and set this country to community */
            community.setCountry(countryDAO.fill(community.getCountry()));
            /* fill community type and set this type to community */
            community.setType(communityTypeDAO.fill(community.getType()));

        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return community;
    }
    
    /**
     * synchronized method that read all data about Community
     * @param name String
     * @return Community
     */
    public static synchronized Community readCommunityByName(String name) {
        ICommunityDAO communityDAO;         // link to communityDAO
        ICommunityTypeDAO communityTypeDAO; // link to communityTypeDAO
        ICountryDAO countryDAO;             // link to countryDAO
        Community community = null;         // link to Community object

        try {
            communityDAO = factory.getCommunityDAO();   // new communityDAO
            countryDAO = factory.getCountryDAO();       // new countryDAO
            communityTypeDAO 
                    = factory.getCommunityTypeDAO();    // new communityTypeDAO

            /* read Community by ID */
            community = communityDAO.readByName(name);
            /* fill country and set this country to community */
            community.setCountry(countryDAO.fill(community.getCountry()));
            /* fill community type and set this type to community */
            community.setType(communityTypeDAO.fill(community.getType()));

        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return community;
    }
    
    /**
     * synchronized method that read all Communities
     * @return List
     */
    public static synchronized List<Community> readAllCommunity() {
        ICommunityDAO communityDAO;              // link to communityDAO
        ICommunityTypeDAO communityTypeDAO;      // link to communityTypeDAO
        ICountryDAO countryDAO;                  // link to countryDAO
        List<Community> list = new ArrayList<>();// new ArrayList of Countries

        try {
            communityDAO = factory.getCommunityDAO();   // new communityDAO
            countryDAO = factory.getCountryDAO();       // new countryDAO
            communityTypeDAO 
                    = factory.getCommunityTypeDAO();    // new communityTypeDAO

            /* get all Community list */
            list = communityDAO.getAll();
            for(Community community : list) {
                /* fill country and set this country to community */
                community.setCountry(countryDAO.fill(community.getCountry()));
                /* fill community type and set this type to community */
                community.setType(communityTypeDAO.fill(community.getType()));
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return list;
    }
    
    /**
     * synchronized method that read top Communities
     * @param top int
     * @return List
     */
    public static synchronized List<Community> readTopCommunity(int top) {
        ICommunityDAO communityDAO;              // link to communityDAO
        ICommunityTypeDAO communityTypeDAO;      // link to communityTypeDAO
        ICountryDAO countryDAO;                  // link to countryDAO
        List<Community> list = new ArrayList<>();// new ArrayList of Countries

        try {
            communityDAO = factory.getCommunityDAO();   // new communityDAO
            countryDAO = factory.getCountryDAO();       // new countryDAO
            communityTypeDAO 
                    = factory.getCommunityTypeDAO();    // new communityTypeDAO

            /* get top Community list */
            list = communityDAO.geTop(top);
            for(Community community : list) {
                /* fill country and set this country to community */
                community.setCountry(countryDAO.fill(community.getCountry()));
                 /* fill community type and set this type to community */
                community.setType(communityTypeDAO.fill(community.getType()));
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return list;
    }
}
