/*
* @(#)IProuderCommunitiesDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProuderCommunities;
import java.sql.SQLException;
import java.util.List;

/**
 * The IProuderCommunitiesDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface IProuderCommunitiesDAO {
   
    /**
     * abstract method for creating ProuderCommunities
     * @param prouderCommunity ProuderCommunities
     * @return boolean
     * @throws SQLException 
     */
    public boolean create(ProuderCommunities prouderCommunity) 
           throws SQLException;

    /**
     * abstract method for checking ProuderCommunities
     * @param prouder
     * @param community
     * @return boolean
     * @throws SQLException 
     */
    public boolean check(Prouder prouder, Community community) 
                throws SQLException;
    
    /**
    * abstract method for getting all Communities ID
    * @param prouderID Long
    * @return List
    * @throws SQLException 
    */
    public List<Long> getAllCommunitiesID(Long prouderID) throws SQLException;
   
    /**
    * abstract method for getting all Prouders ID
    * @param communityID Long
    * @return List
    * @throws SQLException 
    */
    public List<Long> getAllProudersID(Long communityID) throws SQLException;
}
