/*
* @(#)ICommunityTypeDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.CommunityType;
import java.sql.SQLException;
import java.util.List;

/**
 * The ICommunityTypeDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface ICommunityTypeDAO {
   
    /**
     * abstract method for creating CommunityType
     * @param communityType CommunityType
     * @return boolean
     * @throws SQLException 
     */
    public boolean create(CommunityType communityType) throws SQLException;

    /**
     * abstract method for reading CommunityType by key
     * @param key int
     * @return CommunityType
     * @throws SQLException 
     */
    public CommunityType read(int key) throws SQLException;

    /**
     * abstract method for reading CommunityType by type
     * @param type String
     * @return CommunityType
     * @throws SQLException 
     */
    public CommunityType readByType(String type) throws SQLException;
    
    /**
     * abstract method for filling CommunityType
     * @param communityType CommunityType
     * @return CommunityType
     * @throws SQLException 
     */
    public CommunityType fill(CommunityType communityType) throws SQLException;
   
    /**
     * abstract method for updating CommunityType
     * @param type CommunityType
     * @return boolean
     * @throws SQLException 
     */
    public boolean update(CommunityType type) throws SQLException;

    /**
     * abstract method for getting all CommunityType
     * @return List
     * @throws SQLException 
     */
    public List<CommunityType> getAll() throws SQLException;
}
