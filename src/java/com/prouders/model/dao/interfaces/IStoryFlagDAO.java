/*
* @(#)IStoryFlagDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.StoryFlag;
import java.sql.SQLException;
import java.util.List;

/**
 * The IStoryFlagDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface IStoryFlagDAO {
   
    /**
    * abstract method for creating StoryFlag
    * @param storyFlag StoryFlag
    * @return boolean
    * @throws SQLException 
    */
    public boolean create(StoryFlag storyFlag) throws SQLException;

    /**
     * abstract method for reading StoryFlag by key
     * @param key int
     * @return StoryFlag
     * @throws SQLException 
     */
    public StoryFlag read(int key) throws SQLException;

    /**
     * abstract method for reading StoryFlag by name
     * @param name
     * @return
     * @throws SQLException 
     */
    public StoryFlag readByName(String name) throws SQLException;
    
    /**
      * abstract method for filling StoryFlag
      * @param storyFlag StoryFlag
      * @return StoryFlag
      * @throws SQLException 
      */
    public StoryFlag fill(StoryFlag storyFlag) throws SQLException;
   
    /**
      * abstract method for updating StoryFlag
      * @param flag StoryFlag
      * @return boolean
      * @throws SQLException 
      */
    public boolean update(StoryFlag flag) throws SQLException;

    /**
      * abstract method for getting all StoryFlag
      * @return List
      * @throws SQLException 
      */
    public List<StoryFlag> getAll() throws SQLException;
}
