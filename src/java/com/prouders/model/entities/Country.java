/*
* @(#)Country.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides Country entity class. 
*/

package com.prouders.model.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Country class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class Country implements Serializable{
    // private fields
    @Id
    private Long countryID;
    private String countryName;
    private Long totalAmount; 
    
    /**
     * empty constructor
     */
    public Country() {}
    
    /**
     * constructor with one parameter
     * @param countryID 
     */
    public Country(Long countryID) {
        this.countryID = countryID;
    }

    /**
     * constructor with parameters
     * @param countryID
     * @param countryName
     * @param totalAmount 
     */
    public Country(Long countryID, String countryName, Long totalAmount) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.totalAmount = totalAmount;
    }
    
    // get and set merhods
    public Long getCountryID() {
        return countryID;
    }
    
    public void setCountryID(Long countryID) {
        this.countryID = countryID;
    }
    
    public String getCountryName() {
        return countryName;
    }
    
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    public Long getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countryID != null ? countryID.hashCode() : 0);
        return hash;
    }
    
    // equals method
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        if ((this.countryID == null && other.countryID != null) 
            || (this.countryID != null 
                && !this.countryID.equals(other.countryID))) {
            return false;
        }
        return true;
    } 
    
    // toString method
    @Override
    public String toString() {
        return "com.prouders.model.objects.Country[ countryID=" 
                + countryID + " ]";
    }
}
