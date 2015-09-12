/*
* @(#)PasswordGenerator.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides generating new password for user. 
*/

package com.prouders.controller.email;

import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.entities.Prouder;
import com.prouders.model.services.ProuderService;
import java.sql.SQLException;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 * The PasswordGenerator class provides 
 * method for genering new password
 * and chenge user password 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class PasswordGenerator {
    private static String newPwd;        // field for new password 
    static DAOFactory factory 
            = DAOFactory.getDAOFactory();// DAO factory
    static IProuderDAO prouderDAO;       // prouder DAO instance
    
    /*logger for PasswordGenerator class*/
    private static final Logger log = Logger.getLogger(PasswordGenerator.class);
    
    /**
     * method update password for user. User searching by email.
     * @param email String
     * @return String
     * @throws SQLException 
     */
    public static String getNewPwd(String email) throws SQLException {
        prouderDAO = factory.getProuderDAO();   // new prouderDAO instance
        Prouder prouder                         // get Poruder by email
                = ProuderService.readProuderByEmail(email);
        /* if no user - error */
        if(prouder == null) {
            log.error("No user with this email: " + email);
        } else {
            log.info(prouder.getName() + " user finded.");
        }
        
        newPwd = getPwd();                      // generating new password
        prouder.setPassword(newPwd);            // set password to prouder
        prouderDAO.update(prouder);             // update prouder     
        log.info("Prouder password updated.");
        return newPwd;
    }
    
    /**
     * method generate and return new password
     * @return String
     */
    private static String getPwd() {
        Random rand = new Random();     // random instance for randoming char[]
        char[] symbols                  // array of posible symbols for password
        = "ABCDEFGHIGKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        int leng = symbols.length;      // length of symbols
        String pwd = "";                // password field
       
        /* get random eight symbols and add them to password field*/
        for(int i=0; i<8; i++) {
            pwd += symbols[rand.nextInt(leng)];
        }
        log.info("New password generated.");
        return pwd;
    }
}
