/*
* @(#)IStoryDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Story;
import java.sql.SQLException;
import java.util.List;

/**
 * The IStoryDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */

public interface IStoryDAO {
   
    /**
     * abstract method for creating Story
     * @param story Story
     * @return boolean
     * @throws SQLException 
     */
     public boolean create(Story story) throws SQLException;

    /**
      * abstract method for reading Story by story ID
      * @param storyID Long
      * @return Story
      * @throws SQLException 
      */
    public Story read(Long storyID) throws SQLException;

    /**
     * abstract method for updating Story
     * @param story Story
     * @return boolean
     * @throws SQLException 
     */
    public boolean update(Story story) throws SQLException;

    /**
     * abstract method for updating total amount in Story
     * @param story Story
     * @return boolean
     * @throws SQLException 
     */
    public boolean updateTotal(Story story) throws SQLException;
           
    /**
     * abstract method for getting all Story (except DELETED)
     * @return List
     * @throws SQLException 
     */
    public List<Story> getAll() throws SQLException;
   
    /**
     * abstract method for getting all Story (for Admin)
     * @return List
     * @throws SQLException 
     */
    public List<Story> getAllForAdmin() throws SQLException;
    
    /**
     * abstract method for getting top Story
     * @param top int
     * @return List
     * @throws SQLException 
     */
    public List<Story> geTop(int top) throws SQLException;
}
