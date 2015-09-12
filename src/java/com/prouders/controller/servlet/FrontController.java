/*
* @(#)FrontController.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This is the main single servlet for project. 
* He process all requst and responce.
*/

package com.prouders.controller.servlet;

import com.prouders.controller.commands.ICommand;
import com.prouders.controller.manager.Message;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * The FrontController servlet process all requst and responce from JSPs.
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class FrontController extends HttpServlet {
    /* FrontControllerHelper instance */
    FrontControllerHelper helper = FrontControllerHelper.getInstance();
    
    /*logger for FrontController class*/
    private static final Logger log = Logger.getLogger(FrontController.class);
    
    /**
     * empty constructor for servlet
     */
    public FrontController() {
        super();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        
        String page = null;                                 // return page
        request.setCharacterEncoding("UTF-8");              // set Unicode
        response.setContentType("text/html;charset=UTF-8"); // set Unicode
                
        try {
            ICommand command = helper.getCommand(request);//get commend 
            page = command.execute(request, response);    // execute command
            log.info("Command " + command + " executes sucessful.");
        } catch (ServletException e) {
            log.error("Command execute with servlet exception: " + e);
            /* return error masage */
            request.setAttribute("messageError", 
                Message.getInstance().getProperty(Message.SERVLET_EXECPTION));

        } catch (IOException e) {
            log.error("Command execute with IOException: " + e);
            /* return error masage */
            request.setAttribute("messageError", 
                Message.getInstance().getProperty(Message.IO_EXCEPTION));

        }
        
        RequestDispatcher dispatcher 
                = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);  // forward to dispatcher
    }
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
