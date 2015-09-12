/*
* @(#)MySQLProuderDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "prouder" MySQL database. 
*/

package com.prouders.model.dao.objects;

import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.db.DBCP;
import com.prouders.model.entities.Country;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.UserType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The MySQLProuderDAO class provides MySQL DB DAO layer 
 * for table prouders.country and contains several methods:
 * create(), read(), readByEmail(), checkLogin(), checkEmail(), 
 * checkName(), update(), getAll(), 
 * and closeAll() to close connection
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class MySQLProuderDAO implements IProuderDAO{
    /* final fields for columns in community table */
    protected static final int PROUDER_ID = 1;          // prouder_id
    protected static final int PROUDER_NAME = 2;        // prouder_name
    protected static final int PROUDER_EMAIL = 3;       // prouder_email
    protected static final int PROUDER_PASSWORD = 4;    // prouder_password
    protected static final int PROUDER_PHOTO = 5;       // prouder_photo
    protected static final int COUNTRY_ID = 6;          // country_id
    protected static final int TYPE_ID = 7;             // type_id
    protected static final int PROUDER_STATUS = 8;      // prouder_status
    
    /*logger for MySQLProuderDAO class*/
    private static final Logger log = Logger.getLogger(MySQLProuderDAO.class);
    
    /**
     * Empty constructor for MySQLProuderDAO
     * @throws SQLException 
     */
    public MySQLProuderDAO() throws SQLException {}
    
    /**
     * create() method insert new data to prouders table 
     * @param prouder Prouder
     * @return result boolean
     * @throws SQLException 
     */
    @Override
    public boolean create(Prouder prouder) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for inserting to prouders table*/
        PreparedStatement create 
                = connection.prepareStatement("INSERT INTO prouders "
                        + "(prouder_name, prouder_email, prouder_password, "
                        + "prouder_photo, country_id, type_id, prouder_status) "
                        + "VALUES (?,?,?,?,?,?,?);");
        
        boolean result;     // method return result
        log.info("Connection is open: " + connection);
       
       /*add parameters to prepared statement:*/
        create.setString(1, prouder.getName());
        create.setString(2, prouder.getEmail());
        create.setString(3, prouder.getPassword());
        create.setString(4, prouder.getPhoto());
        create.setLong(5, prouder.getCountry().getCountryID());
        create.setLong(6, prouder.getType().getTypeID());
        create.setLong(7, prouder.getStatus().getValue());
        
        /*if statement execute success result = true*/
        result = ((create.executeUpdate() > 0) ? true : false);        
        log.info("CREATE PROUDER query is executed whith result: " + result);
        
        closeAll(create,connection);    // close connection and statement
        return result;
    }
    
    /**
     * read() method read data from countries table 
     * @param prouderID Long
     * @return prouder Prouder
     * @throws SQLException 
     */
    @Override
    public Prouder read(Long prouderID) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement for select from prouders table by prouder ID*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM prouders "
                        + "WHERE prouder_id=?;");
        
        ResultSet resultSet;    // result for execute query
        Prouder prouder = null; // new Prouder object link
        log.info("Connection is open: " + connection);
        
        /*add parameter to prepared statement:*/
        read.setLong(1,prouderID);
        resultSet = read.executeQuery();    // execute query
        log.info("READ PROUDER query is executed");
        
        /*set all data to new Prouder object*/
        while (resultSet.next()) {
            prouder = new Prouder();
            prouder.setID(resultSet.getLong(PROUDER_ID));
            prouder.setName(resultSet.getString(PROUDER_NAME));
            prouder.setEmail(resultSet.getString(PROUDER_EMAIL));
            prouder.setPassword(resultSet.getString(PROUDER_PASSWORD));
            prouder.setPhoto(resultSet.getString(PROUDER_PHOTO));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            prouder.setCountry(country);
            UserType userType = new UserType(resultSet.getLong(TYPE_ID));
            prouder.setType(userType);
            prouder.setStatus(Status.getStatus
                    (resultSet.getInt(PROUDER_STATUS)));
        }
        log.info("Prouder is readed.");
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return prouder;
    }
    
    /**
     * readByEmail() method return Prouder object with given email
     * @param email String
     * @return prouder Prouder
     * @throws SQLException 
     */
    @Override
    public Prouder readByEmail(String email) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared stement for selecting Prouder data by email*/
        PreparedStatement read 
                = connection.prepareStatement("SELECT * FROM prouders "
                        + "WHERE prouder_email=?;");
        
        ResultSet resultSet;        // result for execute query
        Prouder prouder = null;     // Country object link
        log.info("Connection is open: " + connection);
        
        /* add email to prepared statement*/
        read.setString(1,email);
        resultSet = read.executeQuery();        // execute query
        log.info("READ BY EMAIL PROUDER query is executed");
        
        /* set all data to new Prouder object*/
        while (resultSet.next()) {
            prouder = new Prouder();
            prouder.setID(resultSet.getLong(PROUDER_ID));
            prouder.setName(resultSet.getString(PROUDER_NAME));
            prouder.setEmail(resultSet.getString(PROUDER_EMAIL));
            prouder.setPassword(resultSet.getString(PROUDER_PASSWORD));
            prouder.setPhoto(resultSet.getString(PROUDER_PHOTO));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            prouder.setCountry(country);
            UserType userType = new UserType(resultSet.getLong(TYPE_ID));
            prouder.setType(userType);
            prouder.setStatus(Status.getStatus
                    (resultSet.getInt(PROUDER_STATUS)));
        }
        log.info("Prouder is readed by email: " + email);
        
        resultSet.close();          // close result set
        closeAll(read,connection);  // close connection and statement
        return prouder;
    }
    
    
    /**
     * checkLogin() method check login by email and password
     * @param email String
     * @param password String
     * @return check boolean
     * @throws SQLException 
     */
    @Override
    public boolean checkLogin(String email, String password) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared stement for selecting Prouders data by email*/
        PreparedStatement checkLogin = connection.prepareStatement("SELECT "
            + "* FROM prouders WHERE prouder_email=?;");
        
        ResultSet resultSet;    // result for execute query
        boolean check;          // result for check
        log.info("Connection is open: " + connection);
        
        /* add email to prepared statement*/
        checkLogin.setString(1, email);
        resultSet = checkLogin.executeQuery();
        
        /* check email exist and password equals */
        if (resultSet.next()) {     // email exist
            log.info("CHECK LOGIN query is executed");
            if(password.equals(resultSet.getString(PROUDER_PASSWORD))) {
                check = true;   // passwords are equal
            } else {
                check = false;  // passwords are not equal
            }            
        } else {
            check = false;      // email does not exist
        }
        log.info("Checking are ended with result: " + check);
        
        resultSet.close();                  // close result set
        closeAll(checkLogin,connection);    // close connection and statement
        return check;
    }
    
    /**
     * checkLogin() method check email existing
     * @param email String
     * @return check boolean
     * @throws SQLException 
     */
    @Override
    public boolean checkEmail(String email) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /*prepared stement for selecting Prouders data by email*/
        PreparedStatement checkEmail 
                = connection.prepareStatement("SELECT * FROM prouders "
                        + "WHERE prouder_email=?;");
        
        ResultSet resultSet;    // result for execute query
        boolean check;          // result for check
        log.info("Connection is open: " + connection);
        
        /* add email to prepared statement*/
        checkEmail.setString(1, email);
        resultSet = checkEmail.executeQuery();
        
        /* check email exist*/
        if (resultSet.next()) {     
            log.info("CKECK EMAIL query is executed");
            check = true;      // email exist      
        } else {
            check = false;     // email does not exist
        }        
        log.info("Checking are ended with result: " + check);
        
        resultSet.close();                  // close result set
        closeAll(checkEmail,connection);    // close connection and statement
        return check;
    }
    
    /**
     * checkName() method check name existing
     * @param name String
     * @return check boolean
     * @throws SQLException 
     */ 
    @Override
    public boolean checkName(String name) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();       // new connection
        
        /*prepared stement for selecting Prouders data by name */
        PreparedStatement checkName
                = connection.prepareStatement("SELECT * FROM prouders "
                        + "WHERE prouder_name=?;");
        
        ResultSet resultSet;    // result for execute query
        boolean check;          // result for check
        log.info("Connection is open: " + connection);
        
        /* add name to prepared statement*/
        checkName.setString(1, name);
        resultSet = checkName.executeQuery();
        
        /* check name exist */
        if (resultSet.next()) {
            log.info("CKECK NAME query is executed");
            check = true;       // name exist     
        } else {
            check = false;      // name does not exist
        }       
        log.info("Checking are ended with result: " + check);
        
        resultSet.close();                // close result set  
        closeAll(checkName,connection);  // close connection and statement  
        return check;
    }
      
    /**
     * update() method updates data fro given Prouder object
     * @param prouder Prouder
     * @return  result boolean
     * @throws SQLException 
     */
    @Override
    public boolean update(Prouder prouder) throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();      // new connection
        
        /* prepared statement to update prouders table*/
        PreparedStatement update 
                = connection.prepareStatement("UPDATE prouders "
                        + "SET prouder_name=?, prouder_email=?, "
                        + "prouder_password=?, prouder_photo=?, country_id=?, "
                        + "type_id=?, prouder_status=? "
                        + "WHERE prouder_id=?;");
        
        boolean result;         // result of update
        log.info("Connection is open: " + connection);
        
        /* add parameters to prapared statement*/
        update.setString(1, prouder.getName());
        update.setString(2, prouder.getEmail());
        update.setString(3, prouder.getPassword());
        update.setString(4, prouder.getPhoto());
        update.setLong(5, prouder.getCountry().getCountryID());
        update.setLong(6, prouder.getType().getTypeID());
        update.setInt(7, prouder.getStatus().getValue());
        update.setLong(8, prouder.getID());
        
        /* if update executes result = true */
        result = ((update.executeUpdate() > 0) ? true : false);        
        log.info("UPDATE PROUDER query is executed whith result: " + result);
        
        closeAll(update,connection);    // close connection and statement
        return result;
    }
    
    /**
     * getAll() method return all rows from prouders table
     * @return list List
     * @throws SQLException 
     */
    @Override
    public List<Prouder> getAll() throws SQLException {
        Connection connection 
                = DBCP.getInstance().getConnection();   // new connection
        
        /* prepared statement to select all rows from prouders table */
        PreparedStatement selectAll 
                = connection.prepareStatement("SELECT * FROM prouders;");
        
        ResultSet resultSet;                    // result set of execute query
        List<Prouder> list = new ArrayList<>(); // list of all Prouder object
        log.info("Connection is open: " + connection);
        
        resultSet = selectAll.executeQuery();   // execute query
 
        /* set data to all new Prouder */
        while (resultSet.next()) {
            Prouder prouder = new Prouder();
            prouder.setID(resultSet.getLong(PROUDER_ID));
            prouder.setName(resultSet.getString(PROUDER_NAME));
            prouder.setEmail(resultSet.getString(PROUDER_EMAIL));
            prouder.setPassword(resultSet.getString(PROUDER_PASSWORD));
            prouder.setPhoto(resultSet.getString(PROUDER_PHOTO));
            Country country = new Country(resultSet.getLong(COUNTRY_ID));
            prouder.setCountry(country);
            UserType userType = new UserType(resultSet.getLong(TYPE_ID));
            prouder.setType(userType);
            prouder.setStatus(Status.getStatus
                    (resultSet.getInt(PROUDER_STATUS)));
            list.add(prouder);
        }
        log.info("All prouders are readed.");
        
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
