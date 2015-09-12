/*
* @(#)DoRegistrationCommand.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides command for Registration page. 
*/

package com.prouders.controller.commands;

import com.prouders.controller.manager.Config;
import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.dao.interfaces.IProuderDAO;
import com.prouders.model.dao.interfaces.IUserTypeDAO;
import com.prouders.model.entities.Country;
import com.prouders.model.entities.Prouder;
import com.prouders.model.entities.Status;
import com.prouders.model.entities.UserType;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.*;
import org.apache.log4j.Logger;

/**
 * The DoRegistrationCommand class provides 
 * registration and checking entered parameters in
 * registraation form
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class DoRegistrationCommand implements ICommand{
    /* final fields for attributes in registration form */
    private static final String NAME = "name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String CONFPASSWORD = "confpassword";
    private static final String COUNTRY = "country";
    private static final String CHECKRULES = "checkrules";
    
    DAOFactory factory = DAOFactory.getDAOFactory();// DAO factory
    IProuderDAO prouderDAO;                         // IProuderDAO instance
    ICountryDAO countryDAO;                         // ICountryDAO instance
    List<Country> list;                             // list of country
    
    /*logger for DoRegistrationCommand class*/
    private static final Logger log 
            = Logger.getLogger(DoRegistrationCommand.class);
    
    /**
     * method do registration process with checking entered attributes
     * @param request
     * @param response
     * @return 
     */
    @Override
    public String execute(HttpServletRequest request, 
            HttpServletResponse response) {
        
        String page = null;           // new page name
        
        String checkNameError = "false";
        String checkEmailError = "false";
        String checkPasswordsError = "false";
        String checkRulesError = "false";
        
        boolean checkFlag = false;    // flag for all errors
        
        /* if name exist or emply - error + checkFlag = true */
        if(!checkName(request.getParameter(NAME))) {
            checkNameError = "true";
            checkFlag = true;
            log.info("Registration: name checking failed.");
        }
        
        /* if email not valid - error + checkFlag = true */
        if(!checkEmail(request.getParameter(EMAIL))) {
            checkEmailError = "true";
            checkFlag = true;
            log.info("Registration: email checking failed.");
        }
        
        /* if password not valid - error + checkFlag = true */
        if(!checkPasswords(request.getParameter(PASSWORD),
                request.getParameter(CONFPASSWORD))) {
            checkPasswordsError = "true";
            checkFlag = true;
            log.info("Registration: password checking failed.");
        }
        
        /* if check rules flag false  - error + checkFlag = true */
        if(!checkRules(request.getParameter(CHECKRULES))) {
            checkRulesError = "true";
            checkFlag = true;
            log.info("Registration: checkRules flag checking failed.");
        }
        
        /* if at least one checking fail - return to registration page */
        if(checkFlag) {
            try {
                countryDAO = factory.getCountryDAO();// get country DAO
                list = countryDAO.getAll();          // get list of all country
                request.setAttribute("countryList", list);
            } catch (SQLException ex) {
                log.error("Could not get all country for registration page,"
                        + "exception: " + ex);
            }
            
            request.setAttribute("checkFlag", checkFlag);
            request.setAttribute("checkNameError", checkNameError);
            request.setAttribute("checkEmailError", checkEmailError);
            request.setAttribute("checkPasswordsError", checkPasswordsError);
            request.setAttribute("checkRulesError", checkRulesError);
            request.setAttribute("countryList", list);
            log.info("Registration: error attribute setted.");
            page = Config.getInstance().getProperty(Config.REGISTRATION);
        } else {
            /* all checking executes good - registration process */
            Prouder prouder = new Prouder();    // new Prouder
            /* set all data to Prouders fields */
            prouder.setName(request.getParameter(NAME));
            prouder.setEmail(request.getParameter(EMAIL));
            prouder.setPassword(request.getParameter(PASSWORD));
            prouder.setPhoto("D:\\myPhoto.jpg");
            /* set country */
            Country country = null;
            try {
                countryDAO = factory.getCountryDAO();
                country = countryDAO.readByName(request.getParameter(COUNTRY));
            } catch (SQLException ex) {
                log.error("SQLException by reading countryDAO");
            }
            prouder.setCountry(country);
            /* set user type 1 = prouder */
            UserType type = null;
            try {
                IUserTypeDAO userTypeDAO = factory.getUserTypeDAO();
                type = userTypeDAO.read(1);
            } catch (SQLException ex) {
                log.error("SQLException by reading userTypeDAO");
            }
            prouder.setType(type);
            prouder.setStatus(Status.ACTIVE); // set status
            log.info("New Prouder builded");

            try {
                prouderDAO = factory.getProuderDAO(); // new prouderDAO
                /* if create(registration) new prouder success */
                if(prouderDAO.create(prouder)) {
                    request.setAttribute("registrationMessage", "true");
                    page = Config.getInstance().getProperty(Config.MAIN);
                    log.info("New Prouder write to database");
                } else {
                    /* if create(registration) new prouder failed */
                    request.setAttribute("error", "REGISTRATION_ERROR");
                    page = Config.getInstance().getProperty(Config.ERROR);
                    log.info("Could not write new prouder to database");
                }
            } catch (SQLException ex) {
                log.error("SQL exception while write "
                        + "new prouder to database" + ex);
            } 
        }
        return page;        
    } 
    
    /**
     * method check new prouder name
     * @param name String
     * @return boolaen
     */
    private boolean checkName(String name) {
        boolean  checkName = false; // checking flag
        try {
            prouderDAO = factory.getProuderDAO();
            /* if name are valid and not empty */
            if((!prouderDAO.checkName(name)) && (name.length() > 0)) {
                checkName = true;
                log.info("Registration: name checking valid");
            }
        } catch (SQLException ex) {
            log.error("Exception while checking name: " + ex);
        }
        return checkName;
    }
    
    /**
     * method check new prouder email
     * @param email String
     * @return boolean
     */
    private boolean checkEmail(String email) {
        /* create pattern for check email: 
         * sDomen - part of email before and after @ 
         */
        String sDomen = "[a-z][a-z[0-9]\u005F\u002E\u002D]*[a-z||0-9]";
        /* sDomen2 - end part of email: com, ua, etc. */
        String sDomen2 = "([a-z]){2,4}";
        Pattern pattern                           // compile pattern
                = Pattern.compile(sDomen + "@" + sDomen + "\\u002E" + sDomen2);
        Matcher matcher = pattern.matcher(email); // get matcher
        return matcher.matches();                 // match pattern and email
    }
    
    /**
     * method check new prouder password
     * @param password String
     * @param confirm String
     * @return boolean
     */
    private boolean checkPasswords(String password, String confirm) {
        /* entered passwords are equal and paswords not empty */
        return (password.equals(confirm) && (password.length() > 0)) 
                ? true : false;
    }
    
    /**
     * method check new prouder checkRules flag
     * @param checkrules String
     * @return boolean
     */
    private boolean checkRules(String checkrules) {
        /* flag - true? */
        return (checkrules != null) ? true : false;
    }
}
