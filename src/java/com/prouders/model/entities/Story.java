/*
* @(#)Story.java 1.01 15/06/01
*
* Copyright (c) 2015 Aleksandr Chudov
* All Rights Reserved.
*
* This file provides Story entity class. 
*/
package com.prouders.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Story class provides entity
 * 
 * @version 1.01 01 JUN 2015
 * @author Aleksandr Chudov
 */
@Entity
public class Story implements Serializable{
    // private fields
    @Id
    private Long ID;
    private String header;
    private String body;
    private Date time;
    private Long totalAmount;
    private Prouder prouder;
    private Community community;
    private StoryFlag flag;
    private Status status;
    
    /**
     * empty constructor
     */
    public Story() {}
    
    /**
     * constructor with one parameter
     * @param ID 
     */
    public Story(Long ID){
        this.ID = ID;
    }

    /**
     * constructor with parameters
     * @param ID
     * @param header
     * @param body
     * @param time
     * @param totalAmount
     * @param prouder
     * @param community
     * @param flag
     * @param status 
     */
    public Story(Long ID, String header, String body, Date time, 
            Long totalAmount, Prouder prouder, Community community, 
            StoryFlag flag, Status status) {
        this.ID = ID;
        this.header = header;
        this.body = body;
        this.time = time;
        this.totalAmount = totalAmount;
        this.prouder = prouder;
        this.community = community;
        this.flag = flag;
        this.status = status;
    }
    
    // get and set merhods
    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

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

    public StoryFlag getFlag() {
        return flag;
    }

    public void setFlag(StoryFlag flag) {
        this.flag = flag;
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
        hash = 97 * hash + Objects.hashCode(this.ID);
        hash = 97 * hash + Objects.hashCode(this.header);
        hash = 97 * hash + Objects.hashCode(this.body);
        hash = 97 * hash + Objects.hashCode(this.time);
        hash = 97 * hash + Objects.hashCode(this.totalAmount);
        hash = 97 * hash + Objects.hashCode(this.prouder);
        hash = 97 * hash + Objects.hashCode(this.community);
        hash = 97 * hash + Objects.hashCode(this.flag);
        hash = 97 * hash + Objects.hashCode(this.status);
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
        final Story other = (Story) obj;
        if (!Objects.equals(this.ID, other.ID)) {
            return false;
        }
        if (!Objects.equals(this.header, other.header)) {
            return false;
        }
        if (!Objects.equals(this.body, other.body)) {
            return false;
        }
        if (!Objects.equals(this.time, other.time)) {
            return false;
        }
        if (!Objects.equals(this.totalAmount, other.totalAmount)) {
            return false;
        }
        if (!Objects.equals(this.prouder, other.prouder)) {
            return false;
        }
        if (!Objects.equals(this.community, other.community)) {
            return false;
        }
        if (!Objects.equals(this.flag, other.flag)) {
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
        return "Story{" + "ID=" + ID + ", header=" + header + ", body=" 
                + body + ", time=" + time + ", totalAmount=" + totalAmount 
                + ", prouder=" + prouder + ", community=" + community 
                + ", flag=" + flag + ", status=" + status + '}';
    }
    
    
}
