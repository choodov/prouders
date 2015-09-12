/*
* @(#)Community.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides Community entity class. 
*/

package com.prouders.model.entities;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Community class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class Community implements Serializable{
    // private fields
    @Id
    private Long ID;
    private String name;
    private String description;
    private Country country;
    private Long totalAmount;
    private Status status;
    private CommunityType type;
    
    /**
     * empty constructor
     */
    public Community() {}

    /**
     * constructor with parameters
     * @param ID
     * @param name
     * @param description
     * @param country
     * @param totalAmount
     * @param status
     * @param type 
     */
    public Community(Long ID, String name, String description, 
            Country country, Long totalAmount, Status status, 
            CommunityType type) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.country = country;
        this.totalAmount = totalAmount;
        this.status = status;
        this.type = type;
    }
    
    /**
     * constructor with one parameter
     * @param ID 
     */
    public Community(Long ID) {
        this.ID = ID;
    }

    // get and set merhods
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public CommunityType getType() {
        return type;
    }

    public void setType(CommunityType type) {
        this.type = type;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.ID);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.description);
        hash = 89 * hash + Objects.hashCode(this.country);
        hash = 89 * hash + Objects.hashCode(this.totalAmount);
        hash = 89 * hash + Objects.hashCode(this.status);
        hash = 89 * hash + Objects.hashCode(this.type);
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
        final Community other = (Community) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.totalAmount, other.totalAmount)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "Community{" + "ID=" + ID + ", name=" + name + ", description=" 
                + description + ", country=" + country + ", totalAmount=" 
                + totalAmount + ", status=" + status + ", type=" + type + '}';
    }
}
