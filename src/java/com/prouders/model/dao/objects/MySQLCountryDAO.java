/*
* @(#)MySQLCountryDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "country" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Country;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLCountryDAO class provides MySQL DB DAO layer 
 * for table prouders.country and contains several methods:
 * create(), read(), fill(), readByName(), 
 * update(), updateTotal(), getAll(), geTop(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLCountryDAO implements ICountryDAO{
    /* final fields for columns in community table */
    protected static final int COUNTRY_ID = 1;      // country_id column
    protected static final int COUNTRY_NAME = 2;    // country_name column
    protected static final int TOTAL_AMOUNT = 3;    // total_amount column
    
    /*logger for MySQLCountryDAO class*/
    private static final Logger log = Logger.getLogger(MySQLCountryDAO.class);
    
    /**
     * Empty constructor for MySQLCountryDAO
     * @throws SQLException 
     */
    public MySQLCountryDAO() throws SQLException {
    }
    
    /**
     * create() method insert new data to countries table 
     * @param country Country
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(Country country) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to country table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO countries "
                        + "(country_name, total_amount) VALUES (?,?);");
        
        boolean result;         // method return result        
        log.info("Connection is open: " + connection);
        
        /*add parameters to prepared statement:*/
        create.setString(1,country.getCountryName());   // add country name
        create.setLong(2,country.getTotalAmount());     // add total amount
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);
        log.info("CREATE COUNTRY query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from countries table 
     * @param countryID Long
     * @return country Country
     * @throws SQLException 
     */
    @Override
    public Country read(Long countryID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
                
        /* prepared statement for select from country table by country ID*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM countries "
                        + "WHERE country_id=?;");
        
        ResultSet resultSet;        // result for execute query
        Country country = null;     // new Country object link        
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,countryID);          // add country ID
        resultSet = read.executeQuery();    // execute query
        log.info("SELECT COUNTRY query is executed");
        
        /*set all data to new Country object*/
        while (resultSet.next()) {
            country = new Country();
            country.setCountryID(resultSet.getLong(COUNTRY_ID));
            country.setCountryName(resultSet.getString(COUNTRY_NAME));
            country.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
        }
        log.info("Country is readed.");
        
        resultSet.close();              // close result set
        closeAll(read,connection);      // close connection and statement
        return country;
    }
    
    /**
     * fill() method fill Country object data by country ID
     * @param country Country
     * @return country Country
     * @throws SQLException 
     */
    @Override
    public Country fill(Country country) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared statement for select country data by country id */
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM countries "
                        + "WHERE country_id=?;");
        
        ResultSet resultSet;        // result for execute query
        log.info("Connection is open: " + connection);
        
        /*add country ID to select prapared statement*/
        read.setLong(1,country.getCountryID());
        resultSet = read.executeQuery();        // execute query
        
        /*set country name and total amount to country object*/
        while (resultSet.next()) {
            country.setCountryName(resultSet.getString(COUNTRY_NAME));
            country.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
        }
        log.info("Country is filled.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return country;
    }
    
    /**
     * readByName() method return Country object with given name
     * @param countryName String
     * @return country Country
     * @throws SQLException 
     */
    @Override
    public Country readByName(String countryName) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared stement for selecting Country data by name*/
        PreparedStatement readByName 
                = connection.prepareStatement("SELECT * FROM countries "
                        + "WHERE country_name=?;");
        
        ResultSet resultSet;        // result for execute query
        Country country = null;     // Country object link        
        log.info("Connection is open: " + connection);
        
        /* add country name to prepared statement*/
        readByName.setString(1,countryName);
        resultSet = readByName.executeQuery();  // execute query
        log.info("READ BY NAME COUNTRY query is executed");
        
        /* set all data to new Country object*/
        while (resultSet.next()) {
            country = new Country();
            country.setCountryID(resultSet.getLong(COUNTRY_ID));
            country.setCountryName(resultSet.getString(COUNTRY_NAME));
            country.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
        }
        log.info("Country is readed by name: " + countryName);
        
        resultSet.close();                  // close result set
        closeAll(readByName,connection);    // close connection and statement
        return country;
    }
    
    /**
     * update() method updates data fro given Country object
     * @param country Country
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(Country country) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to update country table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE countries "
                        + "SET country_name=?, total_amount=? "
                        + "WHERE country_id=?;");
        
        boolean result;     // result of update        
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, country.getCountryName());  // add country name
        update.setLong(2, country.getTotalAmount());    // add total amount
        update.setLong(3, country.getCountryID());      // add country ID
        
        /* if update executes result = true */
        result =  ((update.executeUpdate() > 0) ? true : false);
        log.info("UPDATE COUNTRY query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * updateTotal() method update total amount for given country
     * @param country Country
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean updateTotal(Country country) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /* prepared statement for update total amount */
        PreparedStatement updateTotal 
                = connection.prepareStatement("UPDATE countries "
                        + "SET total_amount=? WHERE country_id=?;");
        
        boolean result;         // result of execute query        
        log.info("Connection is open: " + connection);
        
        /* add parameters to statement */
        updateTotal.setLong(1, country.getTotalAmount());   // add total amount
        updateTotal.setLong(2, country.getCountryID());     // add country ID
        
        /* if execute is success then result = true*/
        result = ((updateTotal.executeUpdate() > 0) ? true : false);
        log.info("UPDATE TOTAL in COUNTRY query "
                + "is executed whith result: " + result);
        
        closeAll(updateTotal,connection);      // close connection andstatement
        return result;
    }
    
    /**
     * getAll() method return all rows from countries table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Country> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from country table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM countries;");
        
        ResultSet resultSet;                    // result set of execute query        
        List<Country> list = new ArrayList<>(); // list of all Country object        
        log.info("Connection is open: " + connection);
        
        resultSet = selectAll.executeQuery();   // execute query
        
        /* set data to all new Countries */
        while (resultSet.next()) {
            Country country = new Country();
            country.setCountryID(resultSet.getLong(COUNTRY_ID));
            country.setCountryName(resultSet.getString(COUNTRY_NAME));
            country.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            list.add(country);
        }        
        log.info("All countries are readed.");
        
        resultSet.close();              // close result set
        closeAll(selectAll,connection); // close connection and statement
        return list;
    }
    
    /**
     * geTop() method return top N Cuntry by total amount column
     * @param top int
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Country> geTop(int top) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select top Country */
        PreparedStatement selectTop 
                = connection.prepareStatement("SELECT * FROM countries "
                        + "ORDER BY total_amount DESC LIMIT ?;");
        
        ResultSet resultSet;                    // result set of execute query        
        List<Country> list = new ArrayList<>(); // result list of Countries
        log.info("Connection is open: " + connection);
        
        selectTop.setInt(1, top);               // set number of selected rows
        resultSet = selectTop.executeQuery();   // execute query
        
        /* set data to top N Country objects */
        while (resultSet.next()) {
            Country country = new Country();
            country.setCountryID(resultSet.getLong(COUNTRY_ID));
            country.setCountryName(resultSet.getString(COUNTRY_NAME));
            country.setTotalAmount(resultSet.getLong(TOTAL_AMOUNT));
            list.add(country);
        }        
        log.info("Top " + top + " countries is readed.");
        
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