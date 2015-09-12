/*
* @(#)ProudsCounter.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides ProudsCounter entity class. 
*/

package com.prouders.model.entities;

import java.util.Objects;
import java.io.Serializable;

/**
 * The ProudsCounter class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public class ProudsCounter implements Serializable{
    // private fields
    private Prouder prouder;
    private Story story;

    /**
     * empty constructor
     */
    public ProudsCounter() {}

    /**
     * constructor with parameters
     * @param prouder
     * @param story 
     */
    public ProudsCounter(Prouder prouder, Story story) {
        this.prouder = prouder;
        this.story = story;
    }

    // get and set merhods
    public Prouder getProuder() {
        return prouder;
    }

    public void setProuder(Prouder prouder) {
        this.prouder = prouder;
    }

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.prouder);
        hash = 31 * hash + Objects.hashCode(this.story);
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
        final ProudsCounter other = (ProudsCounter) obj;
        if (!Objects.equals(this.prouder, other.prouder)) {
            return false;
        }
        if (!Objects.equals(this.story, other.story)) {
            return false;
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "ProudsCounter{" + "prouder=" + prouder + ", story=" 
                + story + '}';
    }
    
    
}
