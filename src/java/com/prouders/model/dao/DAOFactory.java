/*
* @(#)DAOFactory.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAOFactory class 
* with abstract methods for getting DAO layer methods. 
*/

package com.prouders.model.dao;

import com.prouders.model.dao.interfaces.*;
import java.sql.SQLException;

/**
 * The DAOFactory class provides
 * abstract mehotds for getting DAO layer methods.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public abstract class DAOFactory {
    
    /**
     * abstract method for getting CommunityDAO
     * @return ICommunityDAO
     * @throws SQLException 
     */
    public abstract ICommunityDAO getCommunityDAO() throws SQLException;
    
    /**
     * abstract method for getting CommunityType
     * @return ICommunityTypeDAO
     * @throws SQLException 
     */
    public abstract ICommunityTypeDAO getCommunityTypeDAO() throws SQLException;
    
    /**
     * abstract method for getting CountryDAO
     * @return ICountryDAO
     * @throws SQLException 
     */
    public abstract ICountryDAO getCountryDAO() throws SQLException;
    
    /**
     * abstract method for getting ProuderDAO
     * @return IProuderDAO
     * @throws SQLException 
     */
    public abstract IProuderDAO getProuderDAO() throws SQLException;
    
    /**
     * abstract method for getting ProuderCommunitiesDAO
     * @return IProuderCommunitiesDAO
     * @throws SQLException 
     */
    public abstract IProuderCommunitiesDAO getProuderCommunitiesDAO() 
            throws SQLException;
    
    /**
     * abstract method for getting ProudsCounterDAO
     * @return IProudsCounterDAO
     * @throws SQLException 
     */
    public abstract IProudsCounterDAO getProudsCounterDAO() throws SQLException;
    
    /**
     * abstract method for getting StoryDAO
     * @return IStoryDAO
     * @throws SQLException 
     */
    public abstract IStoryDAO getStoryDAO() throws SQLException;
    
    /**
     * abstract method for getting StoryFlagDAO
     * @return IStoryFlagDAO
     * @throws SQLException 
     */
    public abstract IStoryFlagDAO getStoryFlagDAO() throws SQLException;
    
    /**
     * abstract method for getting UserTypeDAO
     * @return IUserTypeDAO
     * @throws SQLException 
     */
    public abstract IUserTypeDAO getUserTypeDAO() throws SQLException;

    /**
     * method return DAOFactory instance
     * @return 
     */
    public static DAOFactory getDAOFactory() {
        return new MySQLDAOFactory();
    }
}
