/*
* @(#)MySQLCommunityTypeDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "community type" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.ICommunityTypeDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.CommunityType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLCommunityTypeDAO class provides MySQL DB DAO layer 
 * for table prouders.community_type and contains several methods:
 * create(), read(), fill(), update(),  getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLCommunityTypeDAO implements ICommunityTypeDAO{
    /* final fields for columns in community table */
    protected static final int TYPE_ID = 1;         // type_id
    protected static final int COMMUNITY_TYPE = 2;  // community_type
    
    /*logger for MySQLCountryDAO class*/
    private static final Logger log 
            = Logger.getLogger(MySQLCommunityTypeDAO.class);
    
    /**
     * Empty constructor for MySQLCommunityTypeDAO
     * @throws SQLException 
     */
    public MySQLCommunityTypeDAO() throws SQLException {}
    
    /**
     * create() method insert new data to community_types table 
     * @param communityType CommunityType
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(CommunityType communityType) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to community_types table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO community_types "
                        + "(community_type) VALUES (?);");
        
        boolean result;         // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        create.setString(1,communityType.getCommunityType());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE COMMUNITY query is executed whith result: " + result);
        
        closeAll(create,connection);     // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from community_types table 
     * @param key int
     * @return communityType CommunityType
     * @throws SQLException 
     */
    @Override
    public CommunityType read(int key) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from community_types table by key */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM community_types "
                        + "WHERE type_id=?;");
        
        ResultSet resultSet;                // result for execute query
        CommunityType communityType = null; // new CommunityType object link        
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,key);                // add key
        resultSet = read.executeQuery();    // execute query
        
        /*set all data to new CommunityType object*/
        while (resultSet.next()) {
            communityType = new CommunityType();
            communityType.setTypeID(resultSet.getLong(TYPE_ID));
            communityType.setCommunityType(resultSet.getString(COMMUNITY_TYPE));
        }
        log.info("CommunityType is readed.");
         
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return communityType;
    }
    
    /**
     * read() method read data from community_types table 
     * @param type String
     * @return communityType CommunityType
     * @throws SQLException 
     */
    @Override
    public CommunityType readByType(String type) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from community_types table by key */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM community_types "
                        + "WHERE community_type=?;");
        
        ResultSet resultSet;                // result for execute query
        CommunityType communityType = null; // new CommunityType object link        
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setString(1,type);                // add key
        resultSet = read.executeQuery();    // execute query
        
        /*set all data to new CommunityType object*/
        while (resultSet.next()) {
            communityType = new CommunityType();
            communityType.setTypeID(resultSet.getLong(TYPE_ID));
            communityType.setCommunityType(resultSet.getString(COMMUNITY_TYPE));
        }
        log.info("CommunityType is readed.");
         
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return communityType;
    }
    
    
    /**
     * fill() method fill CommunityType object data by communityType
     * @param communityType CommunityType
     * @return communityType CommunityType
     * @throws SQLException 
     */
    @Override
    public CommunityType fill(CommunityType communityType) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared statement for select communityTypes data by type_id */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM community_types "
                        + "WHERE type_id=?;");
        
        ResultSet resultSet;            // result for execute query   
        log.info("Connection is open: " + connection);
        
        /*add getTypeID to select prapared statement*/
        read.setLong(1,communityType.getTypeID());
        resultSet = read.executeQuery();            // execute query
        
        /*set community type amount to communityType object*/
        while (resultSet.next()) {
            communityType.setCommunityType(resultSet.getString(COMMUNITY_TYPE));
        }
        log.info("CommunityType is filled.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return communityType;
    }
    
    /**
     * update() method updates data fro given CommunityType object
     * @param type CommunityType
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(CommunityType type) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update community_types table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE community_types "
                        + "SET community_type='?' WHERE type_id=?;");
        
        boolean result;         // result of update
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, type.getCommunityType());
        update.setLong(2,type.getTypeID());
        
        /* if update executes result = true */
        result = ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE COMMUNITY query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * getAll() method return all rows from community_types table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<CommunityType> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from community_types table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM community_types;");
        
        ResultSet resultSet;            // result set of execute query
        List<CommunityType> list 
                = new ArrayList<>();    // list of all CommunityType object        
        log.info("Connection is open: " + connection);
        resultSet = selectAll.executeQuery();           // execute query
        
        /* set data to all new CommunityType */
        while (resultSet.next()) {
            CommunityType type = new CommunityType();
            type.setTypeID(resultSet.getLong(TYPE_ID));
            type.setCommunityType(resultSet.getString(COMMUNITY_TYPE));
            list.add(type);
        }
        log.info("All community types are readed.");
        
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
                statement.close();      // close statement
                log.info("Prepared statement is closed.");
                connection.close();     // close connection
                log.info("Connection is closed.");
            } catch (SQLException ex) {
                log.error("Error while closing connection: " + ex);
            }
    }
}