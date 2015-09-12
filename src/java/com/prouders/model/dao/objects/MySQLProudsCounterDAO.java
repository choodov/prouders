/*
* @(#)MySQLProudsCounterDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "ProudsCounter" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IProudsCounterDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.ProudsCounter;
import com.prouders.model.entities.Story;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLProudsCounterDAO class provides MySQL DB DAO layer 
 * for table prouders.prouder _communities and contains several methods:
 * create(), getAllStoryCount(), getAllProudsCount(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLProudsCounterDAO implements IProudsCounterDAO{
    /* final fields for columns in prouders_communities table */    
    protected static final int PROUDER_ID = 1;      // prouder_id
    protected static final int STORY_ID= 2;         // story_id
    
    /*logger for MySQLProudsCounterDAO class*/
    private static final Logger log 
            = Logger.getLogger(MySQLProudsCounterDAO.class);
    
    /**
     * Empty constructor for MySQLProudsCounterDAO
     * @throws SQLException 
     */
    public MySQLProudsCounterDAO() throws SQLException {}
    
    /**
     * create() method insert new data to prouds_counter table 
     * @param proudsCounter ProudsCounter
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(ProudsCounter proudsCounter) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for inserting to prouds_counter table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO prouds_counter "
                        + "(prouder_id, story_id) VALUES (?,?);");
       
        boolean result;     // method return result
        log.trace("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        create.setLong(1,proudsCounter.getProuder().getID());
        create.setLong(2,proudsCounter.getStory().getID());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE PROUDS_COUNTER query "
                + "is executed whith result: " + result);
        
        closeAll(create,connection);     // close connection and statement
        return result;
    }
    
    /**
     * check() method check connaction between story and prouder 
     * @param prouder Prouder
     * @param story Story
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean check(Prouder prouder, Story story) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for inserting to prouds_counter table*/
        PreparedStatement check 
                = connection.prepareStatement("SELECT * FROM prouds_counter "
                        + "WHERE prouder_id=? AND story_id=?;");
        
        ResultSet resultSet;                 // result set of execute query 

        boolean result;     // method return result
        log.trace("Connection is open: " + connection);
           
        /*add parameters to prepared statement:*/
        check.setLong(1,prouder.getID());
        check.setLong(2,story.getID());
        
        /*if statement execute success result = true*/
        resultSet = check.executeQuery();  // execute query
        
        /* set all story ID */
        if(resultSet.next()) {
            result = true;
        } else {
            result = false;
        }

        log.info("CHECK PROUDS_COUNTER query "
                + "is executed whith result: " + result);
        
        closeAll(check,connection);     // close connection and statement
        return result;
    }
    
    /**
     * getAllStoryCount() method return all story ID 
     * from prouds_counter table
     * @param prouderID Long
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Long> getAllStoryCount(Long prouderID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement to select all rows from prouds_counter table */
        PreparedStatement selectAllStory 
            = connection.prepareStatement("SELECT * FROM prouds_counter "
                    + "WHERE prouder_id=?;");
        
        ResultSet resultSet;                // result set of execute query    
        List<Long> list = new ArrayList<>();// list of all storyID
        log.trace("Connection is open: " + connection);
        
        /* add parameters to statement */
        selectAllStory.setLong(1, prouderID);
        resultSet = selectAllStory.executeQuery();  // execute query
        
        /* set all story ID */
        while (resultSet.next()) {
            list.add(resultSet.getLong(STORY_ID));
        }
        log.info("All story ID are readed");
        
        resultSet.close();                     // close result set
        closeAll(selectAllStory,connection);   // close connection and statement
        return list;
    }
    
    /**
     * getAllProudsCount() method return all prouderID 
     * from prouds_counter table
     * @param storyID Long
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Long> getAllProudsCount(Long storyID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from prouds_counter table */
        PreparedStatement selectAllProuder 
            = connection.prepareStatement("SELECT * FROM prouds_counter" 
                    + "WHERE story_id=?;");
        
        ResultSet resultSet;                 // result set of execute query    
        List<Long> list = new ArrayList<>(); // list of all proudersID
        log.trace("Connection is open: " + connection);
        
        /* add parameters to statement */
        selectAllProuder.setLong(1, storyID);
        resultSet = selectAllProuder.executeQuery();    // execute query
        
        /* set all prouder ID */
        while (resultSet.next()) {
            list.add(resultSet.getLong(PROUDER_ID));
        }
        log.info("All prouder ID are readed");
        
        resultSet.close();                     // close result set
        closeAll(selectAllProuder,connection); // close connection and statement
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
