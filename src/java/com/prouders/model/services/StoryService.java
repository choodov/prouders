/*
* @(#)StoryService.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO class with methods for "Story" MySQL database. 
*/

package com.prouders.model.services;

import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.IStoryDAO;
import com.prouders.model.dao.interfaces.IStoryFlagDAO;
import com.prouders.model.entities.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * The StoryService class provides several methods to work with DAO layer:
 * readStory(), readAllStoryByCountryName(), readAllStory(),
 * readAllStoryByCommunityName(), readAllStoryByProuder(), readTopStory()
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class StoryService {
    private static final DAOFactory factory 
            = DAOFactory.getDAOFactory();       // getting DAO factory
    
    /*logger for StoryService class*/
    private static final Logger log 
            = Logger.getLogger(StoryService.class);
    
    /**
     * synchronized method that read all data about Story
     * @param storyID Long
     * @return Story
     */
    public static synchronized Story readStory(Long storyID) {
        IStoryDAO storyDAO;         // link to storyDAO
        IStoryFlagDAO storyFlagDAO; // link to storyFlagDAO
        Story story = null;         // link to Story object

        try {
            storyDAO = factory.getStoryDAO();           // new storyDAO
            storyFlagDAO = factory.getStoryFlagDAO();   // new storyFlagDAO

            /* read Story by ID */
            story = storyDAO.read(storyID);
            /* read Prouder by ID and set this prouder to Story */
            story.setProuder(ProuderService.readProuder
                    (story.getProuder().getID()));
            /* read Community by ID and set this Community to Story */
            story.setCommunity(CommunityService.readCommunity
                    (story.getCommunity().getID()));
            /* read Flag by ID and set this Flag to Story */
            story.setFlag(storyFlagDAO.fill(story.getFlag()));
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return story;
    }
    
    /**
     * synchronized method that read all Story
     * @return List
     */
    public static synchronized List<Story> readAllStory() {
        IStoryDAO storyDAO;                     // link to storyDAO
        IStoryFlagDAO storyFlagDAO;             // link to storyFlagDAO
        List<Story> list = new ArrayList<>();   // new ArrayList of Story

        try {
            storyDAO = factory.getStoryDAO();           // new storyDAO 
            storyFlagDAO = factory.getStoryFlagDAO();   // new storyFlagDAO

            /* get all Story*/
            list = storyDAO.getAll();
            for(Story story : list) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                        (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
            }   
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return list;
    }
    
    /**
     * synchronized method that read all new Story
     * @return List
     */
    public static synchronized List<Story> readAllNewOrPreparedStory() {
        IStoryDAO storyDAO;                         // link to storyDAO
        IStoryFlagDAO storyFlagDAO;                 // link to storyFlagDAO
        List<Story> list;                           // new ArrayList of Story
        List<Story> resulatlist = new ArrayList<>();// result ArrayList of Story
        
        try {
            storyDAO = factory.getStoryDAO();           // new storyDAO 
            storyFlagDAO = factory.getStoryFlagDAO();   // new storyFlagDAO

            /* get all Story*/
            list = storyDAO.getAllForAdmin();
            for(Story story : list) {
                if((story.getFlag().getFlagID() == 1) 
                        || (story.getFlag().getFlagID() == 2)) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                        (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
                resulatlist.add(story);
                }
            }   
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return resulatlist;
    }
    
    /**
     * synchronized method that read all Story by Country name
     * @param countryName String
     * @return List
     */
    public static synchronized List<Story> 
        readAllStoryByCountryName(String countryName) {
        
        IStoryDAO storyDAO;                         // link to IStoryDAO
        IStoryFlagDAO storyFlagDAO;                 // link to storyFlagDAO
        List<Story> list;       // new ArrayList of Story
        List<Story> resultList = new ArrayList<>(); // new ArrayList for result

        try {
            storyDAO = factory.getStoryDAO();           // new storyDAO 
            storyFlagDAO = factory.getStoryFlagDAO();   // new storyFlagDAO 

            /* get all Story*/
            list = storyDAO.getAll();
            for(Story story : list) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                        (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
            }

            for(Story story: list) {
                /* if Country names are equal that add Story to result */
                if (story.getProuder().getCountry().getCountryName().equals
                        (countryName)) {
                    resultList.add(story);
                }
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return resultList;
    }
    
    /**
     * synchronized method that read all Story by Community name
     * @param communityName String
     * @return List
     */    
    public static synchronized List<Story> 
        readAllStoryByCommunityName(String communityName) {
        
        IStoryDAO storyDAO;                         // link to IStoryDAO
        IStoryFlagDAO storyFlagDAO;                 // link to IStoryFlagDAO
        List<Story> list;       // new ArrayList of Story
        List<Story> resultList = new ArrayList<>(); // new ArrayList for result

        try {
            storyDAO = factory.getStoryDAO();         // new storyDAO
            storyFlagDAO = factory.getStoryFlagDAO(); // new storyFlagDAO  

            /* get all Story*/
            list = storyDAO.getAll();
            for(Story story : list) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                        (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
            }

            for(Story story: list) {
                /* if Community names are equal that add Story to result */
                if (story.getCommunity().getName().equals(communityName)) {
                    resultList.add(story);
                }
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return resultList;
    }
    
    /**
     * synchronized method that read all Story by Prouder name
     * @param prouderName String
     * @return List
     */
    public static synchronized List<Story> 
        readAllStoryByProuder(String prouderName) {
        
        IStoryDAO storyDAO;                         // link to IStoryDAO
        IStoryFlagDAO storyFlagDAO;                 // link to IStoryFlagDAO
        List<Story> list;       // new ArrayList of Story
        List<Story> resultList = new ArrayList<>(); // new ArrayList for result

        try {
            storyDAO = factory.getStoryDAO();          // new storyDAO 
            storyFlagDAO = factory.getStoryFlagDAO();  // new storyFlagDAO

            /* get all Story*/
            list = storyDAO.getAll();
            for(Story story : list) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                    (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
            }

            for(Story story: list) {
                /* if Prouder names are equal that add Story to result */
                if (story.getProuder().getName().equals(prouderName)) {
                    resultList.add(story);
                }
            }
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return resultList;
    }
    
    /**
     * synchronized method that read top N Stories
     * @param top int 
     * @return List
     */
    public static synchronized List<Story> readTopStory(int top) {
        IStoryDAO storyDAO;                     // link to IStoryDAO
        IStoryFlagDAO storyFlagDAO;             // link to IStoryFlagDAO
        List<Story> list = new ArrayList<>();   // new ArrayList of Story

        try {
            storyDAO = factory.getStoryDAO();           // new storyDAO
            storyFlagDAO = factory.getStoryFlagDAO();   // new storyFlagDAO

            /* get all Story*/
            list = storyDAO.geTop(top);
            for(Story story : list) {
                /* read Prouder by ID and set this prouder to Story */
                story.setProuder(ProuderService.readProuder
                        (story.getProuder().getID()));
                /* read Community by ID and set this Community to Story */
                story.setCommunity(CommunityService.readCommunity
                        (story.getCommunity().getID()));
                /* read Flag by ID and set this Flag to Story */
                story.setFlag(storyFlagDAO.fill(story.getFlag()));
            }   
        } catch (SQLException ex) {
            log.error("Getting DAO ended with exeption: " + ex);
        }
        return list;
    }
}
