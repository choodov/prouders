/*
* @(#)Config.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides getting configuration parameter 
* from bundle file "config.properties". 
*/

package com.prouders.controller.manager;

import java.util.ResourceBundle;

/**
 * The Config class provides singleton realization
 * for getting configuration parameter 
 * from bundle file "config.properties"
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class Config {

    private static Config instance;                    // instance of this class
    private ResourceBundle resource;                   // bundle resource
    private static final String BUNDLE_NAME 
            = "com.prouders.controller.manager.config";// bundle name
    /* parameters final filelds */
    public static final String DRIVER = "DRIVER";
    public static final String URL = "URL";
    public static final String INDEX = "INDEX";
    public static final String ADMIN = "ADMIN";
    public static final String MAIN = "MAIN";
    public static final String ERROR = "ERROR";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTRATION = "REGISTRATION";
    public static final String COUNTRIES = "COUNTRIES";
    public static final String COMMUNITIES = "COMMUNITIES";
    public static final String COMMUNITY = "COMMUNITY";
    public static final String STORIES = "STORIES";
    public static final String STORY = "STORY";
    public static final String PROUDERS = "PROUDERS";
    public static final String PROUDER = "PROUDER";
    public static final String FORGOTPWD = "FORGOTPWD";
    public static final String SMTPPORT = "SMTPPORT";
    public static final String EMAILFROM = "EMAILFROM";
    public static final String EMAILUSER = "EMAILUSER";
    public static final String EMAILPWD = "EMAILPWD";
    public static final String EMAILHOST = "EMAILHOST";
    public static final String STORY_REGISTR_MIN_COUNT 
            = "STORY_REGISTR_MIN_COUNT";
    public static final String NEWSTORY = "NEWSTORY";
    public static final String NEWCOMMUNITY = "NEWCOMMUNITY";
    public static final String PROFILE = "PROFILE";
    public static final String MIN_ACTIVE_PROUDER_PROUDS 
            = "MIN_ACTIVE_PROUDER_PROUDS";
     public static final String MIN_PATRIOT_PROUDS = "MIN_PATRIOT_PROUDS";       

    /**
     * method return instance of this class
     * @return Config
     */
    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();    // new instance
            /* get resource bundle */
            instance.resource = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    /**
     * method return property by key
     * @param key
     * @return 
     */
    public String getProperty(String key) {
        return (String) resource.getObject(key);
    }
}
