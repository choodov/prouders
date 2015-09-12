/*
* @(#)DBCP.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DataBase Connection Pool. 
*/

package com.prouders.model.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * The DBCP class provides DataBase Connection Pool
 * that build by Singleton pattern
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class DBCP {

    private static volatile DBCP dbcp = null;   // instance to DBCP
    private static DataSource datasource;       // Data Source instance
    
    /*logger for DBCP class*/
    private static final Logger log = Logger.getLogger(DBCP.class);
    
    /**
     * costructor without parameters 
     * that create DataSource
     */
    private DBCP() {
        try {
            /* create new datasource by InitialContext */
            datasource = (DataSource)new InitialContext().lookup("java:"
                            + "comp/env/jdbc/prouders");
        } catch (NamingException ex) {
           log.error("Cannot build DBCP.");
        }
    }
    
    /**
     * create new DBCP instance
     */
    private static class DBCPHolder {
        private static final DBCP INSTANCE = new DBCP();
    }
 
    /**
     * return DBCPHolder instance
     * @return 
     */
    public static DBCP getInstance() {
        return DBCPHolder.INSTANCE;
    }
    
    /**
     * return new Connection
     * @return 
     */
    public Connection getConnection(){
        Connection conn = null;                 // Connection instans
        try {
            conn = datasource.getConnection();  // create new Connection
        } catch (SQLException ex) {
            log.error("Cannot get connection.");
        }        
        return conn;                            // return Connection
    }
}
