/*
* @(#)FrontControllerHelper.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides "get command" for FrontController servlet. 
*/

package com.prouders.controller.servlet;

import com.prouders.controller.commands.*;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 * The FrontControllerHelper class provides singleton realization
 * for helper class for FrontController servlet that choose command
 * for page buiding
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class FrontControllerHelper {
    /* instanse of this class */
    private static FrontControllerHelper instance = null;   
    /* HashMap for all project commands */
    HashMap<String, ICommand> commands;

    /**
     * constructor without parameters that build HashMap of commands
     */
    private FrontControllerHelper() {
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("forgotpassword", new ForgotPasswordCommand());
        commands.put("prouders", new ProudersCommand());
        commands.put("prouder", new ProuderCommand());
        commands.put("stories", new StoriesCommand());
        commands.put("story", new StoryCommand());
        commands.put("communities", new CommunitiesCommand());
        commands.put("community", new CommunityCommand());
        commands.put("countries", new CountriesCommand());
        commands.put("country", new CountryCommand());
        commands.put("rules", new RulesCommand());
        commands.put("about", new AboutCommand());
        commands.put("doregistration", new DoRegistrationCommand());
        commands.put("locale", new LocaleCommand());
        commands.put("newcommunity", new NewCommunityCommand());
        commands.put("newstory", new NewStoryCommand());
        commands.put("donewcommunity", new DoNewCommunityCommand());
        commands.put("donewstory", new DoNewStoryCommand());
        commands.put("proudstory", new ProudStoryCommand());
        commands.put("userprofile", new UserProfileCommand());
        commands.put("profilechange", new ProfileChangeCommand());
        commands.put("adminchangestory", new AdminChangeStoryCommand());
        commands.put("adminchangeprouder", new AdminChangeProuderCommand());
        commands.put("addcommunitytoprouder", new AddCommunityToProuder());
    }

    /**
     * method retunr instance of FrontControllerHelper class
     * @return 
     */
    public static FrontControllerHelper getInstance() {
        if (instance == null) {
            instance = new FrontControllerHelper();
        }
        return instance;
    }
    
    /**
     * mthod returncommand by parameter "command" from page request
     * @param request HttpServletRequest
     * @return ICommand
     */
    public ICommand getCommand(HttpServletRequest request) {
        /* get "command" parameter */
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null) {
            command = new MissingCommand(); // if command not exist
        }
        return command;                     // if command exist
    }
}