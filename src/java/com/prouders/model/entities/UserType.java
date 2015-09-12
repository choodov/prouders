/*
* @(#)UserType.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides UserType entity class. 
*/

package com.prouders.model.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The UserType class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class UserType implements Serializable{
    // private fields
    @Id
    private Long typeID;
    private String userType;
    
    /**
     * empty constructor
     */
    public UserType() {}
    
    /**
     * constructor with one parameter
     * @param typeID 
     */
    public UserType(Long typeID) {
        this.typeID = typeID;
    }

    /**
     * constructor with parameters
     * @param typeID
     * @param userType 
     */
    public UserType(Long typeID, String userType) {
        this.typeID = typeID;
        this.userType = userType;
    }
    
    // get and set merhods
    public Long getTypeID() {
        return typeID;
    }
    
    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typeID != null ? typeID.hashCode() : 0);
        return hash;
    }

    // equals method
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserType)) {
            return false;
        }
        UserType other = (UserType) object;
        if ((this.typeID == null && other.typeID != null) 
                || (this.typeID != null && !this.typeID.equals(other.typeID))) {
            return false;
        }
        return true;
    }
    
    // toString method
    @Override
    public String toString() {
        return "com.prouders.model.objects.UserType[ typeID=" + typeID + " ]";
    }
}
