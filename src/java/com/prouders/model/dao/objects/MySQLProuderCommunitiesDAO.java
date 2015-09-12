/*
* @(#)MySQLProuderCommunitiesDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods 
* for "ProuderCommunities" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IProuderCommunitiesDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProuderCommunities;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLProuderCommunitiesDAO class provides MySQL DB DAO layer 
 * for table prouders.prouder _communities and contains several methods:
 * create(), getAllCommunitiesID(), getAllProudersID(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLProuderCommunitiesDAO implements IProuderCommunitiesDAO{
    /* final fields for columns in prouders_communities table */
    protected static final int PROUDER_ID = 1;      // prouder_id
    protected static final int COMMUNITY_ID= 2;     // community_id
     
    /*logger for MySQLProuderCommunitiesDAO class*/
    private static final Logger log 
            = Logger.getLogger(MySQLProuderCommunitiesDAO.class);
    
    /**
     * Empty constructor for MySQLProuderCommunitiesDAO
     * @throws SQLException 
     */
    public MySQLProuderCommunitiesDAO() throws SQLException {}
    
    /**
     * create() method insert new data to prouders_communities table 
     * @param prouderCommunities ProuderCommunities
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(ProuderCommunities prouderCommunities) 
            throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to prouders_communities table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO prouders_communities "
                        + "(prouder_id, community_id) VALUES (?,?);");
        
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        create.setLong(1,prouderCommunities.getProuder().getID());
        create.setLong(2,prouderCommunities.getCommunity().getID());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE PROUDER_COMMUNITY "
                + "query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * check() method check data in prouders_communities table 
     * @param prouder Prouder
     * @param community Community
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean check(Prouder prouder, Community community) 
            throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to prouders_communities table*/
        PreparedStatement check 
                = connection.prepareStatement("SELECT * FROM prouders_communities "
                        + "WHERE prouder_id=? AND community_id=?;");
        
        ResultSet resultSet;                 // result set of execute query 
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        check.setLong(1,prouder.getID());
        check.setLong(2,community.getID());
        
        /*if statement execute success result = true*/
        resultSet = check.executeQuery();  // execute query
        
        /* set all story ID */
        if(resultSet.next()) {
            result = true;
        } else {
            result = false;
        }
        
        log.info("CHECK PROUDER_COMMUNITY "
                + "query is executed whith result: " + result);
        
        closeAll(check,connection);    // close connection and statement
        return result;
    }
    
    /**
     * getAllCommunitiesID() method return all communityID 
     * from prouders_communities table
     * @param prouderID Long
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Long> getAllCommunitiesID(Long prouderID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* 
        * prepared statement to select all 
        * rows from prouders_communities table 
        */
        PreparedStatement selectAllCommunity 
                = connection.prepareStatement("SELECT * "
                        + "FROM prouders_communities WHERE prouder_id=?;");
        
        ResultSet resultSet;                // result set of execute query
        List<Long> list = new ArrayList<>();// list of all communityID
        log.info("Connection is open: " + connection);
        
        /* add parameters to statement */
        selectAllCommunity.setLong(1, prouderID);
        resultSet = selectAllCommunity.executeQuery(); // execute query
        
        /* set all community ID */
        while (resultSet.next()) {
            list.add(resultSet.getLong(COMMUNITY_ID));
        }
        log.info("All community ID are readed");
        
        resultSet.close();                      // close result set
        closeAll(selectAllCommunity,connection);// close connection and statement
        return list;
    }
    
    /**
     * getAllProudersID() method return all prouderID 
     * from prouders_communities table
     * @param communityID Long
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Long> getAllProudersID(Long communityID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* 
        * prepared statement to select all 
        * rows from prouders_communities table 
        */
        PreparedStatement selectAllProuder 
                = connection.prepareStatement("SELECT * "
                        + "FROM prouders_communities WHERE community_id=?;");
        
        ResultSet resultSet;                // result set of execute query
        List<Long> list = new ArrayList<>();// list of all communityID
        log.info("Connection is open: " + connection);
        
        /* add parameters to statement */
        selectAllProuder.setLong(1, communityID);
        resultSet = selectAllProuder.executeQuery();    // execute query
        
        /* set all prouder ID */
        while (resultSet.next()) {
            list.add(resultSet.getLong(PROUDER_ID));
        }
        log.info("All prouder ID are readed");
        
        resultSet.close();                    // close result set
        closeAll(selectAllProuder,connection);// close connection and statement  
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
