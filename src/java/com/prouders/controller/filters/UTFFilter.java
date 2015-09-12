/*
* @(#)UTFFilter.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides filter that recoding UTF for request/responce. 
*/

package com.prouders.controller.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 * The UTFFilter class provides filter to recoding UTF for request/responce.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class UTFFilter implements Filter{
    
    FilterConfig filterConfig = null;       // FilterConfig instance
     
    /*logger for UTFFilter class*/
    private static final Logger log = Logger.getLogger(UTFFilter.class);
    
    /**
     * methid initiate filter with given config 
     * @param filterConfig
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;   // init filter config
        log.info("Initialization of filter success");
    }
    
    /**
     * main method that run filter with request and response from
     * jsp and filter chain
     * @param request
     * @param response
     * @param filterChain
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
        // reading encoding of response
        String responseEncoding = response.getCharacterEncoding();
        // reading encoding of request
        String requestEncoding = request.getCharacterEncoding();
        
        log.info("Encoding of responce: " + responseEncoding);
        log.info("Encoding of request: " + requestEncoding);
        
        // if not utf-8 - set
        if (!"UTF-8".equalsIgnoreCase(responseEncoding)) {
            request.setCharacterEncoding("UTF-8");
        }
        
        // if not utf-8 - set
        if (!"UTF-8".equalsIgnoreCase(requestEncoding)) {
            response.setCharacterEncoding("UTF-8");
        }
        
        log.info("UTF-8 setted for request and response");
        log.info("Encoding of responce: " + responseEncoding);
        log.info("Encoding of request: " + requestEncoding);
        
        filterChain.doFilter(request, response);// pass controll to next filter
    }
    
    /**
     * method destroy filter (end his fork)
     */
    @Override
    public void destroy() {
        filterConfig = null; 
        log.info("Destroy filter success");
    }
}
