/*
* @(#)IProudsCounterDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProudsCounter;
import com.prouders.model.entities.Story;
import java.sql.SQLException;
import java.util.List;

/**
 * The IProudsCounterDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface IProudsCounterDAO {
    
    /**
     * abstract method for creating ProudsCounter
     * @param proudsCounter ProudsCounter
     * @return boolean
     * @throws SQLException 
     */
    public boolean create(ProudsCounter proudsCounter) throws SQLException;

    /**
     * abstract method for checking connaction between story and prouder
     * @param prouder
     * @param story
     * @return
     * @throws SQLException 
     */
    public boolean check(Prouder prouder, Story story) throws SQLException;
    
    /**
     * abstract method for getting all story ID
     * @param prouderID Long
     * @return List
     * @throws SQLException 
     */
    public List<Long> getAllStoryCount(Long prouderID) throws SQLException;
   
    /**
    * abstract method for getting all Communities ID
    * @param storyID Long
    * @return List
    * @throws SQLException 
    */
    public List<Long> getAllProudsCount(Long storyID) throws SQLException;
}
