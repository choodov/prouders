/*
* @(#)CommunityType.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides CommunityType entity class. 
*/

package com.prouders.model.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The CommunityType class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class CommunityType implements Serializable{
    // private fields
    @Id
    private Long typeID;
    private String communityType;
    
    /**
     * empty constructor 
     */
    public CommunityType() {}
    
    // get and set merhods
    public CommunityType(Long typeID) {
        this.typeID = typeID;
    }

    public CommunityType(Long typeID, String communityType) {
        this.typeID = typeID;
        this.communityType = communityType;
    }
    
    public Long getTypeID() {
        return typeID;
    }
    
    public void setTypeID(Long typeID) {
        this.typeID = typeID;
    }
    
    public String getCommunityType() {
        return communityType;
    }
    
    public void setCommunityType(String communityType) {
        this.communityType = communityType;
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
        if (!(object instanceof CommunityType)) {
            return false;
        }
        CommunityType other = (CommunityType) object;
        if ((this.typeID == null && other.typeID != null) 
          || (this.typeID != null && !this.typeID.equals(other.typeID))) {
            return false;
        }
        return true;
    } 
    
    // toString method
    @Override
    public String toString() {
        return "com.prouders.model.objects.CommunityType[ typeID=" 
                + typeID + " ]";
    }
}
