/*
* @(#)ICommunityDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Community;
import java.sql.SQLException;
import java.util.List;

/**
 * The ICommunityDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface ICommunityDAO {
   
    /**
     * abstract method for creating Community
     * @param community Community
     * @return boolean
     * @throws SQLException 
     */
   public boolean create(Community community) throws SQLException;

   /**
    * abstract method for reading Community by community ID
    * @param communityID Long
    * @return Community
    * @throws SQLException 
    */
   public Community read(Long communityID) throws SQLException;

   /**
    * abstract method for reading Community by community name
    * @param name
    * @return
    * @throws SQLException 
    */
   public Community readByName(String name) throws SQLException;
   
   /**
    * abstract method for updating Community
    * @param community Community
    * @return boolean
    * @throws SQLException 
    */
   public boolean update(Community community) throws SQLException;

   /**
    * abstract method for updating total amount in Community
    * @param community Community
    * @return boolean
    * @throws SQLException 
    */
   public boolean updateTotal(Community community) throws SQLException;
           
   /**
    * abstract method for getting all Community
    * @return List
    * @throws SQLException 
    */
   public List<Community> getAll() throws SQLException;
   
   /**
    * abstract method for getting top Community
    * @param top int
    * @return List
    * @throws SQLException 
    */
   public List<Community> geTop(int top) throws SQLException;
}
