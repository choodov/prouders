/*
* @(#)MySQLStoryFlagDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "StoryFlag" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.StoryFlag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLStoryFlagDAO class provides MySQL DB DAO layer 
 * for table prouders.story_flag and contains several methods:
 * create(), read(), fill(), update(),  getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLStoryFlagDAO implements IStoryFlagDAO{
    /* final fields for columns in story_flag table */
    protected static final int FLAG_ID = 1;     // flag_id
    protected static final int STORY_FLAG = 2;  // story_flag
    
    /*logger for MySQLStoryFlagDAO class*/
    private static final Logger log 
            = Logger.getLogger(MySQLStoryFlagDAO.class);
    
    /**
     * Empty constructor for MySQLStoryFlagDAO
     * @throws SQLException 
     */
    public MySQLStoryFlagDAO() throws SQLException {}
    
    /**
     * create() method insert new data to story_flags table 
     * @param storyFlag StoryFlag
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(StoryFlag storyFlag) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to story_flags table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO story_flags "
                        + "(story_flag) VALUES (?);");
        
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        create.setString(1,storyFlag.getStoryFlag());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE STORY_FLAG query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from story_flags table 
     * @param key int
     * @return storyFlag StoryFlag
     * @throws SQLException 
     */
    @Override
    public StoryFlag read(int key) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from story_flags table by key */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM story_flags "
                        + "WHERE flag_id=?;");
        
        ResultSet resultSet;        // result for execute query
        StoryFlag storyFlag = null; // new CommunityType object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,key);                // add key
        resultSet = read.executeQuery();    // execute query
        
        /*set all data to new CommunityType object*/
        while (resultSet.next()) {
            storyFlag = new StoryFlag();
            storyFlag.setFlagID(resultSet.getLong(FLAG_ID));
            storyFlag.setStoryFlag(resultSet.getString(STORY_FLAG));
        }
        log.info("StoryFlag is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return storyFlag;
    }
    
    /**
     * readByName() method read data from story_flags table by name of flag
     * @param name String
     * @return storyFlag StoryFlag
     * @throws SQLException 
     */
    @Override
    public StoryFlag readByName(String name) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from story_flags table by key */
        PreparedStatement readByName 
                = connection.prepareStatement("SELECT * FROM story_flags "
                        + "WHERE story_flag=?;");
        
        ResultSet resultSet;        // result for execute query
        StoryFlag storyFlag = null; // new CommunityType object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        readByName.setString(1,name);                // add key
        resultSet = readByName.executeQuery();    // execute query
        
        /*set all data to new CommunityType object*/
        while (resultSet.next()) {
            storyFlag = new StoryFlag();
            storyFlag.setFlagID(resultSet.getLong(FLAG_ID));
            storyFlag.setStoryFlag(resultSet.getString(STORY_FLAG));
        }
        log.info("StoryFlag is readed.");
        
        resultSet.close();          // close result set
        closeAll(readByName,connection);  // close connection and statement
        return storyFlag;
    }
    
    /**
     * fill() method fill StoryFlag object data by storyFlag
     * @param storyFlag StoryFlag
     * @return storyFlag StoryFlag
     * @throws SQLException 
     */
    @Override
    public StoryFlag fill(StoryFlag storyFlag) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared statement for select storyFlags data by flag_id */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM story_flags "
                        + "WHERE flag_id=?;");
        
        ResultSet resultSet;    // result for execute query
        log.info("Connection is open: " + connection);
        
        /*add getFlagID to select prapared statement*/
        read.setLong(1,storyFlag.getFlagID());
        resultSet = read.executeQuery();        // execute query
        
        /*set story flag type amount to storyFlag object*/
        while (resultSet.next()) {
            storyFlag.setStoryFlag(resultSet.getString(STORY_FLAG));
        }
        log.info("StoryFlag is filled.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return storyFlag;
    }
    
    /**
     * update() method updates data fro given StoryFlag object
     * @param flag StoryFlag
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(StoryFlag flag) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update story_flags table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE story_flags "
                        + "SET story_flag='?' WHERE flag_id=?;");
        
        boolean result;     // result of update
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, flag.getStoryFlag());
        update.setLong(2,flag.getFlagID());
        
        /* if update executes result = true */
        result = ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE STORY_FLAG query is executed whith result: " + result);
        
        closeAll(update,connection);        // close connection and statement
        return result;
    }
    
    /**
     * getAll() method return all rows from story_flags table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<StoryFlag> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from story_flags table */
        PreparedStatement selectAll = connection.prepareStatement("SELECT "
             + "* FROM story_flags;");
        
        ResultSet resultSet;                     // result set of execute query
        List<StoryFlag> list = new ArrayList<>();// list of all StoryFlag object
        log.info("Connection is open: " + connection);
        resultSet = selectAll.executeQuery();   // execute query
        
        /* set data to all new StoryFlag */
        while (resultSet.next()) {
            StoryFlag flag = new StoryFlag();
            flag.setFlagID(resultSet.getLong(FLAG_ID));
            flag.setStoryFlag(resultSet.getString(STORY_FLAG));
            list.add(flag);
        }
        log.info("All story flags are readed.");
        
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