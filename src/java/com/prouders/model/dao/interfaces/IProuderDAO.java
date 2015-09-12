/*
* @(#)IProuderDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Prouder;
import java.sql.SQLException;
import java.util.List;

/**
 * The IProuderDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface IProuderDAO {
    
    /**
     * abstract method for creating Prouder
     * @param prouder Prouder
     * @return boolean
     * @throws SQLException 
     */
    public boolean create(Prouder prouder) throws SQLException;

    /**
     * abstract method for reading Prouder by prouder ID
     * @param prouderID Long
     * @return Prouder
     * @throws SQLException 
     */
    public Prouder read(Long prouderID) throws SQLException;

    /**
    * abstract method for reading Prouder by prouder email
    * @param email String
    * @return Prouder
    * @throws SQLException 
    */
    public Prouder readByEmail(String email) throws SQLException;
   
    /**
    * abstract method for checking Prouder by prouder name
    * @param name String
    * @return boolean
    * @throws SQLException 
    */
    public boolean checkName(String name) throws SQLException;
   
    /**
    * abstract method for checking Prouder by prouder password and email
    * @param email String
    * @param password String
    * @return boolean
    * @throws SQLException 
    */
    public boolean checkLogin(String email, String password) 
            throws SQLException;
   
   /**
    * abstract method for checking Prouder by prouder email
    * @param email String
    * @return boolean
    * @throws SQLException 
    */
    public boolean checkEmail(String email) throws SQLException;
   
   /**
    * abstract method for updating Prouder
    * @param prouder Prouder
    * @return boolean
    * @throws SQLException 
    */
    public boolean update(Prouder prouder) throws SQLException;
           
    /**
    * abstract method for getting all Prouder
    * @return List
    * @throws SQLException 
    */
    public List<Prouder> getAll() throws SQLException;
}
