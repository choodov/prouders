/*
* @(#)Status.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides Status enumeration. 
*/

package com.prouders.model.entities;

/**
 * The Status enumeration provides statuses
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
public enum Status {
    ACTIVE(1), BLOCKED(2), DELETED(3); // type of status
    private final int value;           // field for status values
    
    /**
     * constructor with parameter
     * @param value 
     */
    Status(int value){
        this.value = value;
    }
    
    /**
     * return value of status
     * @return 
     */
    public int getValue(){ return value; }
    
    /**
     * return status by given value
     * @param value
     * @return 
     */
    public static Status getStatus(int value) {
        switch(value) {
            case 1: return ACTIVE;
            case 2: return BLOCKED;
            case 3: return DELETED;
            default: return null;
        }
    }
            
}
