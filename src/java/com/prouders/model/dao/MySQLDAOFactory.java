/*
* @(#)MySQLDAOFactory.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides extends of DAOFactory and realization of abstract methods. 
*/

package com.prouders.model.dao;
import com.prouders.model.dao.interfaces.*;
import com.prouders.model.dao.objects.*;
import java.sql.SQLException;

/**
 * The MySQLDAOFactory class provides MySQL DB DAO Factory 
 * and provide realization of DAOFactory abstract methods. 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLDAOFactory extends DAOFactory{
    
    /**
     * mehtod return MySQLCommunityDAO instance 
     * under ICommunityDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public ICommunityDAO getCommunityDAO() throws SQLException{
        return new MySQLCommunityDAO();
    }
    
    /**
     * mehtod return MySQLCommunityTypeDAO instance 
     * under ICommunityTypeDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public ICommunityTypeDAO getCommunityTypeDAO() throws SQLException{
        return new MySQLCommunityTypeDAO();
    }    
    
    /**
     * mehtod return MySQLCountryDAO instance 
     * under ICountryDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public ICountryDAO getCountryDAO() throws SQLException{
        return new MySQLCountryDAO();
    }
    
    /**
     * mehtod return MySQLProuderDAO instance 
     * under IProuderDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IProuderDAO getProuderDAO() throws SQLException{
        return new MySQLProuderDAO();
    }
    
    /**
     * mehtod return MySQLProuderCommunitiesDAO instance 
     * under IProuderCommunitiesDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IProuderCommunitiesDAO getProuderCommunitiesDAO() throws SQLException{
        return new MySQLProuderCommunitiesDAO();
    }
    
    /**
     * mehtod return MySQLProudsCounterDAO instance 
     * under IProudsCounterDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IProudsCounterDAO getProudsCounterDAO() throws SQLException{
        return new MySQLProudsCounterDAO();
    }
    
    /**
     * mehtod return MySQLStoryDAO instance 
     * under IStoryDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IStoryDAO getStoryDAO() throws SQLException{
        return new MySQLStoryDAO();
    }
    
    /**
     * mehtod return MySQLStoryFlagDAO instance 
     * under IStoryFlagDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IStoryFlagDAO getStoryFlagDAO() throws SQLException{
        return new MySQLStoryFlagDAO();
    }
    
    /**
     * mehtod return MySQLUserTypeDAO instance 
     * under IUserTypeDAO interface
     * @return
     * @throws SQLException 
     */
    @Override
    public IUserTypeDAO getUserTypeDAO() throws SQLException{
        return new MySQLUserTypeDAO();
    }    
}
