/*
* @(#)Prouder.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides Prouder entity class. 
*/

package com.prouders.model.entities;

import java.util.Objects;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Prouder class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class Prouder implements Serializable{
    // private fields
    @Id
    private Long ID;
    private String name;
    private String email;
    private String password;
    private String photo;
    private Country country;
    private UserType type;
    private Status status;
    
    /**
     * empty constructor
     */
    public Prouder() {}

    /**
     * constructor with parameters
     * @param ID
     * @param name
     * @param email
     * @param password
     * @param photo
     * @param country
     * @param type
     * @param status 
     */
    public Prouder(Long ID, String name, String email, String password, String photo, Country country, UserType type, Status status) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photo = photo;
        this.country = country;
        this.type = type;
        this.status = status;
    }
    
    /**
     * constructor with one parameter
     * @param ID 
     */
    public Prouder(Long ID) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // hashCode method
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.ID);
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.password);
        hash = 79 * hash + Objects.hashCode(this.photo);
        hash = 79 * hash + Objects.hashCode(this.country);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.status);
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
        final Prouder other = (Prouder) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.photo, other.photo)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return true;
    }

    // toString method
    @Override
    public String toString() {
        return "Prouder{" + "ID=" + ID + ", name=" + name + ", email=" + email 
            + ", password=" + password + ", photo=" + photo 
            + ", country=" + country + ", type=" + type 
            + ", status=" + status + '}';
    }
    
    
}
