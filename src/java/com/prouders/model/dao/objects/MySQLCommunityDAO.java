/*
* @(#)MySQLCommunityDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "community" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.ICommunityDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.CommunityType;
import com.prouders.model.entities.Country;
import com.prouders.model.entities.Status;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLCommunityDAO class provides MySQL DB DAO layer 
 * for table prouders.community and contains several methods:
 * create(), read(), readByName(), 
 * update(), updateTotal(), getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLCommunityDAO implements ICommunityDAO{
    /* final fields for columns in community table */
    protected static final int COMMUNITY_ID = 1;         // community_id
    protected static final int COMMUNITY_NAME = 2;       // community_name
    protected static final int COMMUNITY_DESCRIPTION = 3;// community_description
    protected static final int COUNTRY_ID = 4;           // county_id
    protected static final int TOTAL_AMOUNT = 5;         // total_amount
    protected static final int TYPE_ID = 6;              // type_id
    protected static final int COMMUNITY_STATUS = 7;     // community_status
    
    /*logger for MySQLCommunityDAO class*/
    private static final Logger log = Logger.getLogger(MySQLCommunityDAO.class);
    
    /**
     * Empty constructor for MySQLCommunityDAO
     * @throws SQLException 
     */
    public MySQLCommunityDAO() throws SQLException {
    }
    
    /**
     * create() method insert new data to community table 
     * @param community Community
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(Community community) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to community table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO communities "
                        + "(community_name, community_description, "
                        + "country_id, total_amount, type_ID, community_status)"
                        + " VALUES (?,?,?,?,?,?);");
        boolean result;         // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        create.setString(1, community.getName());
        create.setString(2, community.getDescription());
        create.setLong(3, community.getCountry().getCountryID());
        create.setLong(4, community.getTotalAmount());
        create.setLong(5, community.getType().getTypeID());
        create.setInt(6, community.getStatus().getValue());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE COMMUNITY query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from communities table 
     * @param communityID Long
     * @return community Community
     * @throws SQLException 
     */
    @Override
     public Community read(Long communityID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from community table by community ID*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM communities "
                        + "WHERE community_id=?;");
        ResultSet resultSet;        // result for execute query
        Community community = null; // new Community object link
        
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,communityID);        // add community ID
        resultSet = read.executeQuery();    // execute query
        log.info("READ COMMUNITY query is executed");
        
        /*set all data to new Community object*/
        while (resultSet.next()) {
            community = new Community();
            community.setID(resultSet.getLong(COMMUNITY_ID));
            community.setName(resultSet.getString(COMMUNITY_NAME));
            community.setDescription(resultSet.getString(COMMUNITY_DESCRIPTION));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            community.setCountry(country);
            community.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            CommunityType communityType 
                    = new CommunityType(resultSet.getLong(TYPE_ID));
            community.setType(communityType);
            community.setStatus(Status.getStatus
                    (resultSet.getInt(COMMUNITY_STATUS)));
        }
        log.info("Community is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return community;
    }
     
     /**
     * read() method read data from communities table 
     * @param name String
     * @return community Community
     * @throws SQLException 
     */
    @Override
     public Community readByName(String name) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from community table by community ID*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM communities "
                        + "WHERE community_name=?;");
        ResultSet resultSet;        // result for execute query
        Community community = null; // new Community object link
        
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setString(1,name);        // add community ID
        resultSet = read.executeQuery();    // execute query
        log.info("READ COMMUNITY query is executed");
        
        /*set all data to new Community object*/
        while (resultSet.next()) {
            community = new Community();
            community.setID(resultSet.getLong(COMMUNITY_ID));
            community.setName(resultSet.getString(COMMUNITY_NAME));
            community.setDescription(resultSet.getString(COMMUNITY_DESCRIPTION));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            community.setCountry(country);
            community.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            CommunityType communityType 
                    = new CommunityType(resultSet.getLong(TYPE_ID));
            community.setType(communityType);
            community.setStatus(Status.getStatus
                    (resultSet.getInt(COMMUNITY_STATUS)));
        }
        log.info("Community is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return community;
    }
     
    /**
     * update() method updates data fro given Community object
     * @param community Community
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(Community community) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update communities table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE communities "
                        + "SET community_name=?, community_description=?,"
                        + " country_id=?, total_amount=?, type_ID=?, "
                        + "community_status=? WHERE community_id=?;");
        boolean result;     // result of update
         
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, community.getName());
        update.setString(2, community.getDescription());
        update.setLong(3, community.getCountry().getCountryID());
        update.setLong(4, community.getTotalAmount());
        update.setLong(5, community.getType().getTypeID());
        update.setInt(6, community.getStatus().getValue());
        update.setLong(7, community.getID());
        
        /* if update executes result = true */
        result = ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE COMMUNITY query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * updateTotal() method update total amount for given community
     * @param community Community
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean updateTotal(Community community) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for update total amount */
        PreparedStatement updateTotal 
                = connection.prepareStatement("UPDATE communities "
                        + "SET total_amount='?' WHERE community_id=?;");
        boolean result;     // result of execute query
        
        log.info("Connection is open: " + connection);
        
         /* add parameters to statement */
        updateTotal.setLong(1, community.getTotalAmount());
        updateTotal.setLong(2, community.getID());
        
        /* if execute is success then result = true*/
        result = ((updateTotal.executeUpdate() > 0) ? true : false);
        log.info("UPDATE TOTAL in COMMUNITY query "
                + "is executed whith result: " + result);
        
        closeAll(updateTotal,connection);   // close connection andstatement
        return result;
    }
    
    /**
     * getAll() method return all rows from communities table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Community> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from communities table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM communities;");
        
        ResultSet resultSet;                     // result set of execute query
        List<Community> list = new ArrayList<>();// list of all Community object
        
        log.info("Connection is open: " + connection);
        resultSet = selectAll.executeQuery();    // execute query
        
        /* set data to all new Communities */
        while (resultSet.next()) {
            Community community = new Community();
            community.setID(resultSet.getLong(COMMUNITY_ID));
            community.setName(resultSet.getString(COMMUNITY_NAME));
            community.setDescription(resultSet.getString(COMMUNITY_DESCRIPTION));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            community.setCountry(country);
            community.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            CommunityType communityType 
                    = new CommunityType(resultSet.getLong(TYPE_ID));
            community.setType(communityType);
            community.setStatus(Status.getStatus
                    (resultSet.getInt(COMMUNITY_STATUS)));
            list.add(community);
        }
        log.info("All community is readed.");
        
        resultSet.close();              // close result set
        closeAll(selectAll,connection); // close connection and statement
        return list;
    }
    
    /**
     * geTop() method return top N Communities by total amount column
     * @param top int
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Community> geTop(int top) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select top Communities */
        PreparedStatement selectTop 
                = connection.prepareStatement("SELECT * FROM communities "
                        + "ORDER BY total_amount DESC LIMIT ?;");
        
        ResultSet resultSet;                     // result set of execute query
        List<Community> list = new ArrayList<>();// result list of Countries
        log.info("Connection is open: " + connection);
        
        /* add parameters to statement */
        selectTop.setInt(1, top);
        resultSet = selectTop.executeQuery();    // execute query
        
        /* set data to top N Communities objects */
        while (resultSet.next()) {
            Community community = new Community();
            community.setID(resultSet.getLong(COMMUNITY_ID));
            community.setName(resultSet.getString(COMMUNITY_NAME));
            community.setDescription(resultSet.getString(COMMUNITY_DESCRIPTION));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            community.setCountry(country);
            community.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            CommunityType communityType 
                    = new CommunityType(resultSet.getLong(TYPE_ID));
            community.setType(communityType);
            community.setStatus(Status.getStatus
                    (resultSet.getInt(COMMUNITY_STATUS)));
            list.add(community);
        }
        log.info("Top " + top + " communities is readed.");
        resultSet.close();              // close result set
        closeAll(selectTop,connection); // close connection and statement
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