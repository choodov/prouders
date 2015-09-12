/*
* @(#)IUserTypeDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.UserType;
import java.sql.SQLException;
import java.util.List;

/**
 * The IUserTypeDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface IUserTypeDAO {
   
    /**
     * abstract method for creating UserType
     * @param userType UserType
     * @return boolean
     * @throws SQLException 
     */
     public boolean create(UserType userType) throws SQLException;

    /**
     * abstract method for reading UserType by key
     * @param key int
     * @return UserType
     * @throws SQLException 
     */
    public UserType read(int key) throws SQLException;
    
    /**
     * abstract method for reading UserType by userType
     * @param userTypeName
     * @return
     * @throws SQLException 
     */
    public UserType readByName(String userTypeName) throws SQLException;

    /**
     * abstract method for filling UserType
     * @param userType UserType
     * @return UserType
     * @throws SQLException 
     */
    public UserType fill(UserType userType) throws SQLException;

    /**
     * abstract method for updating UserType
     * @param flag UserType
     * @return boolean
     * @throws SQLException 
     */
    public boolean update(UserType flag) throws SQLException;

    /**
     * abstract method for getting all UserType
     * @return List
     * @throws SQLException 
     */
    public List<UserType> getAll() throws SQLException;
}
