/*
* @(#)ICountryDAO.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides DAO interface 
* with abstract methods for "prouders" MySQL database. 
*/

package com.prouders.model.dao.interfaces;

import com.prouders.model.entities.Country;
import java.sql.SQLException;
import java.util.List;

/**
 * The ICountryDAO interface provides
 * abstract mehotds for MySQL DB DAO layer 
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public interface ICountryDAO {
   
    /**
     * abstract method for creating Country
     * @param country Country
     * @return boolean
     * @throws SQLException 
     */
   public boolean create(Country country) throws SQLException;

   /**
    * abstract method for reading Country by country ID
    * @param countryID Long
    * @return Country
    * @throws SQLException 
    */
    public Country read(Long countryID) throws SQLException;
   
   /**
    * abstract method for filling Country
    * @param country Country
    * @return Country
    * @throws SQLException 
    */
    public Country fill(Country country) throws SQLException;
   
   /**
    * abstract method for reading Country by name
    * @param countryName Sting
    * @return Country
    * @throws SQLException 
    */
    public Country readByName(String countryName) throws SQLException;

   /**
    * abstract method for updating Country
    * @param country Country
    * @return boolean
    * @throws SQLException 
    */
    public boolean update(Country country) throws SQLException;

   /**
    * abstract method for updating total amount of Country
    * @param country Country
    * @return boolean
    * @throws SQLException 
    */
    public boolean updateTotal(Country country) throws SQLException;
           
   /**
    * abstract method for getting all Country
    * @return List
    * @throws SQLException 
    */
    public List<Country> getAll() throws SQLException;
   
   /**
    * abstract method for getting top Country
    * @param top int
    * @return List
    * @throws SQLException 
    */
    public List<Country> geTop(int top) throws SQLException;
}
