/*
* @(#)ProuderCommunities.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides ProuderCommunities entity class. 
*/

package com.prouders.model.entities;

import java.util.Objects;
import java.io.Serializable;

/**
 * The ProuderCommunities class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProuderCommunities implements Serializable{
    // private fields
    private Prouder prouder;
    private Community community;
    
    /**
     * empty constructor
     */
    public ProuderCommunities() {}

    /**
     * constructor with parameters
     * @param prouder
     * @param community 
     */
    public ProuderCommunities(Prouder prouder, Community community) {
        this.prouder = prouder;
        this.community = community;
    }
    
    // get and set merhods
    public Prouder getProuder() {
        return prouder;
    }

    public void setProuder(Prouder prouder) {
        this.prouder = prouder;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.prouder);
        hash = 23 * hash + Objects.hashCode(this.community);
        return hash;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProuderCommunities other = (ProuderCommunities) obj;
        if (!Objects.equals(this.prouder, other.prouder)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "ProuderCommunities{" + "pdouder=" + prouder + ", "
                + "community=" + community + '}';
    }
       
}
