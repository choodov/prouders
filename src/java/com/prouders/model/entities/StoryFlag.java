/*
* @(#)StoryFlag.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides StoryFlag entity class. 
*/

package com.prouders.model.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The StoryFlag class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class StoryFlag implements Serializable{
    // private fields
    @Id
    private Long flagID;
    private String storyFlag;

    /**
     * empty constructor
     */
    public StoryFlag() {}
    
    /**
     * constructor with one parameter
     * @param flagID 
     */
    public StoryFlag(Long flagID) {
        this.flagID = flagID;
    }

    /**
     * constructor with parameters
     * @param flagID
     * @param storyFlag 
     */
    public StoryFlag(Long flagID, String storyFlag) {
        this.flagID = flagID;
        this.storyFlag = storyFlag;
    }
    
    // get and set merhods
    public Long getFlagID() {
        return flagID;
    }

    public void setFlagID(Long flagID) {
        this.flagID = flagID;
    }

    public String getStoryFlag() {
        return storyFlag;
    }

    public void setStoryFlag(String storyFlag) {
        this.storyFlag = storyFlag;
    }
    
    // hashCode method
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flagID != null ? flagID.hashCode() : 0);
        return hash;
    }

    // equals method
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof StoryFlag)) {
            return false;
        }
        StoryFlag other = (StoryFlag) object;
        if ((this.flagID == null && other.flagID != null) 
                || (this.flagID != null && !this.flagID.equals(other.flagID))) {
            return false;
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "com.prouders.model.objects.StoryFlag[ flagID=" + flagID + " ]";
    }
}
