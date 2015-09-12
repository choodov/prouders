/*
* @(#)MySQLStoryDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "story" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IStoryDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Story;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.StoryFlag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLStoryDAO class provides MySQL DB DAO layer 
 * for table prouders.stories and contains several methods:
 * create(), read(), update(), updateTotal(), getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLStoryDAO implements IStoryDAO{
    /* final fields for columns in stories table */    
    protected static final int STORY_ID = 1;        // story_id
    protected static final int STORY_HEADER = 2;    // story_header
    protected static final int STORY_BODY = 3;      // story_body
    protected static final int STORY_TIME = 4;      // story_time
    protected static final int PROUDER_ID = 5;      // prouder_id
    protected static final int COMMUNITY_ID = 6;    // community_id
    protected static final int TOTAL_AMOUNT = 7;    // total_amount
    protected static final int FLAG_ID = 8;         // flag_id
    protected static final int STORY_STATUS = 9;    // story_status
    
    /*logger for MySQLStoryDAO class*/
    private static final Logger log = Logger.getLogger(MySQLStoryDAO.class);
       
    /**
     * Empty constructor for MySQLStoryDAO
     * @throws SQLException 
     */
    public MySQLStoryDAO() throws SQLException {}
    
    /**
     * create() method insert new data to stories table 
     * @param story Story
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(Story story) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to stories table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO stories "
                        + "(story_header, story_body, "
                        + "prouder_id, community_id, total_amount, "
                        + "flag_id, story_status) "
                        + "VALUES (?,?,?,?,?,?,?);");
        
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        create.setString(1, story.getHeader());
        create.setString(2, story.getBody());
        create.setLong(3, story.getProuder().getID());
        create.setLong(4, story.getCommunity().getID());
        create.setLong(5, story.getTotalAmount());
        create.setLong(6, story.getFlag().getFlagID());
        create.setLong(7, story.getStatus().getValue());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE STORY query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from stories table 
     * @param storyID Long
     * @return country Country
     * @throws SQLException 
     */
    @Override
    public Story read(Long storyID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for select from country table by story ID*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM stories "
                        + "WHERE story_id=?;");
        
        ResultSet resultSet;    // result for execute query
        Story story = null;     // new Story object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,storyID);
        resultSet = read.executeQuery();    // execute query
        
        /*set all data to new Story object*/
        while (resultSet.next()) {
            story = new Story();
            story.setID(resultSet.getLong(STORY_ID));
            story.setHeader(resultSet.getString(STORY_HEADER));
            story.setBody(resultSet.getString(STORY_BODY));
            story.setTime(resultSet.getDate(STORY_TIME));
            Prouder prouder = new Prouder(resultSet.getLong(PROUDER_ID));
            story.setProuder(prouder);
            Community community 
                    = new Community(resultSet.getLong(COMMUNITY_ID));
            story.setCommunity(community);
            story.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            StoryFlag storyFlag 
                    = new StoryFlag(resultSet.getLong(FLAG_ID));
            story.setFlag(storyFlag);
            story.setStatus(Status.getStatus(resultSet.getInt(STORY_STATUS)));
        }
        log.info("Story is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return story;
    }
    
    /**
     * update() method updates data fro given Story object
     * @param story Story
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(Story story) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update stories table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE stories "
                        + "SET story_header=?, story_body=?,"
                        + " prouder_id=?, community_id=?, total_amount=?, "
                        + "flag_id=?, story_status=?"
                        + " WHERE story_id=?;");
        
        boolean result;     // result of update
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, story.getHeader());
        update.setString(2, story.getBody());
        update.setLong(3, story.getProuder().getID());
        update.setLong(4, story.getCommunity().getID());
        update.setLong(5, story.getTotalAmount());
        update.setLong(6, story.getFlag().getFlagID());
        update.setInt(7, story.getStatus().getValue());
        update.setLong(8, story.getID());
        
        /* if update executes result = true */
        result =  ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE STORY query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * updateTotal() method update total amount for given story
     * @param story Story
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean updateTotal(Story story) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for update total amount */
        PreparedStatement updateTotal 
                = connection.prepareStatement("UPDATE stories "
                        + "SET total_amount=? WHERE story_id=?;");
        
        boolean result;     // result of execute query
        log.info("Connection is open: " + connection);
        
        /* add parameters to statement */
        updateTotal.setLong(1, story.getTotalAmount());
        updateTotal.setLong(2, story.getID());
        
        /* if execute is success then result = true*/
        result = ((updateTotal.executeUpdate() > 0) ? true : false);
        log.info("UPDATE TOTAL in STORY query "
                + "is executed whith result: " + result);
        
        closeAll(updateTotal,connection);   // close connection andstatement
        return result;
    }
    
    /**
     * getAll() method return all rows from stories table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Story> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement to select all rows from stories table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM stories "
                        + "WHERE story_status <> 3;"); // not deleted
        
        ResultSet resultSet;                 // result set of execute query
        List<Story> list = new ArrayList<>();// list of all Country object
        log.info("Connection is open: " + connection);
        
        resultSet = selectAll.executeQuery();  // execute query
        
        /* set data to all new Stories */
        while (resultSet.next()) {
            Story story = new Story();
            story.setID(resultSet.getLong(STORY_ID));
            story.setHeader(resultSet.getString(STORY_HEADER));
            story.setBody(resultSet.getString(STORY_BODY));
            story.setTime(resultSet.getDate(STORY_TIME));
            Prouder prouder = new Prouder(resultSet.getLong(PROUDER_ID));
            story.setProuder(prouder);
            Community community 
                    = new Community(resultSet.getLong(COMMUNITY_ID));
            story.setCommunity(community);
            story.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            StoryFlag storyFlag 
                    = new StoryFlag(resultSet.getLong(FLAG_ID));
            story.setFlag(storyFlag);
            story.setStatus(Status.getStatus(resultSet.getInt(STORY_STATUS)));
            list.add(story);
        }
        log.info("All stories are readed.");
        
        resultSet.close();              // close result set
        closeAll(selectAll,connection); // close connection and statement
        return list;
    }
    
    /**
     * getAllForAdmin() method return all rows 
     * from stories table for Administrator
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Story> getAllForAdmin() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement to select all rows from stories table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM stories;");
        
        ResultSet resultSet;                 // result set of execute query
        List<Story> list = new ArrayList<>();// list of all Country object
        log.info("Connection is open: " + connection);
        
        resultSet = selectAll.executeQuery();  // execute query
        
        /* set data to all new Stories */
        while (resultSet.next()) {
            Story story = new Story();
            story.setID(resultSet.getLong(STORY_ID));
            story.setHeader(resultSet.getString(STORY_HEADER));
            story.setBody(resultSet.getString(STORY_BODY));
            story.setTime(resultSet.getDate(STORY_TIME));
            Prouder prouder = new Prouder(resultSet.getLong(PROUDER_ID));
            story.setProuder(prouder);
            Community community 
                    = new Community(resultSet.getLong(COMMUNITY_ID));
            story.setCommunity(community);
            story.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            StoryFlag storyFlag 
                    = new StoryFlag(resultSet.getLong(FLAG_ID));
            story.setFlag(storyFlag);
            story.setStatus(Status.getStatus(resultSet.getInt(STORY_STATUS)));
            list.add(story);
        }
        log.info("All stories are readed.");
        
        resultSet.close();              // close result set
        closeAll(selectAll,connection); // close connection and statement
        return list;
    }
    
    /**
     * geTop() method return top N Story by total amount column
     * @param top int
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Story> geTop(int top) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for select top Stories */
        PreparedStatement selectTop 
                = connection.prepareStatement("SELECT * FROM stories "
                        + "WHERE story_status <> 3 " // not deleted
                        + "ORDER BY total_amount DESC LIMIT ?;");
        
        ResultSet resultSet;                 // result set of execute query
        List<Story> list = new ArrayList<>();// result list of Countries
        log.info("Connection is open: " + connection);
        
        
        selectTop.setInt(1, top);            // set number of selected rows
        resultSet = selectTop.executeQuery();// execute query
        
        /* set data to top N Stories objects */
        while (resultSet.next()) {
            Story story = new Story();
            story.setID(resultSet.getLong(STORY_ID));
            story.setHeader(resultSet.getString(STORY_HEADER));
            story.setBody(resultSet.getString(STORY_BODY));
            story.setTime(resultSet.getDate(STORY_TIME));
            Prouder prouder = new Prouder(resultSet.getLong(PROUDER_ID));
            story.setProuder(prouder);
            Community community 
                    = new Community(resultSet.getLong(COMMUNITY_ID));
            story.setCommunity(community);
            story.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            StoryFlag storyFlag 
                    = new StoryFlag(resultSet.getLong(FLAG_ID));
            story.setFlag(storyFlag);
            story.setStatus(Status.getStatus(resultSet.getInt(STORY_STATUS)));
            list.add(story);
        }
        log.info("Top " + top + " stories is readed.");
        
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
