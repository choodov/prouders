/*
* @(#)TopTag.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides realization of user tag <TopTag> with two parameters. 
*/

package com.prouders.controller.tags;

import com.prouders.model.dao.DAOFactory;
import com.prouders.model.dao.interfaces.ICountryDAO;
import com.prouders.model.entities.Community;
import com.prouders.model.entities.Country;
import com.prouders.model.entities.Story;
import com.prouders.model.services.CommunityService;
import com.prouders.model.services.StoryService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;

/**
 * The TopTag class provides TopTag user tag rezlization
 * with two parameters: content and amount.
 * TopTag build tables with amount rows of content.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class TopTag extends SimpleTagSupport {
    private String content;     // content for table
    private String amount;      // amount of content rows
    JspWriter out;              // Java Server Page writer

    /*logger for TopTag class*/
    private static final Logger log = Logger.getLogger(TopTag.class);
    
    /**
     * set content parameter
     * @param content 
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * set amount parameter
     * @param amount 
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    /**
     * Called by the container to invoke this tag. The implementation of this
     * method is provided by the tag library developer, and handles all tag
     * processing, body iteration, etc.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public void doTag() throws JspException {
        out = getJspContext().getOut();     // get JSPContext out
        
        try {
            /* case methods by content  */
            switch(content) {
                case "countries": outCountriesTab();
                    break;
                case "stories": outStoriesTab();
                    break;
                case "communities": outCommunitiesTab();
                    break;
                default: out.write("Wrong context");
            }
        } catch (IOException ex) {
            throw new JspException("Error in TopTag tag", ex);
        }
    }

    /**
     * method build Countires table for JSP
     * @throws IOException 
     */
    private void outCountriesTab() throws IOException {
        DAOFactory factory = DAOFactory.getDAOFactory();// get DAOFactory
        ICountryDAO countryDAO;                         // ICountryDAO instance
        List<Country> list;                             // ArrayList of Country
      
        try {
            countryDAO = factory.getCountryDAO();       // new countryDAO
            /* get top (= amount) country list*/
            list = countryDAO.geTop(Integer.valueOf(amount));
            
            /* build HTML table for JSP */
            out.write("<table cellspacing=\"10\" cellpadding=\"0\">");
            for(Country item : list) {
                /* for all Country*/
                out.write("<tr><td>");
                out.write("<form name=\"CountryForm\" "
                        + "method=\"POST\" action=\"FrontController\">");
                out.write("<input type=\"hidden\" name=\"command\" "
                        + "value =\"country\"/>");
                out.write("<input type=\"hidden\" name=\"country\""); 
                out.write("value =\"" + item.getCountryName() + "\"/>");
                out.write("<input type=\"hidden\" name=\"countryID\""); 
                out.write("value =\"" + item.getCountryID() + "\"/>");
                out.write("<button type=\"submit\">" 
                        + item.getCountryName() + "</button>");
                out.write("</form></td>");
                out.write("<td>" + item.getTotalAmount() + "</td></tr>");
            }
            out.write("</table>");
        } catch (SQLException ex) {
            log.error("Country table buiding ended with exception: " + ex);
        }
        log.info("Country table building complited.");
    }
    
    /**
     * method build Communities table for JSP
     * @throws IOException 
     */
    private void outCommunitiesTab() throws IOException {
        List<Community> list;   // ArrayList of Community     
               
        /* get top (= amount) Community list*/
        list = CommunityService.readTopCommunity(Integer.valueOf(amount));
        
        /* build HTML table for JSP */
        out.write("<table cellspacing=\"10\" cellpadding=\"0\">");
            for(Community item : list) {
                /* for all Community*/
                out.write("<tr><td>");
                out.write("<form name=\"CommunityForm\" method=\"POST\" "
                        + "action=\"FrontController\">");
                out.write("<input type=\"hidden\" name=\"command\" "
                        + "value =\"community\"/>");
                out.write("<input type=\"hidden\" name=\"community\""); 
                out.write("value =\"" + item.getName() + "\"/>");
                out.write("<input type=\"hidden\" name=\"communityID\""); 
                out.write("value =\"" + item.getID() + "\"/>");
                out.write("<button type=\"submit\">" 
                        + item.getName() + "</button>");
                out.write("</form></td>");
                out.write("<td>" + item.getTotalAmount() + "</td></tr>");
            }
            out.write("</table>");
            log.info("Community table building complited.");
    }
    
    /**
     * method build Story table for JSP
     * @throws IOException 
     */
    private void outStoriesTab() throws IOException {
        /* get top (= amount) Story list*/
        List<Story> list = StoryService.readTopStory(Integer.valueOf(amount));
        
        /* build HTML table for JSP */
        out.write("<table cellspacing=\"10\" cellpadding=\"0\">");
            for(Story item : list) {
                /* for all Story*/
                out.write("<tr><td>");
                out.write("<form name=\"StoriesForm\" method=\"POST\" "
                        + "action=\"FrontController\">");
                out.write("<input type=\"hidden\" name=\"command\" "
                        + "value =\"story\"/>");
                out.write("<input type=\"hidden\" name=\"storyID\""); 
                out.write("value =\"" + item.getID() + "\"/>");
                out.write("<button type=\"submit\">" 
                        + item.getHeader() + "</button>");
                out.write("</form></td>");
                out.write("<td>" + item.getTotalAmount() + "</td></tr>");
            }
            out.write("</table>");
            log.info("Story table building complited.");
    }
}
