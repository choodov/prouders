/*
* @(#)MySQLUserTypeDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "UserType" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLUserTypeDAO class provides MySQL DB DAO layer 
 * for table prouders.user_types and contains several methods:
 * create(), read(), fill(), update(),  getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLUserTypeDAO implements IUserTypeDAO{
    /* final fields for columns in user_types table */
    protected static final int TYPE_ID = 1;     // type_id
    protected static final int USER_TYPE = 2;   // user_type
    
    /*logger for MySQLUserTypeDAO class*/
    private static final Logger log = Logger.getLogger(MySQLUserTypeDAO.class);

    /**
     * Empty constructor for MySQLUserTypeDAO
     * @throws SQLException 
     */
    public MySQLUserTypeDAO() throws SQLException {}
    
    /**
     * create() method insert new data to user_types table 
     * @param userType UserType
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(UserType userType) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to user_types table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO user_types "
                        + "(user_type) VALUES (?);");
        
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        create.setString(1,userType.getUserType());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE USER_TYPE query is executed whith result: " + result);
        
        closeAll(create,connection);        // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from user_types table 
     * @param key int
     * @return userType UserType
     * @throws SQLException 
     */
    @Override
    public UserType read(int key) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for select from user_types table by key */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM user_types "
                        + "WHERE type_id=?;");
        
        ResultSet resultSet;        // result for execute query
        UserType userType = null;   // new CommunityType object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,key);                // add key
        resultSet = read.executeQuery();    // execute query
        
        /*set all data to new UserType object*/
        while (resultSet.next()) {
            userType = new UserType();
            userType.setTypeID(resultSet.getLong(TYPE_ID));
            userType.setUserType(resultSet.getString(USER_TYPE));
        }
        log.info("UserType is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return userType;
    }
    
    /**
     * readByName() method read data from user_types table
     * @param userTypeName
     * @return
     * @throws SQLException 
     */
    @Override
    public UserType readByName(String userTypeName) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for select from user_types table by key */
        PreparedStatement readByName 
                = connection.prepareStatement("SELECT * FROM user_types "
                        + "WHERE user_type=?;");
        
        ResultSet resultSet;        // result for execute query
        UserType userType = null;   // new CommunityType object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        readByName.setString(1,userTypeName);                // add key
        resultSet = readByName.executeQuery();    // execute query
        
        /*set all data to new UserType object*/
        while (resultSet.next()) {
            userType = new UserType();
            userType.setTypeID(resultSet.getLong(TYPE_ID));
            userType.setUserType(resultSet.getString(USER_TYPE));
        }
        log.info("UserType is readed.");
        
        resultSet.close();          // close result set
        closeAll(readByName,connection);  // close connection and statement
        return userType;
    }
    
    /**
     * fill() method fill UserType object data by userType
     * @param userType UserType
     * @return userType UserType
     * @throws SQLException 
     */
    @Override
    public UserType fill(UserType userType) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /*prepared statement for select user_types data by type_id */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM user_types "
                        + "WHERE type_id=?;");
        
        ResultSet resultSet;    // result for execute query
        log.info("Connection is open: " + connection);
        
        /*add getTypeID to select prapared statement*/
        read.setLong(1,userType.getTypeID());
        resultSet = read.executeQuery();     // execute query
        
        /*set user type amount to userType object*/
        while (resultSet.next()) {
            userType.setUserType(resultSet.getString(USER_TYPE));
        }
        log.info("UserType is filled.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return userType;
    }
    
    /**
     * update() method updates data fro given UserType object
     * @param type UserType
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(UserType type) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update user_types table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE user_types "
                        + "SET user_type='?' WHERE type_id=?;");
        
        boolean result;     // result of update
        log.info("Connection is open: " + connection);
        
         /* add parameters to prapared statement*/
        update.setString(1, type.getUserType());
        update.setLong(2,type.getTypeID());
        
        /* if update executes result = true */
        result = ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE USER_TYPE query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * getAll() method return all rows from user_types table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<UserType> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from user_types table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM user_types;");
        
        ResultSet resultSet;                    // result set of execute query
        List<UserType> list = new ArrayList<>();// list of all UserType object
        log.info("Connection is open: " + connection); 
        
        resultSet = selectAll.executeQuery();   // execute query
        
        /* set data to all new UserType */
        while (resultSet.next()) {
            UserType type = new UserType();
            type.setTypeID(resultSet.getLong(TYPE_ID));
            type.setUserType(resultSet.getString(USER_TYPE));
            list.add(type);
        }
        log.info("All user types are readed.");
        
        resultSet.close();              // close result set
        closeAll(selectAll,connection); // close connection and statement
        return list;
    } 
    
    /**
     * closeAll() methos close prepared statement and connection
     * @param statement PreparedStatement
     * @param connection  Connaction
     */
    private void closeAll(PreparedStatement statement, Connection connection) {
        try {
                statement.close();   // close statement
                log.info("Prepared statement is closed.");
                connection.close();  // close connection
                log.info("Connection is closed.");
            } catch (SQLException ex) {
                log.error("Error while closing connection: " + ex);
            }
    }
}
