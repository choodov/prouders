/*
* @(#)Message.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides getting messages 
* from bundle file "messages.properties". 
*/

package com.prouders.controller.manager;

import java.util.ResourceBundle;

/**
 * The Message class provides singleton realization
 * for getting messages 
 * from bundle file "messages.properties"
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class Message {

    private static Message instance;                   // instance of this class
    private ResourceBundle resource;                   // bundle resource
    private static final String BUNDLE_NAME            // bundle name
            = "com.prouders.controller.manager.messages";
    /* parameters final filelds */
    public static final String SERVLET_EXECPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String LOGIN_ERROR = "LOGIN_ERROR";
    public static final String LOGIN_DELETED_ERROR = "LOGIN_DELETED_ERROR";
    public static final String REGISTRATION_ERROR = "REGISTRATION_ERROR";
    public static final String COUNTRIES_ERROR = "COUNTRIES_ERROR"; 
    public static final String COUNTRIY_ERROR = "COUNTRY_ERROR";
    public static final String COMMUNITIES_ERROR = "COMMUNITIES_ERROR";
    public static final String COMMUNITY_ERROR = "COMMUNITY_ERROR";
    public static final String STORY_ERROR = "STORY_ERROR";
    public static final String STORIES_ERROR = "STORIES_ERROR";
    public static final String PROUDERS_ERROR = "PROUDERS_ERROR";
    public static final String PROUDER_ERROR = "PROUDER_ERROR";
    public static final String FORGOTPWD_ERROR = "FORGOTPWD_ERROR";
    public static final String EMAIL_SUBJECT = "EMAIL_SUBJECT";
    public static final String EMAIL_BODY = "EMAIL_BODY"; 
    public static final String WRONG_NAME_ERROR = "WRONG_NAME_ERROR";
    public static final String WRONG_EMAIL_ERROR = "WRONG_EMAIL_ERROR";
    public static final String WRONG_PASSWORD_ERROR = "WRONG_PASSWORD_ERROR";
    public static final String WRONG_AGREE_ERROR = "WRONG_AGREE_ERROR";
    public static final String SUCCESS_REG = "SUCCESS_REG";
    public static final String TEXT_ABOUT = "TEXT_ABOUT";
    public static final String TEXT_ABOUT_BODY = "TEXT_ABOUT_BODY";
    public static final String TEXT_RULES = "TEXT_RULES";
    public static final String TEXT_RULES_1 = "TEXT_RULES_1";
    public static final String TEXT_RULES_2 = "TEXT_RULES_2";
    public static final String TEXT_RULES_3 = "TEXT_RULES_3";
    public static final String TEXT_RULES_4 = "TEXT_RULES_4";
    public static final String TEXT_TOP = "TEXT_TOP";
    public static final String TEXT_TOTAL_PROUDS = "TEXT_TOTAL_PROUDS";
    public static final String TEXT_COMMUNITIES_SMALL = "TEXT_COMMUNITIES_SMALL";
    public static final String TEXT_COUNTRIES_SMALL = "TEXT_COUNTRIES_SMALL";
    public static final String TEXT_STORIES_SMAL = "TEXT_STORIES_SMAL";
    public static final String TEXT_ERROR = "TEXT_ERROR";
    public static final String TEXT_SEND_PWD = "TEXT_SEND_PWD";
    public static final String TEXT_SUCCESS_PROFILE_CHANGE 
            = "TEXT_SUCCESS_PROFILE_CHANGE";
    public static final String TEXT_STORY_CHANG_ERROR 
            = "TEXT_STORY_CHANG_ERROR";
    public static final String TEXT_PROUDER_CHANG_ERROR 
            = "TEXT_PROUDER_CHANG_ERROR";
    
    /**
     * method return instance of this class
     * @return Message
     */
    public static Message getInstance() {
        if (instance == null) {
            instance = new Message();   // new instance
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
